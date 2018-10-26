package com.vpis.asset.bean.sys;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpis.asset.bean.base.BaseBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: LoginBean
 *  @package: com.entertainment.asset.bean
 *  @Date: Created in 2018/7/20 下午5:48
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
@Data
public class LoginBean extends BaseBean{

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("验证码")
    @JsonProperty("sms_code")
    private String smsCode;
}
