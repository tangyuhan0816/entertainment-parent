package com.entertainment.asset.controller.sys;

import com.alibaba.fastjson.JSONObject;
import com.entertainment.asset.bean.LoginBean;
import com.entertainment.asset.bean.sys.RegisterBean;
import com.entertainment.asset.controller.GlobalExceptionHandler;
import com.entertainment.asset.entity.sys.SysUser;
import com.entertainment.asset.entity.sys.TbUser;
import com.entertainment.asset.service.jwt.JwtService;
import com.entertainment.asset.service.sys.SysUserService;
import com.entertainment.common.exception.STException;
import com.entertainment.common.utils.ResponseContent;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private JwtService jwtService;

    private static final String LOGIN_MESSAGE = "login_success";

    @RequestMapping(path = "/login", method = {RequestMethod.POST})
    public ResponseContent login(HttpServletRequest request, @RequestBody LoginBean sysUser){
        logger.info("login 请求参数:{}", JSONObject.toJSONString(sysUser));
        UsernamePasswordToken token = new UsernamePasswordToken(sysUser.getPhone(), sysUser.getPassword());
        SecurityUtils.getSubject().login(token);
        TbUser user = sysUserService.findByPhone(sysUser.getPhone());
        return ResponseContent.buildSuccess(LOGIN_MESSAGE,jwtService.createJwt(user));
    }

    @RequestMapping(path = "/send", method = {RequestMethod.POST})
    public ResponseContent send(@RequestParam("phone") String phone,
                                @RequestParam("zone")String zone){
        try{
            logger.info("send 请求参数:{},{}", phone,zone);
            sysUserService.send(phone,zone);
        }catch(STException e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(e.getMessage());
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(e.getMessage());
        }
        return ResponseContent.buildSuccess();
    }

    @RequestMapping(path = "/register", method = {RequestMethod.POST})
    public ResponseContent register(HttpServletRequest request,
                                    @RequestBody RegisterBean registerBean) {
        try{
            logger.info("register 请求参数:{}", registerBean);
            sysUserService.register(registerBean);
        }catch(STException e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(e.getMessage());
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(e.getMessage());
        }
        return ResponseContent.buildSuccess();
    }
}
