package com.vpis.asset.bean.vo;


import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpis.asset.bean.base.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

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

    @ApiModelProperty(value = "楼宇名称")
    @JSONField(name = "house_name")
    @JsonProperty("house_name")
    private String houseName;

    @ApiModelProperty(value = "购买数量")
    @JSONField(name = "product_count")
    @JsonProperty("product_count")
    private Integer productCount;

    @ApiModelProperty(value = "实际付款金额")
    @JSONField(name = "order_amount_total")
    @JsonProperty("order_amount_total")
    private BigDecimal orderAmountTotal;
}
