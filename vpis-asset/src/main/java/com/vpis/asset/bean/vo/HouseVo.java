package com.vpis.asset.bean.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpis.asset.bean.base.BaseBean;
import com.vpis.common.entity.house.House;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: HouseVo
 *  @package: com.vpis.asset.bean.vo
 *  @Date: Created in 2018/10/29 下午5:00
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
@Data
@ApiModel(value = "楼盘返回对象")
public class HouseVo extends BaseBean{

    @ApiModelProperty(value = "id")
    @JSONField(name = "id")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value = "楼宇名称")
    @JSONField(name = "house_name")
    @JsonProperty("house_name")
    private String houseName;

    @ApiModelProperty(value = "价格")
    @JSONField(name = "price")
    @JsonProperty("price")
    private BigDecimal price;

    @ApiModelProperty(value = "原价")
    @JSONField(name = "original_price")
    @JsonProperty("original_price")
    private BigDecimal originalPrice;

    @ApiModelProperty(value = "小区均价")
    @JSONField(name = "average_price")
    @JsonProperty("average_price")
    private BigDecimal averagePrice;

    @ApiModelProperty(value = "市场价")
    @JSONField(name = "market_price")
    @JsonProperty("market_price")
    @Column(name = "market_price")
    private BigDecimal marketPrice;

    @ApiModelProperty(value = "可投放广告幅数")
    @JSONField(name = "advice_num")
    @JsonProperty("advice_num")
    private Integer adviceNum;

    @ApiModelProperty(value = "入住时间")
    @JSONField(name = "check_in_date")
    @JsonProperty("check_in_date")
    private Date checkInDate;

    @ApiModelProperty(value = "小区平均年龄")
    @JSONField(name = "average_age")
    @JsonProperty("average_age")
    private Integer averageAge;

    @ApiModelProperty(value = "banner图路径")
    @JSONField(name = "banner_url")
    @JsonProperty("banner_url")
    private String bannerUrl;

    @ApiModelProperty(value = "热度")
    @JSONField(name = "heat")
    @JsonProperty("heat")
    private Integer heat;

    @ApiModelProperty(value = "居住人口")
    @JSONField(name = "person_num")
    @JsonProperty("person_num")
    @Column(name = "person_num")
    private Integer personNum;

    @ApiModelProperty(value = "月曝光人流量")
    @JSONField(name = "month_person_flow")
    @JsonProperty("month_person_flow")
    private Integer monthPersonFlow;

    @ApiModelProperty(value = "距离(单位：米)")
    @JSONField(name = "distance")
    @JsonProperty("distance")
    private BigDecimal distance;

    public HouseVo conver(House house){
        this.setAdviceNum(house.getAdviceNum());
        this.setBannerUrl(house.getBannerUrl());
        this.setHeat(house.getHeat());
        this.setPrice(house.getPrice());
        this.setMarketPrice(house.getMarketPrice());
        this.setOriginalPrice(house.getOriginalPrice());
        this.setAveragePrice(house.getAveragePrice());
        this.setId(house.getId());
        this.setHouseName(house.getHouseName());
        return this;
    }

    public HouseVo converDetail(House house){
        this.setAdviceNum(house.getAdviceNum());
        this.setBannerUrl(house.getBannerUrl());
        this.setHeat(house.getHeat());
        this.setPrice(house.getPrice());
        this.setMarketPrice(house.getMarketPrice());
        this.setOriginalPrice(house.getOriginalPrice());
        this.setAveragePrice(house.getAveragePrice());
        this.setHouseName(house.getHouseName());
        this.setMonthPersonFlow(house.getMonthPersonFlow());
        this.setCheckInDate(house.getCheckInDate());
        this.setAverageAge(house.getAverageAge());
        return this;
    }
}
