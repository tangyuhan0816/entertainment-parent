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

    @Value("${wechat.notifyUrl}")
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
        appPayReq.setNotifyUrl(notifyUrl);

        // 请求参数
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        // 以下业务参数请按实际业务情况填写
        // ----------------------------------------------------------------
        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d
        // m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。 非必填 类型String 最大长度6
        model.setTimeoutExpress(TIMEOUT);

        // 订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]   [建议填写] 非必填 类型String 最大长度9
        model.setTotalAmount(new DecimalFormat("#.00").format(payRequest.getOrderAmount()));

        // 对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。  非必填 类型String 最大长度128
        model.setBody(payRequest.getOrderName());

        // 商品的标题/交易标题/订单标题/订单关键字等。  非必填 类型String 最大长度256
        model.setSubject(payRequest.getSubject());

        // 商户网站唯一订单号 型String 最大长度64
        model.setOutTradeNo(payRequest.getOrderId());

        appPayReq.setBizModel(model);
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
