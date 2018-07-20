package com.entertainment.asset.service.shiro.realm;

import com.entertainment.asset.entity.sys.SysUser;
import com.entertainment.asset.service.jwt.JwtService;
import com.entertainment.asset.service.shiro.token.JwtToken;
import com.entertainment.asset.service.sys.SysUserService;
import com.entertainment.common.constant.BusinessConstant;
import com.entertainment.common.exception.BusinessException;
import com.entertainment.common.utils.Preconditions;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: JwtAuthorizingRealm
 *  @package: com.entertainment.asset.service.shiro.realm
 *  @Date: Created in 2018/7/19 下午7:26
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 
 */    
@Service("jwtAuthorizingRealm")
public class JwtAuthorizingRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthorizingRealm.class);

    @Autowired
    private JwtService jwtService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 重写此方法，使用JWTUsernamePasswordToken
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }


    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String jwtToken = principals.toString();
        String realJwtToken = jwtToken.substring(jwtToken.lastIndexOf(":") + 1, jwtToken.length());
        String username = jwtService.getValueByParams(realJwtToken, "username");
        SysUser sysUser = sysUserService.findSysUserByEmail(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Set<String> permissions = new HashSet<>();
//        // TODO: 2018/4/3 获取权限
        simpleAuthorizationInfo.addStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        JwtToken jwtUsernamePasswordToken = (JwtToken) auth;
        //直接使用jwtToken，身份验证
        String jwtToken = jwtUsernamePasswordToken.getJwtToken();
        if (Preconditions.isBlank(jwtToken)) {
            throw new BusinessException(BusinessConstant.COMMON_VERIFY_NOT_HAVE_THIS_PERMISSION);
        }
        String realJwtToken = jwtToken.substring(jwtToken.lastIndexOf(":") + 1, jwtToken.length());
        if (!jwtService.verifyJwt(realJwtToken)) {
            throw new BusinessException(BusinessConstant.COMMON_VERIFY_NOT_HAVE_THIS_PERMISSION);
        }
        if(!jwtService.checkTokenValid(realJwtToken)){
            throw new BusinessException(BusinessConstant.COMMON_VERIFY_NOT_HAVE_THIS_PERMISSION);
        }
        return new SimpleAuthenticationInfo(jwtToken, jwtToken, getName());
    }

    /**
     * 清除授权缓存
     */
    @Override
    protected void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    /**
     * 清除验证缓存
     */
    @Override
    protected void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    public void clearCachedAuthorizationInfo2(PrincipalCollection principals){
        clearCachedAuthorizationInfo(principals);
    }

}