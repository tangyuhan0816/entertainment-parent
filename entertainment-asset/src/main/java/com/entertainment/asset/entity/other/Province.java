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
@Table(name = "province")
public class Province extends BaseEntity{

    @JSONField(name = "province_id")
    @JsonProperty("province_id")
    @Column(name = "province_id")
    private String provinceId;

    @JSONField(name = "province")
    @JsonProperty("province")
    @Column(name = "province")
    private String provinCe;

}
