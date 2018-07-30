package com.entertainment.asset.controller.sys;

import com.entertainment.asset.service.emial.MailService;
import com.entertainment.common.utils.ResponseContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


@RestController
public class RegController {


    @Autowired
    private MailService mailService;


    @Value("${spring.mail.username}")
    private String receptionMailAddr;

    //读取配置文件中的参数
    @Value("${spring.mail.username}")
    String sender;

    /**
     * 用户注册
     *
     * @param
     * @return
     */

    @GetMapping("/reg")
    public ResponseContent reg() {
        mailService.sendTextMail(receptionMailAddr,"测试文本邮箱发送","你好你好！");
        return ResponseContent.buildSuccess();
    }

}
