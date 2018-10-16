package com.vpis.asset.dao.other;

import com.vpis.asset.entity.other.Area;
import com.vpis.common.base.BaseEntityRepository;

import java.util.List;

public interface AreaRepository extends BaseEntityRepository<Area>{

    /**
     * 根据所在市下的所有区
     * @param areaId
     * @return
     */
    List<Area> findByCityIdAndDeletedIsFalse(Long areaId);
}
