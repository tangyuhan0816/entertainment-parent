package com.vpis.common.entity.sys;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpis.common.base.BaseEntity;
import com.vpis.common.type.sys.UserStatus;
import com.vpis.common.type.sys.UserTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
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
@Entity
@Table(name = "tb_user")
public class TbUser extends BaseEntity{

    @ApiModelProperty(value = "权限ID", required = true)
    @JSONField(name = "role_id")
    @JsonProperty("role_id")
    @Column(name = "role_id")
    private Long roleId;

    @ApiModelProperty(value = "用户名", required = true)
    @JSONField(name = "user_name")
    @JsonProperty("user_name")
    @Column(name = "user_name")
    private String userName;

    @ApiModelProperty(value = "密码", required = true)
    @JSONField(name = "pass_word")
    @JsonProperty("pass_word")
    @Column(name = "pass_word")
    private String passWord;

    @ApiModelProperty(value = "昵称", required = true)
    @JSONField(name = "nick")
    @JsonProperty("nick")
    @Column(name = "nick")
    private String nick;

    @ApiModelProperty(value = "管理员ID", required = true)
    @JSONField(name = "parent_id")
    @JsonProperty("parent_id")
    @Column(name = "parent_id")
    private Long parentId;

    @ApiModelProperty(value = "邮箱地址", required = true)
    @JSONField(name = "email")
    @JsonProperty("email")
    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.ORDINAL)
    @ApiModelProperty(value = "状态", required = true)
    @JSONField(name = "status")
    @JsonProperty("status")
    @Column(name = "status")
    private UserStatus status;

    @Enumerated(EnumType.ORDINAL)
    @ApiModelProperty(value = "用户类型", required = true)
    @JSONField(name = "user_type")
    @JsonProperty("user_type")
    @Column(name = "user_type")
    private UserTypeEnum userType;

    @ApiModelProperty(value = "手机号码", required = true)
    @JSONField(name = "phone_num")
    @JsonProperty("phone_num")
    @Column(name = "phone_num")
    private String phoneNum;

    @ApiModelProperty(value = "区域编号", required = true)
    @JSONField(name = "agent_area")
    @JsonProperty("agent_area")
    @Column(name = "agent_area")
    private String agentArea;

    @ApiModelProperty(value = "代理商公司名称", required = true)
    @JSONField(name = "agent_company_name")
    @JsonProperty("agent_company_name")
    @Column(name = "agent_company_name")
    private String agentCompanyName;

    @ApiModelProperty(value = "代理商公司法人名称", required = true)
    @JSONField(name = "agent_legal_name")
    @JsonProperty("agent_legal_name")
    @Column(name = "agent_legal_name")
    private String agentLegalName;

    @ApiModelProperty(value = "代理商负责人电话", required = true)
    @JSONField(name = "agent_custom_phone")
    @JsonProperty("agent_custom_phone")
    @Column(name = "agent_custom_phone")
    private String agentCustomPhone;

    @ApiModelProperty(value = "权利", required = true)
    @JSONField(name = "rights")
    @JsonProperty("rights")
    @Column(name = "rights")
    private String rights;

    @ApiModelProperty(value = "性别", required = true)
    @JSONField(name = "sex")
    @JsonProperty("sex")
    @Column(name = "sex")
    private Integer sex;

    @ApiModelProperty(value = "图标", required = true)
    @JSONField(name = "auto_graph")
    @JsonProperty("auto_graph")
    @Column(name = "auto_graph")
    private String autoGraph;

    @ApiModelProperty(value = "头像地址", required = true)
    @JSONField(name = "header_icon")
    @JsonProperty("header_icon")
    @Column(name = "header_icon")
    private String headerIcon;

    @ApiModelProperty(value = "地址", required = true)
    @JSONField(name = "address")
    @JsonProperty("address")
    @Column(name = "address")
    private Date address;
}
