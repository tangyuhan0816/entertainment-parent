package com.entertainment.asset.dao.other;

import com.entertainment.asset.entity.other.City;
import com.entertainment.common.base.BaseEntityRepository;

import java.util.List;

public interface CityRepository extends BaseEntityRepository<City>{


     List<City> findById(String ProvnceId);
}
