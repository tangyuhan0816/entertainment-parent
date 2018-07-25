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
@Table(name = "city")
public class City extends BaseEntity{

    @JSONField(name = "city_id")
    @JsonProperty("city_id")
    @Column(name = "city_id")
    private String cityId;

    @JSONField(name = "city")
    @JsonProperty("city")
    @Column(name = "city")
    private String ciTy;


    @JSONField(name = "parent_id")
    @JsonProperty("parent_id")
    @Column(name = "parent_id")
    private String parentId;


}
