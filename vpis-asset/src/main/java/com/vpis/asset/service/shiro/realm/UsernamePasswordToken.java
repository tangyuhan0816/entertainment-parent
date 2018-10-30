package com.vpis.asset.service.shiro.realm;

import lombok.Data;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: UsernamePasswordToken
 *  @package: com.vpis.asset.service.shiro.realm
 *  @Date: Created in 2018/10/30 下午6:15
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
@Data
public class UsernamePasswordToken extends org.apache.shiro.authc.UsernamePasswordToken {

    public Integer type;

    public UsernamePasswordToken(String username, String password, Integer type) {
        super(username, password);
        this.type = type;
    }

    public UsernamePasswordToken(String username, String password) {
        super(username, password);
    }
}
