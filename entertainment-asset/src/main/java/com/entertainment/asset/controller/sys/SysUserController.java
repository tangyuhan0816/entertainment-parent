package com.entertainment.asset.controller.sys;

import com.entertainment.asset.entity.sys.SysUser;
import com.entertainment.asset.service.sys.SysUserService;
import com.entertainment.common.page.PageableRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 *  @Author: Yuhan.Tang
 *  @ClassName: SysUserController
 *  @package: com.entertainment.asset.controller.sys
 *  @Date: Created in 2018/7/20 下午6:03
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping(path = "/findByPage" , method = {RequestMethod.POST})
    public Object login(HttpServletRequest request,
                        @RequestBody final PageableRequest pageableRequest){

        return sysUserService.findByPage(pageableRequest);
    }
}
