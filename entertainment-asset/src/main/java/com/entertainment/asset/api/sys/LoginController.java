package com.entertainment.asset.api.sys;

import com.entertainment.asset.entity.sys.SysUser;
import com.entertainment.asset.service.jwt.JwtService;
import com.entertainment.asset.service.sys.SysUserService;
import com.entertainment.common.utils.ResponseContent;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: LoginController
 *  @package: com.entertainment.asset.api.sys
 *  @Date: Created in 2018/7/20 下午12:06
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 
 */    
@RestController
@RequestMapping("/")
public class LoginController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private JwtService jwtService;

    @RequestMapping(path = "/login", method = {RequestMethod.POST})
    public Object login(HttpServletRequest request, @RequestBody SysUser sysUser){
        UsernamePasswordToken token = new UsernamePasswordToken(sysUser.getEmail(),sysUser.getPassWord());
        SecurityUtils.getSubject().login(token);
        SysUser user = sysUserService.findSysUserByEmail(sysUser.getEmail());
        return ResponseContent.buildSuccess(jwtService.createJwt(user));
    }
}
