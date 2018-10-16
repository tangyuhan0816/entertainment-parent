package com.vpis.asset;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: Application
 *  @package: com.entertainment
 *  @Date: Created in 2018/7/18 下午4:39
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */

@EntityScan(value = "com.vpis.common.*")
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}