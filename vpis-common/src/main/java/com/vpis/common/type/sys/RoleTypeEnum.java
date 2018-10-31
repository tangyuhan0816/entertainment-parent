package com.vpis.common.type.sys;

import lombok.Getter;

@Getter
public enum RoleTypeEnum {

    ADMIN(10,"系统管理员"),
    SHITOU(20,"视投"),
    AGENT(30,"代理商"),
    OPERATIONS(40,"运维");

    private Integer value;
    private String text;

    RoleTypeEnum(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

}
