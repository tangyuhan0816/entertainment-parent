package com.vpis.common.entity.pay.response.wechat;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: PayResponse
 *  @package: com.vpis.common.entity.pay.response.wechat
 *  @Date: Created in 2018/11/18 下午4:31
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
@Data
public class PayResponse {

    @JSONField(name = "appid")
    @JsonProperty("appid")
    private String appId;

    @JSONField(name = "partnerid")
    @JsonProperty("partnerid")
    private String partnerId;

    @JSONField(name = "prepayid")
    @JsonProperty("prepayid")
    private String prepayId;

    @JSONField(name = "package")
    @JsonProperty("package")
    private String packAge;

    @JSONField(name = "noncestr")
    @JsonProperty("noncestr")
    private String nonceStr;

    @JSONField(name = "timestamp")
    @JsonProperty("timestamp")
    private String timeStamp;

    @JSONField(name = "sign")
    @JsonProperty("sign")
    private String paySign;

    @JSONField(name = "body")
    @JsonProperty("body")
    private String body;
}
