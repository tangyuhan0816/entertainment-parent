package com.entertainment.asset.service.other;

import com.entertainment.asset.dao.other.CityRepository;
import com.entertainment.asset.entity.other.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService{

    @Autowired
    private CityRepository CityRepository;

    public List<City> findByProvinceId(Long provnceId){
        return  CityRepository.findByProvinceIdAndDeletedIsFalse(provnceId);
    }


}
