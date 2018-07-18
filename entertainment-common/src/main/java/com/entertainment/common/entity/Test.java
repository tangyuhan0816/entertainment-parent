package com.entertainment.common.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.entertainment.common.entity.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "test")
public class Test extends BaseEntity {


    @JSONField(name = "media_ad_key")
    @JsonProperty("media_ad_key")
    @Column(name = "media_ad_key")
    private String mediaAdKey;


    @JSONField(name = "ad_num_setting_id")
    @JsonProperty("ad_num_setting_id")
    @Column(name = "ad_num_setting_id")
    private Long adNumSettingId;
}
