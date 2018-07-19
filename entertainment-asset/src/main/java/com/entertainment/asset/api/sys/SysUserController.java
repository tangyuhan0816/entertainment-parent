package com.entertainment.asset.api.sys;

import com.entertainment.asset.entity.sys.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    @RequestMapping("/login")
    public Object login(HttpServletRequest request,
                        @RequestBody SysUser sysUser){
        UsernamePasswordToken token = new UsernamePasswordToken(sysUser.getEmail(),sysUser.getPassWord());
        SecurityUtils.getSubject().login(token);
        return null;
    }
}
