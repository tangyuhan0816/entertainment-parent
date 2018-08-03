package com.entertainment.asset.entity.lockposition;

import com.alibaba.fastjson.annotation.JSONField;
import com.entertainment.common.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * class description
 *
 * @author tangyuhan 349910
 * @version v1.0
 *          Date:2018/8/3 15:25 下午
 */
@Data
@Entity
@Table(name = "lock_position_apply_for")
public class LockPositionApplyFor extends BaseEntity {
    @JSONField(name = "enterprise_name")
    @JsonProperty("enterprise_name")
    @Column(name = "enterprise_name")
    private String enterpriseName;

    @JSONField(name = "user_name")
    @JsonProperty("user_name")
    @Column(name = "user_name")
    private String userName;

    @JSONField(name = "phone")
    @JsonProperty("phone")
    @Column(name = "phone")
    private Long phone;

    @JSONField(name = "wechat")
    @JsonProperty("wechat")
    @Column(name = "wechat")
    private String wechat;

    @JSONField(name = "email")
    @JsonProperty("email")
    @Column(name = "email")
    private String email;

    @JSONField(name = "user_id")
    @JsonProperty("user_id")
    @Column(name = "user_id")
    private Long userId;
}
