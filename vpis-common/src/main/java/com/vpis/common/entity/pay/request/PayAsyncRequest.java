package com.vpis.common.entity.pay.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: PayAsyncRequest
 *  @package: com.vpis.common.entity.pay.request
 *  @Date: Created in 2018/11/23 上午2:15
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 异步回调接口
 */
@Data
public class PayAsyncRequest {

    @ApiModelProperty(value = "通知时间")
    @JSONField(name = "notify_time")
    @JsonProperty("notify_time")
    private String notifyTime;

    @ApiModelProperty(value = "通知类型")
    @JSONField(name = "notify_type")
    @JsonProperty("notify_type")
    private String notifyType;

    @ApiModelProperty(value = "通知校验ID")
    @JSONField(name = "notify_id")
    @JsonProperty("notify_id")
    private String notifyId;

    @ApiModelProperty(value = "支付宝分配给开发者的应用Id")
    @JSONField(name = "app_id")
    @JsonProperty("app_id")
    private String appId;

    @ApiModelProperty(value = "编码格式")
    @JSONField(name = "charset")
    @JsonProperty("charset")
    private String charset;

    @ApiModelProperty(value = "接口版本")
    @JSONField(name = "version")
    @JsonProperty("version")
    private String version;

    @ApiModelProperty(value = "签名类型")
    @JSONField(name = "sign_type")
    @JsonProperty("sign_type")
    private String signType;

    @ApiModelProperty(value = "签名")
    @JSONField(name = "sign")
    @JsonProperty("sign")
    private String sign;

    @ApiModelProperty(value = "支付宝交易号")
    @JSONField(name = "trade_no")
    @JsonProperty("trade_no")
    private String tradeNo;

    @ApiModelProperty(value = "商户订单号")
    @JSONField(name = "out_trade_no")
    @JsonProperty("out_trade_no")
    private String outTradeNo;

    @ApiModelProperty(value = "交易状态")
    @JSONField(name = "trade_status")
    @JsonProperty("trade_status")
    private String tradeStatus;

    @ApiModelProperty(value = "订单金额")
    @JSONField(name = "total_amount")
    @JsonProperty("total_amount")
    private String totalAmount;

    @ApiModelProperty(value = "交易付款时间")
    @JSONField(name = "gmt_payment")
    @JsonProperty("gmt_payment")
    private String gmtPayment;
}
