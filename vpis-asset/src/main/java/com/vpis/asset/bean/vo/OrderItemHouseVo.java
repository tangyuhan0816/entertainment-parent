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
 *  @ClassName: OrderItemHouseVo
 *  @package: com.vpis.asset.bean.vo
 *  @Date: Created in 2018/12/9 下午3:59
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 
 */    
@Data
@ApiModel(value = "楼盘返回对象")
public class OrderItemHouseVo extends BaseBean{


    @ApiModelProperty(value = "楼盘名称")
    @JSONField(name = "house_name")
    @JsonProperty("house_name")
    private String houseName;
    
    @ApiModelProperty(value = "banner图路径")
    @JSONField(name = "banner_url")
    @JsonProperty("banner_url")
    private String bannerUrl;
    
    @ApiModelProperty(value = "购买数量")
    @JSONField(name = "product_count")
    @JsonProperty("product_count")
    private Integer productCount;

    @ApiModelProperty(value = "商品总价")
    @JSONField(name = "product_amount_total")
    @JsonProperty("product_amount_total")
    private BigDecimal productAmountTotal;
    
}
