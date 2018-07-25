package com.entertainment.asset.dao.other;

import com.entertainment.asset.entity.other.City;
import com.entertainment.common.base.BaseEntityRepository;

import java.util.List;

public interface CityRepository extends BaseEntityRepository<City>{

     /**
      * 查询所在省下的所有市
      * @param provnceId
      * @return
      */
     List<City> findByProvinceIdAndDeletedIsFalse(Long provnceId);
}
