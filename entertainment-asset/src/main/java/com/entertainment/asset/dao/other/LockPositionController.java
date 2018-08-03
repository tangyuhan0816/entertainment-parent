package com.entertainment.asset.dao.other;


import org.omg.CORBA.Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * class description
 *
 * @author tangyuhan 349910
 * @version v1.0
 *          Date:2018/8/3 15:22 下午
 */
@RestController
@RequestMapping("/v1/asset/other/LockPosition")
public class LockPositionController {
    @Autowired
    private LockPositionService lockPositionService;

    @RequestMapping(path = "/findlock_position_apply_for",method = {RequestMethod.GET})
    public Object findlock_position_apply_for(HttpServletRequest request){
        return (Object) com.entertainment.common.utils.ResponseContent.buildSuccess(lockPositionService.findAll());
    }
}
