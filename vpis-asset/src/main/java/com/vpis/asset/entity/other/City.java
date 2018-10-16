package com.vpis.asset.entity.other;

import com.alibaba.fastjson.annotation.JSONField;
import com.vpis.common.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "city")
public class City extends BaseEntity{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键")
    private Long id;

    @JSONField(name = "city_id")
    @JsonProperty("city_id")
    @Column(name = "city_id")
    private Long cityId;

    @JSONField(name = "city")
    @JsonProperty("city")
    @Column(name = "city")
    private String city;


    @JSONField(name = "provinceId")
    @JsonProperty("provinceId")
    @Column(name = "provinceId")
    private Long provinceId;


}
