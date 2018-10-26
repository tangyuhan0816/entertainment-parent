package com.vpis.asset.bean.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpis.asset.bean.base.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "楼盘返回对象")
public class HouseVo extends BaseBean{

    @ApiModelProperty(value = "楼宇名称")
    @JSONField(name = "house_name")
    @JsonProperty("house_name")
    private String houseName;

    @ApiModelProperty(value = "均价")
    @JSONField(name = "average_price")
    @JsonProperty("average_price")
    private BigDecimal averagePrice;

    @ApiModelProperty(value = "可投放广告幅数")
    @JSONField(name = "advice_num")
    @JsonProperty("advice_num")
    private Integer adviceNum;

    @ApiModelProperty(value = "banner图路径")
    @JSONField(name = "banner_url")
    @JsonProperty("banner_url")
    private String bannerUrl;

    @ApiModelProperty(value = "热度")
    @JSONField(name = "heat")
    @JsonProperty("heat")
    private Integer heat;

    @ApiModelProperty(value = "距离")
    @JSONField(name = "distance")
    @JsonProperty("distance")
    private BigDecimal distance;
}
