package com.entertainment.asset.bean.sys;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: RegisterBean
 *  @package: com.entertainment.asset.bean.sys
 *  @Date: Created in 2018/10/9 下午4:51
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 注册bean
 */
@Data
public class RegisterBean implements Serializable{

    @ApiModelProperty("手机号")
    @JsonProperty("phone")
    private String phone;

    @ApiModelProperty("验证码")
    @JsonProperty("sms_code")
    private String smsCode;

    @ApiModelProperty("密码")
    @JsonProperty("password")
    private String password;

    @ApiModelProperty("时区")
    @JsonProperty("zone_code")
    private String zoneCode;

    @ApiModelProperty("代理商区域编号")
    @JsonProperty("agent_area")
    private String agentArea;
}
