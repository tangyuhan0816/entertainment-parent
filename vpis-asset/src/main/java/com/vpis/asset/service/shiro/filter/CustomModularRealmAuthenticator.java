package com.vpis.asset.service.shiro.filter;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: CustomModularRealmAuthenticator
 *  @package: com.entertainment.asset.service.shiro.filter
 *  @Date: Created in 2018/7/19 下午7:27
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 
 */    
public class CustomModularRealmAuthenticator extends ModularRealmAuthenticator {

    private static final Logger logger = LoggerFactory.getLogger(CustomModularRealmAuthenticator.class);


    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        logger.debug("UserModularRealmAuthenticator:method doAuthenticate() execute ");
        // 判断getRealms()是否返回为空
        assertRealmsConfigured();
        // 所有Realm
        Collection<Realm> realms = getRealms();
        Collection<Realm> usedRealms = new ArrayList<>();
        usedRealms.add(getReamlsByJwtToken(realms, authenticationToken));
        logger.debug("doSingleRealmAuthentication() execute ");
        return doSingleRealmAuthentication(usedRealms.iterator().next(), authenticationToken);
    }


    /**
     * 暂时只返回单个reaml
     * @param realms
     * @param authenticationToken
     * @return
     */
    public Realm getReamlsByJwtToken(Collection<Realm> realms, AuthenticationToken authenticationToken){
        if(authenticationToken instanceof UsernamePasswordToken){
            for(Realm realm : realms){
               if(realm.getName().contains("UsernamePassword")) {
                   return realm;
               }
            }
        }else{
            for(Realm realm : realms){
                if(realm.getName().contains("Jwt")) {
                    return realm;
                }
            }
        }
        return null;

    }
}