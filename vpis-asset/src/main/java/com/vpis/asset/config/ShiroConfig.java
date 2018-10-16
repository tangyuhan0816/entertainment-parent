package com.vpis.asset.config;

import com.vpis.asset.service.shiro.filter.CustomModularRealmAuthenticator;
import com.vpis.asset.service.shiro.filter.JwtHttpAuthenticationFilter;
import com.vpis.asset.service.shiro.realm.JwtAuthorizingRealm;
import com.vpis.asset.service.shiro.realm.UsernamePasswordAuthorizingRealm;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import javax.servlet.Filter;
import java.util.*;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: ShiroConfig
 *  @package: com.entertainment.asset.config
 *  @Date: Created in 2018/7/20 下午12:52
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: shiro config 配置
 */
@Configuration
public class ShiroConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.database}")
    private int database;


    @Bean("securityManager")
    public DefaultWebSecurityManager getManager(JwtAuthorizingRealm jwtAuthorizingRealm, UsernamePasswordAuthorizingRealm usernamePasswordAuthorizingRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setAuthenticator(modularRealmAuthenticator());
        // 使用自己的realm
        List<Realm> realms = new ArrayList<>();
        realms.add(jwtAuthorizingRealm);
        realms.add(usernamePasswordAuthorizingRealm);
        manager.setRealms(realms);
        //使用redis缓存
        manager.setCacheManager(cacheManager());
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        manager.setSubjectDAO(subjectDAO);
        return manager;
    }


    @Bean("shiroFilter")
    public ShiroFilterFactoryBean factory(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

        // 添加自己的过滤器并且取名为jwt
        Map<String, Filter> filterMap = new HashMap<>(12);
        filterMap.put("jwtFilter", jwtHttpAuthenticationFilter());
        factoryBean.setFilters(filterMap);

        factoryBean.setSecurityManager(securityManager);
        factoryBean.setLoginUrl("/login");
        factoryBean.setUnauthorizedUrl("/401");

            /*
             * 自定义url规则
             * http://shiro.apache.org/web.html#urls-
             */
        Map<String, String> filterRuleMap = new LinkedHashMap<>();
        // 所有请求通过我们自己的JWT Filter

        filterRuleMap.put("/401", "anon");
        filterRuleMap.put("/favicon.*", "anon");
        //监控暂时全部放开TODO后面加上验证


        // 访问401和404页面不通过我们的Filter
        filterRuleMap.put("/login", "anon");
        filterRuleMap.put("/regregister", "anon");
        filterRuleMap.put("/send", "anon");
        filterRuleMap.put("/sendLogin", "anon");
        filterRuleMap.put("/register/**", "anon");
        filterRuleMap.put("/v2/api-docs", "anon");

        filterRuleMap.put("/swagger-ui.html", "anon");
        filterRuleMap.put("/swagger*/**", "anon");

        filterRuleMap.put("/webjars/**", "anon");
        filterRuleMap.put("/**", "jwtFilter");
        factoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return factoryBean;
    }

    @Bean
    public JwtHttpAuthenticationFilter jwtHttpAuthenticationFilter(){
        return new JwtHttpAuthenticationFilter();
    }


    /**
     * 自定义Realm管理，主要针对多realm
     * */
    @Bean
    public ModularRealmAuthenticator modularRealmAuthenticator(){
        CustomModularRealmAuthenticator modularRealmAuthenticator = new CustomModularRealmAuthenticator();
        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return modularRealmAuthenticator;
    }

    /**
     * 解决自定义的filter不能spring bean 管理的问题
     * http://www.hillfly.com/2017/179.html/comment-page-1
     * @param jwtHttpAuthenticationFilter
     * @return
     */
    @Bean
    public FilterRegistrationBean registration(JwtHttpAuthenticationFilter jwtHttpAuthenticationFilter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(jwtHttpAuthenticationFilter);
        registration.setEnabled(false);
        return registration;
    }

    /**
     * 配置shiro redisManager
     * @return
     */
    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        // 配置缓存过期时间
        redisManager.setTimeout(timeout);
        redisManager.setPassword(password);
        redisManager.setDatabase(database);
        return redisManager;
    }


    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    @Bean
    public SessionDAO sessionDAO(){
        return redisSessionDAO();
    }

    /**
     * 下面的代码是添加注解支持
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        // https://zhuanlan.zhihu.com/p/29161098
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }


    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     * @return
     */
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}