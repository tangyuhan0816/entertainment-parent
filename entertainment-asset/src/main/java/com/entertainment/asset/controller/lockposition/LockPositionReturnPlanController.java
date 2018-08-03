package com.entertainment.asset.controller.lockposition;


import com.entertainment.asset.service.lockposition.LockPositionReturnPlanService;
import com.entertainment.common.utils.ResponseContent;
import org.omg.CORBA.Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * class description
 *
 * @author tangyuhan 349910
 * @version v1.0
 *          Date:2018/8/3 19:41 下午
 */
@RestController
@RequestMapping("/v1/asset/lockposition/LockPositionReturnPlan")
public class LockPositionReturnPlanController {
    @Autowired
    private LockPositionReturnPlanService lockPositionReturnPlanService;
    @RequestMapping(path ="/findLockPositionReturnPlan/userId/{userId}",method = {RequestMethod.GET})
    public Object findLockPositionReturnPlan(HttpServletRequest request, @PathVariable(value = "userId") Long userId){
        return (Object) ResponseContent.buildSuccess(lockPositionReturnPlanService.findByUserId(userId));
    }
}
