package com.vpis.asset.service.pay.impl;

import com.vpis.asset.service.order.OrderService;
import com.vpis.asset.service.pay.IPayService;
import com.vpis.common.entity.pay.request.PayRequest;
import com.vpis.common.entity.pay.request.wechat.WxPayUnifiedorderRequest;
import com.vpis.common.entity.pay.response.wechat.PayResponse;
import com.vpis.common.entity.pay.response.wechat.WxPayAsyncResponse;
import com.vpis.common.entity.pay.response.wechat.WxPayUnifiedorderResponse;
import com.vpis.common.exception.HttpServiceException;
import com.vpis.common.type.pay.SignType;
import com.vpis.common.utils.*;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: WechatServiceImpl
 *  @package: com.vpis.asset.service.pay.impl
 *  @Date: Created in 2018/11/16 下午3:33
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 微信服务
 */
@Slf4j
@Service("wechatService")
public class WechatServiceImpl implements IPayService{

    @Value("${wechat.mpAppId}")
    public String mpAppId;

    @Value("${wechat.mchId}")
    public String mchId;

    @Value("${wechat.mchKey}")
    public String mchKey;

    @Value("${wechat.notifyUrl}")
    public String notifyUrl;

    private static final String WX_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    private static final String UNINI = "CNY";

    private static final String TRADETYPE = "APP";

    @Autowired
    private OrderService orderService;

