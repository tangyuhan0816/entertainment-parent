package com.entertainment.asset.controller.sys;

import com.entertainment.asset.service.sys.SysUserService;
import com.entertainment.common.utils.ResponseContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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
