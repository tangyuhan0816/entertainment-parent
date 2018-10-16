package com.vpis.asset.entity.other;

import com.alibaba.fastjson.annotation.JSONField;
import com.vpis.common.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "province")
public class Province extends BaseEntity{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键")
    private Long id;

    @JSONField(name = "province_id")
    @JsonProperty("province_id")
    @Column(name = "province_id")
    private Long provinceId;

    @JSONField(name = "province")
    @JsonProperty("province")
    @Column(name = "province")
    private String province;

}
