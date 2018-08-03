package com.entertainment.asset.service.lockposition;

import com.entertainment.asset.dao.lockposition.LockPositionBatchRenpository;
import com.entertainment.asset.entity.lockposition.LockPositionBatch;
import com.sun.tools.javac.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * class description
 *
 * @author tangyuhan 349910
 * @version v1.0
 *          Date:2018/8/3 19:18 下午
 */
@Service
public class LockPositionBatchService {
    @Autowired
    private LockPositionBatchRenpository lockPositionBatchRenpository;

    public List<LockPositionBatch> findByUserId(Long userId){
        return (List<LockPositionBatch>) lockPositionBatchRenpository.findByUserIdAndDeletedIsFalse(userId);
    }

}
