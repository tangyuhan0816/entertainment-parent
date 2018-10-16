package com.entertainment.common.type;

import lombok.Getter;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: EmailSendStatus
 *  @package: com.entertainment.common.type
 *  @Date: Created in 2018/8/3 下午4:35
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 
 */    
@Getter
public enum EmailSendStatus {

    /**
     * 邮箱验证中
     */
    EMAIL_VALIDATION_ING(10,"邮箱验证中"),

    /**
     * 邮件验证完成
     */
    EMAIL_VALIDATION_FAIL(20,"邮箱验证失败"),

    /**
     * 邮件验证完成
     */
    EMAIL_VALIDATION_SUCCEE(30,"邮箱验证成功");


    EmailSendStatus(int value, String text) {
        this.value = value;
        this.text = text;
    }

    private int value;
    private String text;
}
