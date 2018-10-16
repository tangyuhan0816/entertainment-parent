package com.vpis.asset.entity.sys;

import com.alibaba.fastjson.annotation.JSONField;
import com.vpis.common.base.BaseEntity;
import com.vpis.common.type.EmailSendStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: SysRegisterEmail
 *  @package: com.entertainment.asset.entity.sys
 *  @Date: Created in 2018/8/3 下午4:37
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 
 */
@Data
@Entity
@Table(name = "sys_register_email")
public class SysRegisterEmail extends BaseEntity{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键")
    private Long id;

    @JSONField(name = "content")
    @JsonProperty("content")
    @Column(name = "content")
    private String content;

    @JSONField(name = "status")
    @JsonProperty("status")
    @Column(name = "status")
    private EmailSendStatus status;

    @JSONField(name = "sys_user_id")
    @JsonProperty("sys_user_id")
    @Column(name = "sys_user_id")
    private Long sysUserId;

    @JSONField(name = "title")
    @JsonProperty("title")
    @Column(name = "title")
    private String title;

    @JSONField(name = "verify_code")
    @JsonProperty("verify_code")
    @Column(name = "verify_code")
    private String verifyCode;
}
