package com.entertainment.asset.api.sys;

import com.alibaba.fastjson.JSONObject;
import com.entertainment.asset.entity.sys.SysUser;
import com.entertainment.asset.service.jwt.JwtService;
import com.entertainment.asset.service.sys.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/")
public class LoginController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private JwtService jwtService;

    @RequestMapping("/login")
    public Object login(HttpServletRequest request, @RequestBody SysUser sysUser){
        UsernamePasswordToken token = new UsernamePasswordToken(sysUser.getEmail(),sysUser.getPassWord());
        token.setRememberMe(true);
        token.setHost("127.0.0.1");
        SecurityUtils.getSubject().login(token);
        SysUser user = sysUserService.findSysUserByEmail(sysUser.getEmail());
        return jwtService.createJwt(user);
    }
}
