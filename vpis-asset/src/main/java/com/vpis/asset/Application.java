package com.vpis.asset;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;

import javax.servlet.MultipartConfigElement;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: Application
 *  @package: com.entertainment
 *  @Date: Created in 2018/7/18 下午4:39
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */

//@EntityScan(value = "com.vpis.common.*")
@SpringBootApplication
public class Application {

    /**
     * 文件上传配置
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大 KB,MB
        factory.setMaxFileSize("100MB");
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("500MB");
        return factory.createMultipartConfig();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}