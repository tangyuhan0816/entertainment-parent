package com.entertainment.asset.dao.lockposition;

import com.entertainment.asset.entity.lockposition.LockPositionBatch;
import com.entertainment.common.base.BaseEntityRepository;

import java.util.List;

/**
 * class description
 *
 * @author tangyuhan 349910
 * @version v1.0
 *          Date:2018/8/3 19:11 下午
 */
public interface LockPositionBatchRenpository extends BaseEntityRepository<LockPositionBatch>{

    List<LockPositionBatch> findByUserIdAndDeletedIsFalse(Long userId);
}
