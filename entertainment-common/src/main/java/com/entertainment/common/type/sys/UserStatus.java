package com.entertainment.common.type.sys;

import lombok.Getter;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: UserStatus
 *  @package: com.entertainment.common.type.sys
 *  @Date: Created in 2018/10/10 上午11:04
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 用户状态
 */
@Getter
public enum UserStatus {
    NORMAL(10,"正常"),
    FREEZE(20,"冻结");

    private Integer value;
    private String text;

    UserStatus(Integer value, String text) {
        this.value = value;
        this.text = text;
    }
}
