package com.vpis.asset.bean.sys;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpis.asset.bean.base.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: RegisterBean
 *  @package: com.entertainment.asset.bean.sys
 *  @Date: Created in 2018/10/9 下午4:51
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 注册bean
 */
@ApiModel(value = "注册登陆通用Bean")
@Data
public class RegisterBean extends BaseBean{

    @ApiModelProperty("手机号")
    @JsonProperty("phone")
    private String phone;

    @ApiModelProperty("验证码")
    @JsonProperty("sms_code")
    private String smsCode;

    @ApiModelProperty("密码")
    @JsonProperty("password")
    private String password;

    @ApiModelProperty("代理商区域编号")
    @JsonProperty("agent_area")
    private String agentArea;
}
