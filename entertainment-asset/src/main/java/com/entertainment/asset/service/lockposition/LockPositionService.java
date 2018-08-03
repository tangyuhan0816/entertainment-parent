package com.entertainment.asset.service.lockposition;


import com.entertainment.asset.dao.lockposition.LockPositionRepository;
import com.entertainment.asset.entity.lockposition.LockPositionApplyFor;
import com.sun.tools.javac.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * class description
 *
 * @author tangyuhan 349910
 * @version v1.0
 *          Date:2018/8/3 15:34 下午
 */
@Service
public class LockPositionService {
    @Autowired
    private LockPositionRepository lockPositionRepository;

    public List<LockPositionApplyFor> findAll(){
        return lockPositionRepository.findAllByDeletedIsFalse();
    }
}
