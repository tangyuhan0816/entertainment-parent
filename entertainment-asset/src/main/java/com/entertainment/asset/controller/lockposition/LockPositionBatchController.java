package com.entertainment.asset.controller.lockposition;

import com.entertainment.asset.service.lockposition.LockPositionBatchService;
import com.entertainment.common.utils.ResponseContent;
import org.omg.CORBA.Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * class description
 *
 * @author tangyuhan 349910
 * @version v1.0
 *          Date:2018/8/3 19:29 下午
 */
@RestController
@RequestMapping("/v1/asset/lockposition/LockPositionBatch")
public class LockPositionBatchController {
    @Autowired
    private LockPositionBatchService lockPositionBatchService;
    @RequestMapping(path ="/findLockPositionBatchChen/userId/{userId}",method = {RequestMethod.GET})
    public Object findLockPositionBatchy(HttpServletRequest request, @PathVariable(value = "userId") Long userId){
        return (Object) ResponseContent.buildSuccess(lockPositionBatchService.findByUserId(userId));
    }
}
