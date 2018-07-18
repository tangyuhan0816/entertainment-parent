package com.entertainment.asset.config;

import com.entertainment.common.utils.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: WebSecurityConfig
 *  @package: com.entertainment.asset.config
 *  @Date: Created in 2018/7/18 下午7:08
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */    
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

    private  static  final RequestMatcher  SWAGGER_IGNORE_REQUEST_MATCHER = new RequestMatcher() {
        @Override
        public boolean matches(HttpServletRequest httpServletRequest) {
            String referer = httpServletRequest.getHeader("Referer");
            if(Preconditions.isNotBlank(referer) && referer.endsWith("swagger-ui.html")){
                return true;
            }
            return false;
        }
    } ;
    // 设置 HTTP 验证规则
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 关闭csrf验证
        http.csrf().disable()
                // 对请求进行认证
                .authorizeRequests()
                // 所有 / 的所有请求 都放行
                .antMatchers("/").permitAll()
                // 所有 /login 的POST请求 都放行
//                .antMatchers(HttpMethod.POST, "/authen").permitAll()

                // 所有请求需要身份认证
//                .anyRequest().authenticated()
//                .and()

//                .exceptionHandling().accessDeniedHandler(new JwtAccessDeniedHandler())
                ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 使用自定义身份验证组件
//        auth.authenticationProvider(new CustomAuthenticationProvider());
    }

    /**
     * 忽略swagger页面的css和js请求，忽略从swagger页面的安全请求验证
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/webjars/**").antMatchers("/swagger*/**").antMatchers("/v2/api-docs")
        .requestMatchers(SWAGGER_IGNORE_REQUEST_MATCHER);
    }

}
