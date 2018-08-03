package com.entertainment.asset.dao.other;

import com.entertainment.common.base.BaseEntityRepository;
import com.sun.tools.javac.util.List;

/**
 * class description
 *
 * @author tangyuhan 349910
 * @version v1.0
 *          Date:2018/8/3 15:23 下午
 */
public interface LockPositionRepository extends BaseEntityRepository<LockPosition> {

    List<LockPosition> findAllByDeletedIsFalse();
}
