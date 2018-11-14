package com.vpis.schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: QuartzApplication
 *  @package: com.vpis.schedule
 *  @Date: Created in 2018/11/13 下午3:02
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: quartz
 */
@EntityScan(value = "com.vpis.common.*")
@SpringBootApplication
public class QuartzApplication {
    public static void main(String[] args) {

        SpringApplication.run(QuartzApplication.class);
    }
}
