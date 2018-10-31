package com.vpis.asset.controller;

import com.vpis.asset.bean.SessionUser;
import com.vpis.asset.service.jwt.JwtService;
import com.vpis.asset.service.sys.SysUserService;
import com.vpis.common.entity.sys.TbUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: BaseController
 *  @package: com.vpis.asset.controller
 *  @Date: Created in 2018/10/30 下午3:43
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
public abstract class BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private JwtService jwtService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 获取当前登陆的用户信息
     * RequestContextHolder.getRequestAttributes()).getRequest() 子线程无法获取参数，请注意使用
     * @return
     */
    protected SessionUser getSessionUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String authorization = request.getHeader("Authorization");
        String phone = jwtService.getStringValueByParams(authorization,"phone");
        TbUser sysUser = sysUserService.findByPhone(phone);
        SessionUser sessionUser = new SessionUser();
        BeanUtils.copyProperties(sysUser,sessionUser);
        return sessionUser;
    }

    protected String getSessionPhone(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String authorization = request.getHeader("Authorization");
        return jwtService.getStringValueByParams(authorization,"phone");
    }

    protected Long getSessionAgentId(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String authorization = request.getHeader("Authorization");
        return jwtService.getLongValueByParams(authorization,"agent_id");
    }

    protected Long getSessionUserId(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String authorization = request.getHeader("Authorization");
        return jwtService.getLongValueByParams(authorization,"user_id");
    }

}
