package com.entertainment.asset.dao.other;

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
public class LockPosition extends BaseEntity {
    @JSONField(name = "user_id")
    @JsonProperty("user_id")
    @Column(name = "user_id")
    private Long userId;

    @JSONField(name = "lock_position_apply_for")
    @JsonProperty("LockPosition")
    @Column(name = "enterprise_name")
    private String LockPosition;
}
