package com.entertainment.asset;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: Application
 *  @package: com.entertainment
 *  @Date: Created in 2018/7/18 下午4:39
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */

@ComponentScan(value="com.entertainment.common.*")
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}