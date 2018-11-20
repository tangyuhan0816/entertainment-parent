package com.vpis.common.entity.pay.request.alipay;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: BasePayRequest
 *  @package: com.vpis.common.entity.pay.request.alipay
 *  @Date: Created in 2018/11/20 上午11:56
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 阿里支付请求公共类
 */
@Data
public class BasePayRequest implements Serializable {

    @JSONField(name = "app_id")
    @JsonProperty("app_id")
    private String appId;

    @JSONField(name = "method")
    @JsonProperty("method")
    private String method;

    @JSONField(name = "format")
    @JsonProperty("format")
    private String format;

    @JSONField(name = "return_url")
    @JsonProperty("return_url")
    private String returnUrl;

    @JSONField(name = "charset")
    @JsonProperty("charset")
    private String charset;

    @JSONField(name = "sign_type")
    @JsonProperty("sign_type")
    private String signType;

    @JSONField(name = "sign")
    @JsonProperty("sign")
    private String sign;

    @JSONField(name = "timestamp")
    @JsonProperty("timestamp")
    private String timestamp;

    @JSONField(name = "version")
    @JsonProperty("version")
    private String version;

    @JSONField(name = "notify_url")
    @JsonProperty("notify_url")
    private String notifyUrl;

    @JSONField(name = "app_auth_token")
    @JsonProperty("app_auth_token")
    private String appAuthToken;

    @JSONField(name = "biz_content")
    @JsonProperty("biz_content")
    private String bizContent;

}
