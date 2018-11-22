package com.vpis.asset.service.pay.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.vpis.asset.service.order.OrderService;
import com.vpis.asset.service.pay.IPayService;
import com.vpis.asset.utils.AlipayConfig;
import com.vpis.common.entity.pay.request.PayAsyncRequest;
import com.vpis.common.entity.pay.request.PayRequest;
import com.vpis.common.entity.pay.response.wechat.PayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: AliPayServiceImpl
 *  @package: com.vpis.asset.service.pay.impl
 *  @Date: Created in 2018/11/19 下午8:05
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 支付宝
 */
@Slf4j
@Service("aliPayService")
public class AliPayServiceImpl implements IPayService{

    @Value("${alipay.notifyUrl}")
    public String notifyUrl;

    private static final String TIMEOUT = "60m";

    @Autowired
    private OrderService orderService;

    @Override
    public PayResponse pay(PayRequest payRequest) {

        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.ALIPAY_URL,
                AlipayConfig.ALIPAY_APP_ID,
                AlipayConfig.ALIPAY_PRIVATE_KEY,
                AlipayConfig.ALIPAY_FORMAT,
                AlipayConfig.ALIPAY_CHARSET,
                AlipayConfig.ALIPAY_PUBLIC_KEY,
                AlipayConfig.ALIPAY_SIGN_TYPE);
        // 创建APP支付订单信息
        AlipayTradeAppPayRequest appPayReq = new AlipayTradeAppPayRequest();

        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();

        model.setTimeoutExpress(TIMEOUT);

        model.setTotalAmount(payRequest.getOrderAmount().toString());

        model.setBody(payRequest.getOrderName());

        model.setSubject(payRequest.getSubject());

        model.setOutTradeNo(payRequest.getOrderId());

        model.setProductCode("QUICK_MSECURITY_PAY");

        appPayReq.setBizModel(model);

        appPayReq.setNotifyUrl(notifyUrl);
        // 发起请求
        AlipayTradeAppPayResponse response = null;
        try {
            response = alipayClient.sdkExecute(appPayReq);

            if(response.isSuccess()){
                // 返回前端app需要的orderStr
                return this.buildAliPayResponse(response);
            } else {
                throw new AlipayApiException("skdExecute失败.");
            }
        } catch (AlipayApiException e) {
            log.error(e.getMessage(),e);
            return null;
        }
    }

    @Override
    public void doNotify(String notifyData) {
        try {
            Map<String,String> params = new HashMap<>();
            Map requestParams = JSONObject.parseObject(notifyData, Map.class);
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。
                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }

            boolean flag = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PRIVATE_KEY, AlipayConfig.ALIPAY_CHARSET,"RSA2");
            if(flag){
                PayAsyncRequest request = this.buildAliPayResponse(params);
                if(request.getTradeStatus().equals("TRADE_SUCCESS")){
                    String orderNo = request.getOutTradeNo();
                    BigDecimal amount = orderService.findAmountByOrderNo(orderNo);
                    if(amount.compareTo(BigDecimal.ZERO) > 0){
                        orderService.updateOrderSuccess(orderNo, request.getTradeNo());
                    } else {
                        orderService.updateOrderError(orderNo);
                    }
                }

            } else {
                log.error("【支付宝异步回调参数】 签名校验error:{}",notifyData);
            }
            log.info("【支付宝异步回调参数】:{}",notifyData);
        } catch (AlipayApiException e) {
            log.error("【支付宝异步回调异常】error:{},{}",e.getMessage(),e);
        }
    }

    public PayResponse buildAliPayResponse(AlipayTradeAppPayResponse response){
        PayResponse payResponse = new PayResponse();
        payResponse.setBody(response.getBody());
        return payResponse;
    }

    public PayAsyncRequest buildAliPayResponse(Map<String,String> params){
        PayAsyncRequest request = new PayAsyncRequest();
        request.setNotifyTime(params.get("notify_time"));
        request.setNotifyType(params.get("notify_type"));
        request.setNotifyId(params.get("notify_id"));
        request.setAppId(params.get("app_id"));
        request.setCharset(params.get("charset"));
        request.setVersion(params.get("version"));
        request.setSignType(params.get("sign_type"));
        request.setSign(params.get("sign"));
        request.setTradeNo(params.get("trade_no"));
        request.setOutTradeNo(params.get("out_trade_no"));
        request.setTradeStatus(params.get("trade_status"));
        request.setTotalAmount(params.get("total_amount"));
        request.setGmtPayment(params.get("gmt_payment"));
        return request;
    }
}
