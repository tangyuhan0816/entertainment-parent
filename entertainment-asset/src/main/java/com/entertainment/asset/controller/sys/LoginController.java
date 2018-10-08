package com.entertainment.asset.controller.sys;

import com.entertainment.asset.bean.LoginBean;
import com.entertainment.asset.entity.sys.SysUser;
import com.entertainment.asset.service.jwt.JwtService;
import com.entertainment.asset.service.sys.SysUserService;
import com.entertainment.common.utils.ResponseContent;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: LoginController
 *  @package: com.entertainment.asset.controller.sys
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

    private static final String LOGIN_MESSAGE = "login_success";

    @RequestMapping(path = "/login", method = {RequestMethod.POST})
    public ResponseContent login(HttpServletRequest request, @RequestBody LoginBean sysUser){
        UsernamePasswordToken token = new UsernamePasswordToken(sysUser.getEmail(),sysUser.getPassword());
        SecurityUtils.getSubject().login(token);
        SysUser user = sysUserService.findSysUserByEmail(sysUser.getEmail());
        return ResponseContent.buildSuccess(LOGIN_MESSAGE,jwtService.createJwt(user));
    }

    @RequestMapping(path = "/send", method = {RequestMethod.POST})
    public ResponseContent send(@RequestParam("phone") String phone,
                                @RequestParam("zone")String zone){
        sysUserService.send(phone,zone);
        return ResponseContent.buildSuccess();
    }

    @RequestMapping(path = "/register", method = {RequestMethod.POST})
    public ResponseContent register(HttpServletRequest request,
                                    @RequestBody SysUser sysUser) {

        return ResponseContent.buildSuccess();
    }
}
