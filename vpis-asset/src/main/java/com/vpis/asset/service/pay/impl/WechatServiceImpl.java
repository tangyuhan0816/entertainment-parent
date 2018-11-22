package com.vpis.asset.service.pay.impl;

import com.vpis.asset.service.pay.IPayService;
import com.vpis.common.entity.pay.request.PayRequest;
import com.vpis.common.entity.pay.request.wechat.WxPayUnifiedorderRequest;
import com.vpis.common.entity.pay.response.wechat.PayResponse;
import com.vpis.common.entity.pay.response.wechat.WxPayUnifiedorderResponse;
import com.vpis.common.exception.HttpServiceException;
import com.vpis.common.type.pay.SignType;
import com.vpis.common.utils.*;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

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
    public void doNotify(String notifyData) {
        log.info("【微信异步回调参数】:{}",notifyData);
//        WxPayUnifiedorderResponse
    }

    public PayResponse buildPayResponse(WxPayUnifiedorderResponse wxResponse){

        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000L);
        String nonceStr = OrderUtil.getRandomStr();

        PayResponse payResponse = new PayResponse();
        payResponse.setAppId(wxResponse.getAppId());
        payResponse.setPartnerId(wxResponse.getMchId());
        payResponse.setPrepayId(wxResponse.getPrepayId());
        payResponse.setPackAge("Sign=WXPay");
        payResponse.setNonceStr(nonceStr);
        payResponse.setTimeStamp(timeStamp);
        payResponse.setPaySign(this.sign(XmlUtils.buildMap(payResponse), mchKey));
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
}
