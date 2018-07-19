package com.entertainment.asset.service.shiro.token;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author cuixing
 * @package io.merculet.management.shiro.token
 * @class JWTUsernamePasswordToken
 * @email xing.cui@magicwindow.cn
 * @date 2018/3/29 下午1:48
 * @description jwt token 用于处理jwt的登陆验证
 */

@Data
public class JwtToken implements AuthenticationToken {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    // 密钥
    private String jwtToken;

    public JwtToken(String token) {
        this.jwtToken = token;
    }

    @Override
    public Object getPrincipal() {
        return jwtToken;
    }

    @Override
    public Object getCredentials() {
        return jwtToken;
    }
}