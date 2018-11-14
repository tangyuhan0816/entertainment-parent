package com.vpis.schedule;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: QuartzApplication
 *  @package: com.vpis.schedule
 *  @Date: Created in 2018/11/13 下午3:02
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: quartz
 */
@ComponentScan("com.vpis.schedule")
@MapperScan("com.vpis.schedule.dao")
@EntityScan(value = "com.vpis.common.*")
@SpringBootApplication
public class QuartzApplication {
    public static void main(String[] args) {

        SpringApplication.run(QuartzApplication.class);
    }
}
