package com.entertainment.asset.service.shiro.realm;

import com.entertainment.asset.entity.sys.SysUser;
import com.entertainment.asset.service.sys.SysUserService;
import com.entertainment.common.utils.Preconditions;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: UsernamePasswordAuthorizingRealm
 *  @package: com.entertainment.asset.service.shiro.realm
 *  @Date: Created in 2018/7/19 下午7:26
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 
 */    
@Service
public class UsernamePasswordAuthorizingRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(UsernamePasswordAuthorizingRealm.class);

    @Autowired
    private SysUserService sysUserService;

    @Value("${shiro.credentialsSalt}")
    private String credentialsSalt;

    /**
     * 不用查询权限信息，放到jwt realm授权方法种处理
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) auth;

        String username = usernamePasswordToken.getUsername();
        if (StringUtils.isEmpty(username)) {
            throw new RuntimeException("not found username");
        }

        SysUser sysUser = sysUserService.findSysUserByEmail(username);
        if (Preconditions.isBlank(sysUser)) {
            throw new RuntimeException("not found");
        }
        return new SimpleAuthenticationInfo(sysUser, sysUser.getPassWord(), ByteSource.Util.bytes(credentialsSalt), getName());
    }

    /**
     * 重写密码验证的方法
     * @param credentialsMatcher
     */
    //TODO: 加盐串
//    @Override
//    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
//        // 重写 setCredentialsMatcher 方法为自定义的 Realm 设置 hash 验证方法
//        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
//        hashedCredentialsMatcher.setHashIterations(10); //加密次数
//        super.setCredentialsMatcher(hashedCredentialsMatcher);
//    }

}