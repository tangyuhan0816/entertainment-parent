package com.entertainment.asset.entity.sys;

import com.alibaba.fastjson.annotation.JSONField;
import com.entertainment.common.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: TbUser
 *  @package: com.entertainment.asset.entity.sys
 *  @Date: Created in 2018/10/9 下午5:05
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
@Data
public class TbUser extends BaseEntity{

    @JSONField(name = "user_id")
    @JsonProperty("user_id")
    @Column(name = "user_id")
    private Long userId;

    @JSONField(name = "role_id")
    @JsonProperty("role_id")
    @Column(name = "role_id")
    private Long roleId;

    @JSONField(name = "username")
    @JsonProperty("username")
    @Column(name = "username")
    private String username;

    @JSONField(name = "password")
    @JsonProperty("password")
    @Column(name = "password")
    private String password;

    @JSONField(name = "user_pwd")
    @JsonProperty("user_pwd")
    @Column(name = "user_pwd")
    private String userPwd;


    @JSONField(name = "user_nickname")
    @JsonProperty("user_nickname")
    @Column(name = "user_nickname")
    private String userNickname;

    @JSONField(name = "name")
    @JsonProperty("name")
    @Column(name = "name")
    private String name;

    @JSONField(name = "parent_id")
    @JsonProperty("parent_id")
    @Column(name = "parent_id")
    private Integer parentId;

    @JSONField(name = "email")
    @JsonProperty("email")
    @Column(name = "email")
    private String email;

    @JSONField(name = "status")
    @JsonProperty("status")
    @Column(name = "status")
    private String status;

    @JSONField(name = "user_type")
    @JsonProperty("user_type")
    @Column(name = "user_type")
    private String userType;

    @JSONField(name = "phone_num")
    @JsonProperty("phone_num")
    @Column(name = "phone_num")
    private String phoneNum;

    @JSONField(name = "agent_area")
    @JsonProperty("agent_area")
    @Column(name = "agent_area")
    private String agentArea;

    @JSONField(name = "agent_company_name")
    @JsonProperty("agent_company_name")
    @Column(name = "agent_company_name")
    private String agentCompanyName;

    @JSONField(name = "agent_legal_name")
    @JsonProperty("agent_legal_name")
    @Column(name = "agent_legal_name")
    private String agentLegalName;

    @JSONField(name = "agent_custom_phone")
    @JsonProperty("agent_custom_phone")
    @Column(name = "agent_custom_phone")
    private String agentCustomPhone;


    @JSONField(name = "register_time")
    @JsonProperty("register_time")
    @Column(name = "register_time")
    private Date registerTime;

    @JSONField(name = "login_address_recently")
    @JsonProperty("login_address_recently")
    @Column(name = "login_address_recently")
    private String loginAddressRecently;

    @JSONField(name = "login_time_recently")
    @JsonProperty("login_time_recently")
    @Column(name = "login_time_recently")
    private Date loginTimeRecently;

    @JSONField(name = "created_by")
    @JsonProperty("created_by")
    @Column(name = "created_by")
    private String createdBy;

    @JSONField(name = "updated_time")
    @JsonProperty("updated_time")
    @Column(name = "updated_time")
    private Date updatedTime;

    @JSONField(name = "updated_by")
    @JsonProperty("updated_by")
    @Column(name = "updated_by")
    private String updatedBy;

    @JSONField(name = "loginname")
    @JsonProperty("loginname")
    @Column(name = "loginname")
    private String loginname;


    @JSONField(name = "rights")
    @JsonProperty("rights")
    @Column(name = "rights")
    private String rights;


    @JSONField(name = "last_login")
    @JsonProperty("last_login")
    @Column(name = "last_login")
    private Date lastLogin;

    @JSONField(name = "last_login_start")
    @JsonProperty("last_login_start")
    @Column(name = "last_login_start")
    private Date lastLoginStart;

    @JSONField(name = "last_login_end")
    @JsonProperty("last_login_end")
    @Column(name = "last_login_end")
    private Date lastLoginEnd;

    @JSONField(name = "sex")
    @JsonProperty("sex")
    @Column(name = "sex")
    private String sex;
}
