package com.entertainment.asset.service.other;

import com.entertainment.asset.entity.other.Area;
import com.entertainment.asset.service.base.BaseCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaService extends BaseCacheService<Area>{

    private AreaService areaService;

    public List<Area> findById(String CityId){return  areaService.findById(CityId);}


}
