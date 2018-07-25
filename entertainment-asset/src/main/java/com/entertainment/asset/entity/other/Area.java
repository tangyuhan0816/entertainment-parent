package com.entertainment.asset.entity.other;

import com.alibaba.fastjson.annotation.JSONField;
import com.entertainment.common.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "area")
public class Area extends BaseEntity{

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
