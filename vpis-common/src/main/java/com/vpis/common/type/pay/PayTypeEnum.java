package com.vpis.common.type.pay;

import com.vpis.common.exception.STException;
import com.vpis.common.utils.Preconditions;
import lombok.Getter;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: PayTypeEnum
 *  @package: com.vpis.common.type.pay
 *  @Date: Created in 2018/11/18 下午3:17
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
@Getter
public enum PayTypeEnum {

    ALIPAY_APP("alipay_app", "支付宝app", "aliPayService"),
    ALIPAY_PC("alipay_pc", "支付宝pc", ""),
    ALIPAY_WAP("alipay_wap", "支付宝wap", ""),
    WXPAY_APP("wxpay_app", "微信APP支付", "wechatService"),
    WXPAY_MWEB("MWEB", "微信公众账号支付", "");

    private String code;
    private String name;
    private String serviceName;

    PayTypeEnum(String code, String name, String serviceName) {
        this.code = code;
        this.name = name;
        this.serviceName = serviceName;
    }

    public static PayTypeEnum getIndex(Integer index){
        PayTypeEnum payTypeEnum = PayTypeEnum.values()[index];
        if(Preconditions.isBlank(payTypeEnum)){
            throw new STException("index is null");
        }
        return payTypeEnum;
    }
}
