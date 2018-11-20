package com.vpis.common.entity.pay.response.alipay;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: AliPayBaseResponse
 *  @package: com.vpis.common.entity.pay.response.alipay
 *  @Date: Created in 2018/11/20 下午12:01
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 阿里支付响应公共类
 */
@Data
public class AliPayBaseResponse implements Serializable {

    @JSONField(name = "code")
    @JsonProperty("code")
    private String code;

    @JSONField(name = "msg")
    @JsonProperty("msg")
    private String msg;

    @JSONField(name = "sub_code")
    @JsonProperty("sub_code")
    private String subCode;

    @JSONField(name = "sub_msg")
    @JsonProperty("sub_msg")
    private String subMsg;

    @JSONField(name = "sign")
    @JsonProperty("sign")
    private String sign;

}
