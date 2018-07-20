package com.entertainment.asset.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginBean {

    @ApiModelProperty("邮件")
    private String email;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("验证码，预留")
    @JsonProperty("verify_code")
    private String verifyCode;
}
