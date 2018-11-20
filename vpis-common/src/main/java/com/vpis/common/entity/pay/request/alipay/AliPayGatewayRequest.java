package com.vpis.common.entity.pay.request.alipay;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AliPayGatewayRequest extends BasePayRequest{

    @JSONField(name = "out_trade_no")
    @JsonProperty("out_trade_no")
    private String outTradeNo;

    @JSONField(name = "product_code")
    @JsonProperty("product_code")
    private String productCode;

    @JSONField(name = "total_amount")
    @JsonProperty("total_amount")
    private Double totalAmount;

    @JSONField(name = "subject")
    @JsonProperty("subject")
    private String subject;

    @JSONField(name = "body")
    @JsonProperty("body")
    private String body;

    @JSONField(name = "time_exprice")
    @JsonProperty("time_exprice")
    private String timeExprice;
}
