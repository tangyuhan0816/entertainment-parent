package com.entertainment.asset.service.other;

import com.entertainment.asset.dao.other.CityRepository;
import com.entertainment.asset.entity.other.City;
import com.entertainment.asset.entity.other.Province;
import com.entertainment.asset.service.base.BaseCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceService extends BaseCacheService<Province>{

    @Autowired
    private ProvinceService ProvinceService;

    public List<Province> findBy(){return  ProvinceService.findBy(); }


}
