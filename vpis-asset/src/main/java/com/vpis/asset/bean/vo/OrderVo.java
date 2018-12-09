package com.vpis.asset.bean.vo;


import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpis.asset.bean.base.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: OrderVo
 *  @package: com.vpis.asset.bean.vo
 *  @Date: Created in 2018/11/13 上午11:42
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 
 */    
@Data
@ApiModel(value = "订单返回对象")
public class OrderVo extends BaseBean{

    @ApiModelProperty(value = "id")
    @JSONField(name = "id")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value = "订单号")
    @JSONField(name = "order_no")
    @JsonProperty("order_no")
    private String orderNo;

    @ApiModelProperty(value = "实际付款总金额")
    @JSONField(name = "order_amount_total")
    @JsonProperty("order_amount_total")
    private BigDecimal orderAmountTotal;

    @ApiModelProperty(value = "订单状态")
    @JSONField(name = "order_status")
    @JsonProperty("order_status")
    private Integer orderStatus;

    @ApiModelProperty(value = "楼宇名称")
    @JSONField(name = "houses")
    @JsonProperty("houses")
    private List<OrderItemHouseVo> houses;
}
