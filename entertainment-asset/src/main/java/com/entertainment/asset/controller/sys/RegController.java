package com.entertainment.asset.controller.sys;

import com.entertainment.asset.entity.sys.SysUser;
import com.entertainment.asset.service.sys.SysUserService;
import com.entertainment.asset.config.EmailUtil;
import com.entertainment.common.utils.ResponseContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;


/**
 * @Author: Yuhan.Tang
 * @ClassName: SysUserController
 * @package: com.entertainment.asset.controller.sys
 * @Date: Created in 2018/7/20 下午6:03
 * @email yuhan.tang@magicwindow.cn
 * @Description:
 */
@RestController
public class RegController {

    @Autowired
    private SysUserService sysUserService;



    /**
     * 用户注册
     *
     * @param
     * @return
     */

    @PostMapping("/reg")
    public ResponseContent reg() {
        return ResponseContent.buildSuccess();
    }

}
