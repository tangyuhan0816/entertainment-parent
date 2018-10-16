package com.vpis.asset.controller.sys;

import com.vpis.asset.service.email.EmailTemplateService;
import com.vpis.asset.service.sys.SysUserService;
import com.vpis.common.page.PageableRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private EmailTemplateService emailTemplateService;

    @PostMapping("/findByPage")
    public Object login(@RequestBody final PageableRequest pageableRequest) {
        return sysUserService.findByPage(pageableRequest);
    }
}
