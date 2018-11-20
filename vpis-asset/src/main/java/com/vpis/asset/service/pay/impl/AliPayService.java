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
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: AliPayService
 *  @package: com.vpis.asset.service.pay.impl
 *  @Date: Created in 2018/11/19 下午8:05
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 支付宝
 */
@Slf4j
@Service("aliPayService")
public class AliPayService implements IPayService{

    private String alipayNotifyUrl;

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
        appPayReq.setNotifyUrl(alipayNotifyUrl);

        // 请求参数
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        // 以下业务参数请按实际业务情况填写
        // ----------------------------------------------------------------
        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d
        // m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。 非必填 类型String 最大长度6
        model.setTimeoutExpress("60m");

        // 绝对超时时间，格式为yyyy-MM-dd HH:mm。
//    	model.setTimeExpire(""); // [可不填写] 类型String 最大长度32

        // 订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]   [建议填写] 非必填 类型String 最大长度9
        model.setTotalAmount(new DecimalFormat("#.00").format(payRequest.getOrderAmount()));

        // 收款支付宝用户ID。 如果该值为空，则默认为商户签约账号对应的支付宝用户ID
//    	model.setSellerId("");// [可不填写] 非必填 类型String 最大长度16

        // 销售产品码，商家和支付宝签约的产品码
//    	model.setProductCode("");// [没有产品码就不填] 非必填 类型String 最大长度64

        // 对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。  非必填 类型String 最大长度128
        // TODO 需要修改文案
        model.setBody(payRequest.getOrderName());

        // 商品的标题/交易标题/订单标题/订单关键字等。  非必填 类型String 最大长度256
        // TODO 需要修改文案
        model.setSubject("订单subject");

        // 商户网站唯一订单号 型String 最大长度64
        model.setOutTradeNo(payRequest.getOrderId());

        // 商品主类型 :0-虚拟类商品,1-实物类商品 非必填
        model.setGoodsType("0");


        // 优惠参数  仅与支付宝协商后可用
        // model.setPromoParams("");// 估计没有

        // 公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数。
        // 支付宝只会在同步返回（包括跳转回商户网站）和异步通知时将该参数原样返回。
        // 本参数必须进行UrlEncode之后才可以发送给支付宝。
        // [如果订单中有额外需要处理的参数,比如代理商之类的,建议加上,没有需要处理的就别写了]
//    	model.setPassbackParams(""); //[按需填写] 非必填 类型String 最大长度512

        // 分账信息 估计用不上
        // RoyaltyInfo royalty_info = new RoyaltyInfo();

        // 额外扩展信息 估计也用不上
        //ExtendParams extend = new ExtendParams();
        //extend.setHbFqNum("3"); //花呗分期

        // 商户传入业务信息，具体值要和支付宝约定，应用于安全，营销等参数直传场景，格式为json格式
        // reqJson.addProperty("business_params", ""); // 估计没有

        // ----------------------------------------------------------------

        appPayReq.setBizModel(model);
        // 发起请求
        AlipayTradeAppPayResponse response = null;
        try {
            response = alipayClient.sdkExecute(appPayReq);

            if(response.isSuccess()){
                // 返回前端app需要的orderStr
                return null;
            } else {
                throw new AlipayApiException("skdExecute失败.");
            }
        } catch (AlipayApiException e) {
            log.error(e.getMessage(),e);
            return null;
        }
    }

    public PayResponse buildAliPayResponse(AlipayTradeAppPayResponse response){
        PayResponse payResponse = new PayResponse();
        return payResponse;
    }
}
