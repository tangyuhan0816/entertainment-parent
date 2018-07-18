package com.entertainment.common.entity.base;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
@ApiModel(value = "基础实体")
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键")
    private Long id;

    @Column(name = "create_date_time", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @ApiModelProperty(value = "创建时间")
    private Date createDateTime;

    @Column(name = "update_date_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @ApiModelProperty(value = "最后更新时间")
    private Date updateDateTime;

    @Column(name = "deleted", nullable = false)
    @JsonIgnore
    @JSONField(serialize = false)
    @ApiModelProperty(value = "删除状态")
    private boolean deleted = false;

    @Column(name = "version")
    @JSONField(name = "version")
    @JsonProperty("version")
    @Version
    @ApiModelProperty(value = "版本号")
    private Long version;
}
