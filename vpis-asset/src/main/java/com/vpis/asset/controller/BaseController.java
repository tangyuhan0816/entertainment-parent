package com.vpis.asset.controller;

import com.vpis.asset.bean.SessionUser;
import com.vpis.asset.service.jwt.JwtService;
import com.vpis.asset.service.sys.SysUserService;
import com.vpis.common.entity.sys.TbUser;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public abstract class BaseController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 获取当前登陆的用户信息
     * RequestContextHolder.getRequestAttributes()).getRequest() 子线程无法获取参数，请注意使用
     *
     * @return
     */
    protected SessionUser getSessionUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String authorization = request.getHeader("Authorization");
        String phone = jwtService.getStringValueByParams(authorization, "phone");
        TbUser sysUser = sysUserService.findByPhone(phone);
        SessionUser sessionUser = new SessionUser();
        BeanUtils.copyProperties(sysUser, sessionUser);
        return sessionUser;
    }

    protected String getSessionPhone() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String authorization = request.getHeader("Authorization");
        return jwtService.getStringValueByParams(authorization, "phone");
    }

    protected Long getSessionAgentId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String authorization = request.getHeader("Authorization");
        return jwtService.getLongValueByParams(authorization, "agent_id");
    }

    protected Long getSessionUserId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String authorization = request.getHeader("Authorization");
        return jwtService.getLongValueByParams(authorization, "user_id");
    }

    private static final String UNKNOWN = "unknown";

    public String getIp(HttpServletRequest request) {
        String ipp = request.getRemoteAddr();
        log.info("访问IP request.getRemoteAddr() :{}",request.getRemoteAddr());
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        log.info("访问IP request.getHeader :{}", ip);
        return ip;


    }
}