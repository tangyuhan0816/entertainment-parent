package com.vpis.asset.dao.other;

import com.vpis.asset.entity.other.City;
import com.vpis.common.base.BaseEntityRepository;

import java.util.List;

public interface CityRepository extends BaseEntityRepository<City>{

     /**
      * 查询所在省下的所有市
      * @param provnceId
      * @return
      */
     List<City> findByProvinceIdAndDeletedIsFalse(Long provnceId);
}
