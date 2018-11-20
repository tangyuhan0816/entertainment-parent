package com.vpis.common.entity.pay.response.alipay;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: AliPayGatewayResponse
 *  @package: com.vpis.common.entity.pay.response.alipay
 *  @Date: Created in 2018/11/20 下午12:04
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 阿里下单支付统一响应类
 */
@Data
public class AliPayGatewayResponse extends AliPayBaseResponse{

    @JSONField(name = "trade_no")
    @JsonProperty("trade_no")
    private String tradeNo;

    @JSONField(name = "out_trade_no")
    @JsonProperty("out_trade_no")
    private String outTradeNo;

    @JSONField(name = "seller_id")
    @JsonProperty("seller_id")
    private String sellerId;

    @JSONField(name = "total_amount")
    @JsonProperty("total_amount")
    private String totalAmount;
}
