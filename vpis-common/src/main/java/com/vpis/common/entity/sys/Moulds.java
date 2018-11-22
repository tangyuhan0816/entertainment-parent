package com.vpis.common.entity.sys;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpis.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: Moulds
 *  @package: com.vpis.common.entity.sys
 *  @Date: Created in 2018/10/30 下午7:08
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */

@Data
@Entity
@Table(name = "moulds")
public class Moulds extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键")
    @JSONField(name = "id")
    @JsonProperty("id")
    @Column(name = "id")
    private Long id;

    @ApiModelProperty(value = "用户id")
    @JSONField(name = "user_id")
    @JsonProperty("user_id")
    @Column(name = "user_id")
    private Long userId;

    @ApiModelProperty(value = "模版名称")
    @JSONField(name = "mould_name")
    @JsonProperty("mould_name")
    @Column(name = "mould_name")
    private String mouldName;

    @ApiModelProperty(value = "描述")
    @JSONField(name = "description")
    @JsonProperty("description")
    @Column(name = "description")
    private String description;

    @ApiModelProperty(value = "视频url")
    @JSONField(name = "video_url")
    @JsonProperty("video_url")
    @Column(name = "video_url")
    private String videoUrl;

    @ApiModelProperty(value = "背景url")
    @JSONField(name = "backgroud_url")
    @JsonProperty("backgroud_url")
    @Column(name = "backgroud_url")
    private String backgroudUrl;

    @ApiModelProperty(value = "url")
    @JSONField(name = "picture_url")
    @JsonProperty("picture_url")
    @Column(name = "picture_url")
    private String pictureUrl;

    @ApiModelProperty(value = "模版类型：0通用，1自定义")
    @JSONField(name = "mould_type")
    @JsonProperty("mould_type")
    @Column(name = "mould_type")
    private Integer mouldType;

    @ApiModelProperty(value = "对应MouldCodeType枚举类")
    @JSONField(name = "mould_code")
    @JsonProperty("mould_code")
    @Column(name = "mould_code")
    private Integer mouldCode;

}
