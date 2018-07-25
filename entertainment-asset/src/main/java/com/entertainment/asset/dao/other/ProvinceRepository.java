package com.entertainment.asset.dao.other;

import com.entertainment.asset.entity.other.Province;
import com.entertainment.common.base.BaseEntityRepository;

import java.util.List;

public interface ProvinceRepository extends BaseEntityRepository<Province>{

    /**
     * 查询Deleted = 0 的所有省数据
     * @return
     */
    List<Province> findAllByDeletedIsFalse();
}
