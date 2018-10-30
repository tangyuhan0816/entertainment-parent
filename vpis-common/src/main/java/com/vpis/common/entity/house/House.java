package com.vpis.common.entity.house;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpis.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: House
 *  @package: com.vpis.common.entity.house
 *  @Date: Created in 2018/10/26 上午11:13
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 楼盘
 */
@Data
@Entity
@Table(name = "house")
public class House extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键")
    @JSONField(name = "id")
    @JsonProperty("id")
    @Column(name = "id")
    private Long id;

    @JSONField(name = "user_id")
    @JsonProperty("user_id")
    @Column(name = "user_id")
    private Long userId;

    @ApiModelProperty(value = "楼宇名称")
    @JSONField(name = "house_name")
    @JsonProperty("house_name")
    @Column(name = "house_name")
    private String houseName;

    @ApiModelProperty(value = "原价")
    @JSONField(name = "original_price")
    @JsonProperty("original_price")
    @Column(name = "original_price")
    private BigDecimal originalPrice;

    @ApiModelProperty(value = "市场价")
    @JSONField(name = "market_price")
    @JsonProperty("market_price")
    @Column(name = "market_price")
    private BigDecimal marketPrice;

    @ApiModelProperty(value = "小区均价")
    @JSONField(name = "average_price")
    @JsonProperty("average_price")
    @Column(name = "average_price")
    private BigDecimal averagePrice;

    @ApiModelProperty(value = "价格")
    @JSONField(name = "price")
    @JsonProperty("price")
    @Column(name = "price")
    private BigDecimal price;

    @ApiModelProperty(value = "总户数")
    @JSONField(name = "house_num")
    @JsonProperty("house_num")
    @Column(name = "house_num")
    private Integer houseNum;

    @ApiModelProperty(value = "居住人口")
    @JSONField(name = "person_num")
    @JsonProperty("person_num")
    @Column(name = "person_num")
    private Integer personNum;

    @ApiModelProperty(value = "月曝光人流量")
    @JSONField(name = "month_person_flow")
    @JsonProperty("month_person_flow")
    @Column(name = "month_person_flow")
    private Integer monthPersonFlow;

    @ApiModelProperty(value = "可投放广告幅数")
    @JSONField(name = "advice_num")
    @JsonProperty("advice_num")
    @Column(name = "advice_num")
    private Integer adviceNum;

    @ApiModelProperty(value = "入住时间")
    @JSONField(name = "check_in_date")
    @JsonProperty("check_in_date")
    @Column(name = "check_in_date")
    private Date checkInDate;

    @ApiModelProperty(value = "小区平均年龄")
    @JSONField(name = "average_age")
    @JsonProperty("average_age")
    @Column(name = "average_age")
    private Integer averageAge;

    @ApiModelProperty(value = "banner图路径")
    @JSONField(name = "banner_url")
    @JsonProperty("banner_url")
    @Column(name = "banner_url")
    private String bannerUrl;

    @ApiModelProperty(value = "小区物业人员电话或联系电话")
    @JSONField(name = "principal_phone")
    @JsonProperty("principal_phone")
    @Column(name = "principal_phone")
    private String principalhone;

    @ApiModelProperty(value = "经度")
    @JSONField(name = "longitude_x")
    @JsonProperty("longitude_x")
    @Column(name = "longitude_x")
    private BigDecimal longitudeX;

    @ApiModelProperty(value = "纬度")
    @JSONField(name = "latitude_y")
    @JsonProperty("latitude_y")
    @Column(name = "latitude_y")
    private BigDecimal latitudeY;

    @JSONField(name = "created_by")
    @JsonProperty("created_by")
    @Column(name = "created_by")
    private String createBy;

    @ApiModelProperty(value = "热度")
    @JSONField(name = "heat")
    @JsonProperty("heat")
    @Column(name = "heat")
    private Integer heat;

    @ApiModelProperty(value = "租期开始时间")
    @JSONField(name = "start_time")
    @JsonProperty("start_time")
    @Column(name = "start_time")
    private Date startTime;

    @ApiModelProperty(value = "租期结束时间")
    @JSONField(name = "end_time")
    @JsonProperty("end_time")
    @Column(name = "end_time")
    private Date endTime;

    @ApiModelProperty(value = "省编码")
    @JSONField(name = "province")
    @JsonProperty("province")
    @Column(name = "province")
    private String province;

    @ApiModelProperty(value = "市编码")
    @JSONField(name = "city")
    @JsonProperty("city")
    @Column(name = "city")
    private String city;

    @ApiModelProperty(value = "区(县)编码")
    @JSONField(name = "district")
    @JsonProperty("district")
    @Column(name = "district")
    private String district;

}
