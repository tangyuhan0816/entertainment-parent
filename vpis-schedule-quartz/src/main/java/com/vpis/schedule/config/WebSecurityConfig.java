package com.vpis.schedule.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: WebSecurityConfig
 *  @package: com.entertainment.asset.config
 *  @Date: Created in 2018/7/18 下午7:08
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     *页面的安全请求验证
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/webjars/**").antMatchers("/job/**");
    }

}
