package com.vpis.asset.service.pay.impl;

import com.vpis.asset.service.pay.IPayService;
import com.vpis.common.entity.pay.request.wechat.PayRequest;
import com.vpis.common.entity.pay.request.wechat.WxPayUnifiedorderRequest;
import com.vpis.common.entity.pay.response.wechat.PayResponse;
import com.vpis.common.entity.pay.response.wechat.WxPayUnifiedorderResponse;
import com.vpis.common.exception.HttpServiceException;
import com.vpis.common.type.pay.SignType;
import com.vpis.common.utils.AsyncHttpUtils;
import com.vpis.common.utils.MoneyUtil;
import com.vpis.common.utils.OrderUtil;
import com.vpis.common.utils.Preconditions;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaders;
import org.apache.commons.codec.digest.DigestUtils;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.HashMap;
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
@Service("wechatService")
public class WechatServiceImpl implements IPayService{

    @Value("${wechat.mpAppId}")
    public String mpAppId;

    @Value("${wechat.mpAppId}")
    public String mchId;

    @Value("${wechat.mpAppId}")
    public String mchKey;

    @Value("${wechat.mpAppId}")
    public String notifyUrl;

    private static final String WX_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    @Override
    public PayResponse pay(PayRequest payRequest) {
        WxPayUnifiedorderRequest request = new WxPayUnifiedorderRequest();
        request.setAppId(mpAppId);
        request.setMchId(mchId);
        request.setNonceStr(OrderUtil.getRandomStr());

        //默认MD5
        request.setSignType(SignType.MD5.name());
        request.setBody(payRequest.getOrderName());
        request.setOutTradeNo(payRequest.getOrderId());
        request.setTotalFee(MoneyUtil.Yuan2Fen(payRequest.getOrderAmount()).toString());
        request.setSpbillCreateIp(payRequest.getSpbillCreateIp());
        request.setNotifyUrl(notifyUrl);
        request.setTradeType("APP");
        request.setSign(this.sign(this.buildMap(request), mchKey));

        HttpHeaders headers = new DefaultHttpHeaders();
        headers.set("Content-Type","application/xml; charset=utf-8");
        String responseBody = null;
        try {
            responseBody = AsyncHttpUtils.syncPost(WX_ORDER_URL, xmlToString(request));
        } catch (HttpServiceException e) {
            e.printStackTrace();
        }
        if(Preconditions.isBlank(responseBody)){
            throw new RuntimeException("微信统一下单】发起下单网络异常！");
        } else {
            WxPayUnifiedorderResponse response = (WxPayUnifiedorderResponse) stringToXml(responseBody, WxPayUnifiedorderResponse.class);
            if (!response.getReturnCode().equals("SUCCESS")) {
                throw new RuntimeException("【微信统一下单】下单, returnCode != SUCCESS, returnMsg = " + response.getReturnMsg());
            } else if (!response.getResultCode().equals("SUCCESS")) {
                throw new RuntimeException("【微信统一下单】下单, resultCode != SUCCESS, err_code = " + response.getErrCode() + " err_code_des=" + response.getErrCodeDes());
            } else {
                return this.buildPayResponse(response);
            }
        }
    }

    public PayResponse buildPayResponse(WxPayUnifiedorderResponse wxResponse){

        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000L);
        String nonceStr = OrderUtil.getRandomStr();

        PayResponse payResponse = new PayResponse();
        payResponse.setAppId(wxResponse.getAppId());
        payResponse.setPartnerId(wxResponse.getMchId());
        payResponse.setPackAge("Sign=WXPay");
        payResponse.setNonceStr(nonceStr);
        payResponse.setTimeStamp(timeStamp);
        payResponse.setPaySign(this.sign(this.buildMap(payResponse), mchKey));
        return payResponse;
    }

    public String xmlToString(WxPayUnifiedorderRequest request){
        Serializer serializer = new Persister();
        StringWriter stringWriter = new StringWriter();
        try {
            serializer.write(request, stringWriter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringWriter.toString();
    }

    public Object stringToXml(String xml, Class<?> cls){
        Serializer serializer = new Persister();

        try {
            return serializer.read(cls,xml);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Map<String, String> buildMap(Object obj) {
        Map<String, String> params = new HashMap<>();
        try {
            Class<?> aClass = obj.getClass();
            Field[] fields = aClass.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                Element annotation = field.getAnnotation(Element.class);
                if (Preconditions.isNotBlank(annotation)) {
                    fieldName = annotation.name();
                }
                String value = field.get(obj) == null ? "" : String.valueOf(field.get(obj));
                if(Preconditions.isBlank(value)){
                    continue;
                }
                params.put(fieldName,value);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return params;
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
        return DigestUtils.md5Hex(url.toString());
    }
}
