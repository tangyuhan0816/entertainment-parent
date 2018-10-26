package com.vpis.common.entity.house;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpis.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: Houses
 *  @package: com.vpis.common.entity.house
 *  @Date: Created in 2018/10/26 上午11:13
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 楼盘
 */
@Data
@Entity
@Table(name = "houses")
public class Houses extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键")
    @JSONField(name = "house_id")
    @JsonProperty("house_id")
    @Column(name = "house_id")
    private Long houseId;

    @JSONField(name = "user_id")
    @JsonProperty("user_id")
    @Column(name = "user_id")
    private Long userId;

    @ApiModelProperty(value = "楼宇名称")
    @JSONField(name = "house_name")
    @JsonProperty("house_name")
    @Column(name = "house_name")
    private String houseName;

    @ApiModelProperty(value = "均价")
    @JSONField(name = "average_price")
    @JsonProperty("average_price")
    @Column(name = "average_price")
    private BigDecimal averagePrice;

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

}
