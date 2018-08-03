package com.entertainment.asset.dao.lockposition;

import com.entertainment.asset.entity.lockposition.LockPositionReturnPlan;
import com.entertainment.common.base.BaseEntityRepository;

import java.util.List;

/**
 * class description
 *
 * @author tangyuhan 349910
 * @version v1.0
 *          Date:2018/8/3 19:17 下午
 */
public interface LockPositionReturnPlanRenpository extends BaseEntityRepository<LockPositionReturnPlan>{

    List<LockPositionReturnPlan> findByUserIdAndDeletedIsFalse(Long userId);
}
