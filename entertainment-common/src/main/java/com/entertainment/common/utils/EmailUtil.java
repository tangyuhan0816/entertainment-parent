package com.entertainment.common.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailUtil {

    @Autowired
    JavaMailSender javaMailSender;

    //读取配置文件中的参数
    @Value("${spring.mail.username}")
     String sender;
    @Test
    public  void sendRegEmail(){
        sendRegEmail("1183665198@qq.com", (long) 312321);
    }

    @Test
    public  void sendRegEmail(String account,Long userId) {
        SimpleMailMessage message = new SimpleMailMessage();
        // 发送者
        message.setFrom(sender);
        // 接收者
        message.setTo(account);
        //邮件主题
        message.setSubject("主题：文本邮件");
        // 邮件内容
        String text="欢迎注册麻了格机商场💕 请点击链接完成注册";
        message.setText("<a href='http://localhost:15060/sysUser/activation?userId="+userId+">点击完成注册\\/(^o^)/</a>");
        javaMailSender.send(message);
    }
}