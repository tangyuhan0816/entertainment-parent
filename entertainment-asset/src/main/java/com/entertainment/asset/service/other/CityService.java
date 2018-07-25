package com.entertainment.asset.service.other;

import com.entertainment.asset.dao.other.CityRepository;
import com.entertainment.asset.entity.other.City;
import com.entertainment.asset.service.base.BaseCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService extends BaseCacheService<City>{

    @Autowired
    private CityRepository CityRepository;

    public List<City> findByProvinceId(String ProvnceId){return  CityRepository.findById(ProvnceId); }


}
