package com.entertainment.asset.dao.other;

import com.entertainment.asset.entity.other.Area;
import com.entertainment.asset.entity.other.City;
import com.entertainment.common.base.BaseEntityRepository;

import java.util.List;

public interface AreaRepository extends BaseEntityRepository<Area>{

    /**
     * 根据所在市下的所有区
     * @param areaId
     * @return
     */
    List<Area> findByCityIdAndDeletedIsFalse(Long areaId);
}
