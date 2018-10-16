package com.vpis.asset.entity.other;

import com.alibaba.fastjson.annotation.JSONField;
import com.vpis.common.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "area")
public class Area extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键")
    private Long id;

    @JSONField(name = "area_id")
    @JsonProperty("area_id")
    @Column(name = "area_id")
    private Long areaId;

    @JSONField(name = "area")
    @JsonProperty("area")
    @Column(name = "area")
    private String area;


    @JSONField(name = "city_id")
    @JsonProperty("city_id")
    @Column(name = "city_id")
    private Long cityId;


}
