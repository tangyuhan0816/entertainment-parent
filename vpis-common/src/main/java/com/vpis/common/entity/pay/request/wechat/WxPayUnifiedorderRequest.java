package com.vpis.common.entity.pay.request.wechat;

import lombok.Data;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: WxPayUnifiedorderRequest
 *  @package: com.vpis.common.entity.pay
 *  @Date: Created in 2018/11/16 下午3:07
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 微信统一下单实体类
 */
@Root(
    name = "xml",
    strict = false
)
@Data
public class WxPayUnifiedorderRequest {

    /**
     * 微信开放平台审核通过的应用APPID
     * 必传
     */
    @Element(name = "appid")
    private String appId;

    /**
     * 微信支付分配的商户号
     * 必传
     */
    @Element(name = "mch_id")
    private String mchId;

    /**
     * 终端设备号(门店号或收银设备ID)，默认请传"WEB"
     */
    @Element(
            name = "device_info",
            required = false
    )
    private String deviceInfo;

    /**
     * 随机字符串，不长于32位。推荐随机数生成算法
     * 必传
     */
    @Element(name = "nonce_str")
    private String nonceStr;

    /**
     * 签名
     * 必传
     */
    @Element(name = "sign")
    private String sign;

    /**
     * 签名类型，目前支持HMAC-SHA256和MD5，默认为MD5
     * 否
     */
    @Element(name = "sign_type",
            required = false)
    private String signType;

    /**
     * APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。
     * 必传
     */
    @Element(name = "body")
    private String body;

    /**
     * 商品详细描述，对于使用单品优惠的商户，改字段必须按照规范上传
     * 否
     */
    @Element(name = "detail",
            required = false)
    private String detail;

    /**
     * 附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
     * 否
     */
    @Element(name = "attach",
            required = false)
    private String attach;

    /**
     * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*且在同一个商户号下唯一
     * 必传
     */
    @Element(name = "out_trade_no")
    private String outTradeNo;

    /**
     * 符合ISO 4217标准的三位字母代码，默认人民币：CNY
     * 否
     */
    @Element(name = "fee_type")
    private String feeType;

    /**
     * 场景信订单总金额，单位为分
     * 必传
     */
    @Element(name = "total_fee")
    private String totalFee;

    /**
     * 用户端实际ip
     * 必传
     */
    @Element(name = "spbill_create_ip")
    private String spbillCreateIp;

    /**
     * 订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。
     * 否
     */
    @Element(name = "time_start",
            required = false)
    private String timeStart;

    /**
     * 订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。订单失效时间是针对订单号而言的，由于在请求支付的时候有一个必传参数prepay_id只有两小时的有效期，所以在重入时间超过2小时的时候需要重新请求下单接口获取新的prepay_id
     * 否
     */
    @Element(name = "time_expire",
            required = false)
    private String timeExpire;

    /**
     * 订单优惠标记，代金券或立减优惠功能的参数
     * 否
     */
    @Element(name = "goods_tag",
            required = false)
    private String goosdTag;

    /**
     * 接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。
     * 必传
     */
    @Element(name = "notify_url")
    private String notifyUrl;

    /**
     * 支付类型 APP
     * 必传
     */
    @Element(name = "trade_type")
    private String tradeType;

    /**
     * no_credit--指定不能使用信用卡支付
     * 否
     */
    @Element(name = "limit_pay",
            required = false)
    private String limitPay;

    /**
     * 该字段用于统一下单时上报场景信息，目前支持上报实际门店信息。

         {
         "store_id": "", //门店唯一标识，选填，String(32)
         "store_name":"”//门店名称，选填，String(64)

         }
     * 否
     */
    @Element(name = "scene_info",
            required = false)
    private String sceneInfo;
}
