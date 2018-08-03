package com.entertainment.asset.entity.lockposition;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.BigDecimalCodec;
import com.entertainment.common.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * class description
 *
 * @author tangyuhan 349910
 * @version v1.0
 *          Date:2018/8/3 18:35 下午
 */
@Data
@Entity
@Table(name = "lock_position_batch")
public class LockPositionBatch extends BaseEntity {

    @JSONField(name = "lock_position_address")
    @JsonProperty("lock_position_address")
    @Column(name = "lock_position_address")
    private String lockPositionAddress;

    @JSONField(name = "lock_position_batch")
    @JsonProperty("lock_position_batch")
    @Column(name = "lock_position_batch")
    private String lockPositionBatch;

    @JSONField(name = "lock_position_amount")
    @JsonProperty("lock_position_amount")
    @Column(name = "lock_position_amount")
    private BigDecimal lockPositionAmount;

    @JSONField(name = "start_date")
    @JsonProperty("start_date")
    @Column(name = "start_date")
    private Date startDate;

    @JSONField(name = "user_id")
    @JsonProperty("user_id")
    @Column(name = "user_id")
    private Long userId;
}
