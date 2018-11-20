package com.vpis.common.entity.pay.request.wechat;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpis.common.type.pay.PayTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: PayRequest
 *  @package: com.vpis.common.entity.pay
 *  @Date: Created in 2018/11/18 下午3:18
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
@Data
public class PayRequest {

    @ApiModelProperty(value = "支付类型")
    @JSONField(name = "pay_type")
    @JsonProperty("pay_type")
    private PayTypeEnum payType;

    @ApiModelProperty(value = "订单号")
    @JSONField(name = "order_id")
    @JsonProperty("order_id")
    private String orderId;

    @ApiModelProperty(value = "订单总金额")
    @JSONField(name = "order_amount")
    @JsonProperty("order_amount")
    private Double orderAmount;

    @ApiModelProperty(value = "订单名")
    @JSONField(name = "order_name")
    @JsonProperty("order_name")
    private String orderName;

    @ApiModelProperty(value = "商品名")
    @JSONField(name = "subject")
    @JsonProperty("subject")
    private String subject;

    @ApiModelProperty(value = "创建用户IP")
    @JSONField(name = "spbill_create_ip")
    @JsonProperty("spbill_create_ip")
    private String spbillCreateIp;
}
