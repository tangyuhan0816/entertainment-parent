package com.vpis.common.entity.sys;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpis.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: ExtendInfo
 *  @package: com.vpis.common.entity.sys
 *  @Date: Created in 2018/10/29 下午7:24
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */

@Data
@Entity
@Table(name = "extend_info")
public class ExtendInfo extends BaseEntity {

    @ApiModelProperty(value = "用户id")
    @JSONField(name = "user_id")
    @JsonProperty("user_id")
    @Column(name = "user_id")
    private Long userId;

    @ApiModelProperty(value = "代理商首页图片")
    @JSONField(name = "agent_banner_url")
    @JsonProperty("agent_banner_url")
    @Column(name = "agent_banner_url")
    private String agentBannerUrl;
}
