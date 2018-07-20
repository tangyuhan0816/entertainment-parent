package com.entertainment.asset.controller.sys;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sysUser")
public class SysUserController {

//    @RequestMapping("/login")
//    public Object login(HttpServletRequest request,
//                        @RequestBody SysUser sysUser){
//        UsernamePasswordToken token = new UsernamePasswordToken(sysUser.getEmail(),sysUser.getPassWord());
//        SecurityUtils.getSubject().login(token);
//        return null;
//    }
}
