package com.vpis.asset.controller.sys;

import com.alibaba.fastjson.JSONObject;
import com.vpis.asset.bean.sys.BackPassBean;
import com.vpis.asset.bean.sys.RegisterBean;
import com.vpis.asset.constant.RedisConstant;
import com.vpis.asset.service.jwt.JwtService;
import com.vpis.asset.service.sys.SendSmsService;
import com.vpis.asset.service.sys.SysUserService;
import com.vpis.common.entity.sys.TbUser;
import com.vpis.common.exception.STException;
import com.vpis.common.utils.Preconditions;
import com.vpis.common.utils.ResponseContent;
import io.swagger.annotations.ApiOperation;
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
    private SendSmsService sendSmsService;

    @Autowired
    private JwtService jwtService;

    private static final String LOGIN_MESSAGE = "login_success";

    @ApiOperation(value = "登陆(支持验证码或密码) ，Owner: yuhan.tang")
    @RequestMapping(path = "/login", method = {RequestMethod.POST})
    public ResponseContent login(HttpServletRequest request, @RequestBody RegisterBean sysUser){
        logger.info("login 请求参数:{}", JSONObject.toJSONString(sysUser));
        UsernamePasswordToken token = null;
        if(Preconditions.isNotBlank(sysUser.getSmsCode())){
            sendSmsService.checkVerifyCode(sysUser.getSmsCode(),sysUser.getPhone(), RedisConstant.PREFIX_LOGIN_VERIFY_CODE_KEY);
            TbUser tbUser = sysUserService.findByPhone(sysUser.getPhone());
            token = new UsernamePasswordToken(sysUser.getPhone(), tbUser.getPassWord());
        }else {
            token = new UsernamePasswordToken(sysUser.getPhone(), sysUser.getPassword());
        }
        SecurityUtils.getSubject().login(token);
        TbUser user = sysUserService.findByPhone(sysUser.getPhone());
        return ResponseContent.buildSuccess(LOGIN_MESSAGE,jwtService.createJwt(user));
    }

    @ApiOperation(value = "发送注册验证码 ，Owner: yuhan.tang")
    @RequestMapping(path = "/send", method = {RequestMethod.POST})
    public ResponseContent send(@RequestParam("phone") String phone){
        try{
            logger.info("send 请求参数:{},{}", phone);
            sysUserService.send(phone);
        }catch(STException e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(e.getMessage());
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(ResponseContent.INTERNAL_SERVER_ERROR_CODE, e.getMessage());
        }
        return ResponseContent.buildSuccess();
    }

    @ApiOperation(value = "发送登陆验证码 ，Owner: yuhan.tang")
    @RequestMapping(path = "/sendLogin", method = {RequestMethod.POST})
    public ResponseContent sendLogin(@RequestParam("phone") String phone){
        try{
            logger.info("sendLogin 请求参数:{}", phone);
            sysUserService.sendLogin(phone);
        }catch(STException e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(e.getMessage());
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(ResponseContent.INTERNAL_SERVER_ERROR_CODE, e.getMessage());
        }
        return ResponseContent.buildSuccess();
    }

    @ApiOperation(value = "发送找回密码验证码 ，Owner: yuhan.tang")
    @RequestMapping(path = "/sendBack", method = {RequestMethod.POST})
    public ResponseContent sendBack(@RequestParam("phone") String phone){
        try{
            logger.info("sendBack 请求参数:{}", phone);
            sysUserService.sendBack(phone);
        }catch(STException e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(e.getMessage());
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(ResponseContent.INTERNAL_SERVER_ERROR_CODE, e.getMessage());
        }
        return ResponseContent.buildSuccess();
    }

    @ApiOperation(value = "注册 ，Owner: yuhan.tang")
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
            return ResponseContent.buildFail(ResponseContent.INTERNAL_SERVER_ERROR_CODE, e.getMessage());
        }
        return ResponseContent.buildSuccess();
    }

    @ApiOperation(value = "找回密码 ，Owner: yuhan.tang")
    @RequestMapping(path = "/backPass", method = {RequestMethod.POST})
    public ResponseContent backPass(HttpServletRequest request,
                                    @RequestBody BackPassBean bean) {
        try{
            logger.info("backPass 请求参数:{}", bean);
            sysUserService.backPass(bean);
        }catch(STException e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(e.getMessage());
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(ResponseContent.INTERNAL_SERVER_ERROR_CODE, e.getMessage());
        }
        return ResponseContent.buildSuccess();
    }
}
