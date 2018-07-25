package com.entertainment.asset.service.other;

import com.entertainment.asset.dao.other.AreaRepository;
import com.entertainment.asset.entity.other.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaService{

    @Autowired
    private AreaRepository areaRepository;

    public List<Area> findByCityId(Long cityId){
        return  areaRepository.findByCityIdAndDeletedIsFalse(cityId);
    }


}
