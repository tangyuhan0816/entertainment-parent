package com.vpis.asset.service.pay.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.vpis.asset.service.pay.IPayService;
import com.vpis.asset.utils.AlipayConfig;
import com.vpis.common.entity.pay.request.wechat.PayRequest;
import com.vpis.common.entity.pay.response.wechat.PayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

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
        log.info("【支付宝异步回调参数】:{}",notifyData);
    }

    public PayResponse buildAliPayResponse(AlipayTradeAppPayResponse response){
        PayResponse payResponse = new PayResponse();
        payResponse.setBody(response.getBody());
        return payResponse;
    }
}
