package com.entertainment.common.type;

import lombok.Getter;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: EmailType
 *  @package: com.entertainment.common.type
 *  @Date: Created in 2018/8/3 下午4:19
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 
 */    
@Getter
public enum EmailType {
    /**
     *邮箱注册
     */
    EMAIL_REGISTERED(10,"邮箱注册");

    EmailType(int value, String text) {
        this.value = value;
        this.text = text;
    }

    private int value;
    private String text;
}
