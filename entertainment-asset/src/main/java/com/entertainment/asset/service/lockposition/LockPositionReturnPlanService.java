package com.entertainment.asset.service.lockposition;

import com.entertainment.asset.dao.lockposition.LockPositionReturnPlanRenpository;
import com.entertainment.asset.entity.lockposition.LockPositionReturnPlan;
import com.sun.tools.javac.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * class description
 *
 * @author tangyuhan 349910
 * @version v1.0
 *          Date:2018/8/3 19:26 下午
 */
@Service
public class LockPositionReturnPlanService {
    @Autowired
    private LockPositionReturnPlanRenpository lockPositionReturnPlanRenpository;

    public List<LockPositionReturnPlan> findByUserId(Long userId){
        return (List<LockPositionReturnPlan>) lockPositionReturnPlanRenpository.findByuserIdAndDeletedIsFalse(userId);
    }
}
