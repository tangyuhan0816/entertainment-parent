package com.vpis.asset.bean.sys;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpis.asset.bean.base.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: BackPassBean
 *  @package: com.vpis.asset.bean.sys
 *  @Date: Created in 2018/10/23 下午3:14
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 找回密码Bean
 */
@Data
@ApiModel(value = "文章对象")
public class BackPassBean extends BaseBean{

    @ApiModelProperty("旧密码")
    @JSONField(name = "old_pass_word")
    @JsonProperty("old_pass_word")
    private String oldPassWord;

    @ApiModelProperty("验证码")
    @JSONField(name = "sms_code")
    @JsonProperty("sms_code")
    private String smsCode;

    @ApiModelProperty("手机号码")
    @JSONField(name = "phone")
    @JsonProperty("phone")
    private String phone;

    @ApiModelProperty("新密码")
    @JSONField(name = "new_pass_word")
    @JsonProperty("new_pass_word")
    private String newPassWord;

    @ApiModelProperty("确认密码")
    @JSONField(name = "sure_pass_word")
    @JsonProperty("sure_pass_word")
    private String surePassWord;
}
