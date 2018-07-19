package com.entertainment.asset.service.shiro.token;

import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: JwtToken
 *  @package: com.entertainment.asset.service.shiro.token
 *  @Date: Created in 2018/7/19 下午7:27
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 
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