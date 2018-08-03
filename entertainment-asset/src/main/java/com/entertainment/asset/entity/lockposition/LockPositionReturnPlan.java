package com.entertainment.asset.entity.lockposition;

import com.alibaba.fastjson.annotation.JSONField;
import com.entertainment.common.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.mail.imap.protocol.INTERNALDATE;
import com.sun.tools.javac.code.Attribute;
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
 *          Date:2018/8/3 18:52 下午
 */
@Data
@Entity
@Table(name = "lock_position_return_plan")
public class LockPositionReturnPlan extends BaseEntity{

    @JSONField(name = "lock_position_batch_id")
    @JsonProperty("lock_position_batch_id")
    @Column(name = "lock_position_batch_id")
    private Long lockPositionBatchId;

    @JSONField(name = "return_date")
    @JsonProperty("return_date")
    @Column(name = "return_date")
    private Date returnDate;

    @JSONField(name = "return_amount")
    @JsonProperty("return_amount")
    @Column(name = "return_amount")
    private BigDecimal returnAmount;

    @JSONField(name = "status")
    @JsonProperty("status")
    @Column(name = "status")
    private Enum status;
}