    @Override
    public PayResponse pay(PayRequest payRequest) {
        WxPayUnifiedorderRequest request = new WxPayUnifiedorderRequest();
        request.setAppId(mpAppId);
        request.setMchId(mchId);

        //默认MD5
        request.setSignType(SignType.MD5.name());
        request.setDeviceInfo(TRADETYPE);
        request.setNonceStr(OrderUtil.getRandomStr());
        request.setBody(payRequest.getOrderName());
        request.setOutTradeNo(payRequest.getOrderId());
        request.setTotalFee(MoneyUtil.Yuan2Fen(payRequest.getOrderAmount()).toString());
        request.setSpbillCreateIp(payRequest.getSpbillCreateIp());
        request.setNotifyUrl(notifyUrl);
        request.setTradeType(TRADETYPE);
        request.setFeeType(UNINI);
        request.setSign(this.sign(XmlUtils.buildMap(request), mchKey));

        HttpHeaders headers = new DefaultHttpHeaders();
        headers.set("Content-Type","application/xml; charset=utf-8");
        String responseBody = null;
        try {
            responseBody = AsyncHttpUtils.syncPost(WX_ORDER_URL, XmlUtils.xmlToString(request));
        } catch (HttpServiceException e) {
            e.printStackTrace();
        }
        if(Preconditions.isBlank(responseBody)){
            throw new RuntimeException("微信统一下单】发起下单网络异常！");
        } else {
            try {
                responseBody = new String(responseBody.getBytes("ISO-8859-1"),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("【微信统一下单】下单乱码解码异常,responseBody = " + responseBody);
            }
            WxPayUnifiedorderResponse response = (WxPayUnifiedorderResponse) XmlUtils.stringToXml(responseBody, WxPayUnifiedorderResponse.class);
            if (!response.getReturnCode().equals("SUCCESS")) {
                throw new RuntimeException("【微信统一下单】下单, returnCode != SUCCESS, returnMsg = " + response.getReturnMsg());
            } else if (!response.getResultCode().equals("SUCCESS")) {
                throw new RuntimeException("【微信统一下单】下单, resultCode != SUCCESS, err_code = " + response.getErrCode() + " err_code_des=" + response.getErrCodeDes());
            } else {
                return this.buildPayResponse(response);
            }
        }
    }

    @Override
    public void doNotify(Object request) {
        String notifyData = (String)request;
        if(this.verify(XmlUtils.toMap(notifyData), mchKey).booleanValue()) {
            log.error("【微信支付异步通知】签名验证失败, response={}", notifyData);
            throw new RuntimeException("【微信支付异步通知】签名验证失败");
        } else {
            WxPayAsyncResponse asyncResponse = (WxPayAsyncResponse)XmlUtils.toObject(notifyData, WxPayAsyncResponse.class);

            //支付失败且为非支付订单
            if(!Objects.equals(asyncResponse.getReturnCode(),"SUCCESS") && !Objects.equals(asyncResponse.getErrCode(),"ORDERPAID")) {
                throw new RuntimeException("【微信支付异步通知】发起支付, returnCode != SUCCESS, returnMsg = " + asyncResponse.getReturnMsg());
            }

            //支付失败但为已支付订单
            if(!Objects.equals(asyncResponse.getResultCode(),"SUCCESS") && Objects.equals(asyncResponse.getErrCode(),"ORDERPAID")) {
                orderService.updateOrderSuccess(asyncResponse.getOutTradeNo(), asyncResponse.getTransactionId());
            }

            //支付成功订单
            if(Objects.equals(asyncResponse.getResultCode(),"SUCCESS")) {

                //校验订单金额,防止数据泄漏导致出现“假通知”,造成资金损失
                BigDecimal amount = orderService.findAmountByOrderNo(asyncResponse.getOutTradeNo());
                if(amount != null){
                    BigDecimal wxResult = new BigDecimal(asyncResponse.getTotalFee()).movePointLeft(2);
                    if(amount.compareTo(wxResult) != 0){
                        orderService.updateOrderError(asyncResponse.getOutTradeNo());
                    }else{
                        orderService.updateOrderSuccess(asyncResponse.getOutTradeNo(), asyncResponse.getTransactionId());
                    }
                }
            } else {
                throw new RuntimeException("【微信支付异步通知】发起支付, resultCode != SUCCESS, err_code = " + asyncResponse.getErrCode() + " err_code_des=" + asyncResponse.getErrCodeDes());
            }
        }
    }

    public PayResponse buildPayResponse(WxPayUnifiedorderResponse wxResponse){

//        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000L);
//        String nonceStr = OrderUtil.getRandomStr();
//        String packAge = "prepay_id=" + wxResponse.getPrepayId();
//        String signType = "MD5";
//        Map<String, String> map = new HashMap<>();
//        map.put("appId", wxResponse.getAppId());
//        map.put("timeStamp", timeStamp);
//        map.put("nonceStr", nonceStr);
//        map.put("package", packAge);
//        map.put("signType", signType);
//
//        PayResponse payResponse = new PayResponse();
//        payResponse.setAppId(wxResponse.getAppId());
//        payResponse.setPartnerId(wxResponse.getMchId());
//        payResponse.setPrepayId(wxResponse.getPrepayId());
//        payResponse.setPackAge("Sign=WXPay");
//        payResponse.setNonceStr(nonceStr);
//        payResponse.setTimeStamp(timeStamp);
//        payResponse.setPaySign(this.sign(map, mchKey));
//        return payResponse;

        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000L);
//        String nonceStr = OrderUtil.getRandomStr();

        PayResponse payResponse = new PayResponse();
        payResponse.setAppId(wxResponse.getAppId());
        payResponse.setPartnerId(wxResponse.getMchId());
        payResponse.setPrepayId(wxResponse.getPrepayId());
        payResponse.setPackAge("Sign=WXPay");
        payResponse.setNonceStr(wxResponse.getNonceStr());
        payResponse.setTimeStamp(timeStamp);
        payResponse.setPaySign(wxResponse.getSign());
        return payResponse;
    }

    public String sign(Map<String, String> param, String signKey){
        SortedMap<String, String> sortedMap = new TreeMap<>(param);
        StringBuilder url = new StringBuilder();

        for(Map.Entry<String, String> entry : sortedMap.entrySet()){

            String key = entry.getKey();

            String value = entry.getValue();

            if(Preconditions.isNotBlank(key) &&
                    !"sign".equals(key) && !"key".equals(key)){
                url.append(key).append("=").append(value).append("&");
            }
        }

        url.append("key=").append(signKey);
        return DigestUtils.md5Hex(url.toString()).toUpperCase();
    }

    public Boolean verify(Map<String, String> params, String signKey) {
        String sign = sign(params, signKey);
        return sign.equals(params.get("sign"));
    }
}
