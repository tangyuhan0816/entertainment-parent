package com.vpis.common.entity.pay.response.wechat;

import lombok.Data;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(
        name = "xml",
        strict = false
)
@Data
public class WxPayAsyncResponse {

    @Element(
            name = "return_code"
    )
    private String returnCode;

    @Element(
            name = "return_msg",
            required = false
    )
    private String returnMsg;

    /**
     * 应用ID
     */
    @Element(
            name = "appid",
            required = false
    )
    private String appid;

    /**
     * 商户号
     */
    @Element(
            name = "mch_id",
            required = false
    )
    private String mchId;

    /**
     * 设备号
     */
    @Element(
            name = "device_info",
            required = false
    )
    private String deviceInfo;

    /**
     * 随机字符串
     */
    @Element(
            name = "nonce_str",
            required = false
    )
    private String nonceStr;

    /**
     * 签名
     */
    @Element(
            name = "sign",
            required = false
    )
    private String sign;

    /**
     * 业务结果
     */
    @Element(
            name = "result_code",
            required = false
    )
    private String resultCode;

    /**
     * 错误代码
     */
    @Element(
            name = "err_code",
            required = false
    )
    private String errCode;

    /**
     * 错误代码描述
     */
    @Element(
            name = "err_code_des",
            required = false
    )
    private String errCodeDes;

    /**
     * 用户标识
     */
    @Element(
            name = "openid",
            required = false
    )
    private String openid;

    /**
     * 是否关注公众账号
     */
    @Element(
            name = "is_subscribe",
            required = false
    )
    private String isSubscribe;

    /**
     * 交易类型
     */
    @Element(
            name = "trade_type",
            required = false
    )
    private String tradeType;

    /**
     * 付款银行
     */
    @Element(
            name = "bank_type",
            required = false
    )
    private String bankType;

    /**
     * 总金额
     */
    @Element(
            name = "total_fee",
            required = false
    )
    private Integer totalFee;

    /**
     * 货币种类
     */
    @Element(
            name = "fee_type",
            required = false
    )
    private String feeType;

    /**
     * 现金支付金额
     */
    @Element(
            name = "cash_fee",
            required = false
    )
    private String cashFee;

    /**
     * 现金支付货币类型
     */
    @Element(
            name = "cash_fee_type",
            required = false
    )
    private String cashFeeType;

    /**
     * 代金券金额
     */
    @Element(
            name = "coupon_fee",
            required = false
    )
    private String couponFee;

    /**
     * 代金券使用数量
     */
    @Element(
            name = "coupon_count",
            required = false
    )
    private String couponCount;

    /**
     * 微信支付订单号
     */
    @Element(
            name = "transaction_id",
            required = false
    )
    private String transactionId;

    /**
     * 应用ID
     */
    @Element(
            name = "out_trade_no",
            required = false
    )
    private String outTradeNo;

    /**
     * 商家数据包
     */
    @Element(
            name = "attach",
            required = false
    )
    private String attach;

    /**
     * 支付完成时间
     */
    @Element(
            name = "time_end",
            required = false
    )
    private String timeEnd;


}
