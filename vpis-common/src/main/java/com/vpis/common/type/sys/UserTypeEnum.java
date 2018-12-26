package com.vpis.common.type.sys;

import lombok.Getter;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: UserTypeEnum
 *  @package: com.entertainment.common.type.sys
 *  @Date: Created in 2018/10/10 上午11:01
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: UserType 用户类型
 */
@Getter
public enum  UserTypeEnum {

    SUPER_ADMIN_USER(10,"系统管理员"),
    GENERAL_USER(20,"普通用户"),
    AGENT_USER(30,"代理商用户");

    private Integer value;
    private String text;

    UserTypeEnum(Integer value, String text) {
        this.value = value;
        this.text = text;
    }
}
