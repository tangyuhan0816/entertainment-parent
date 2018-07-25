package com.entertainment.asset.service.other;

import com.entertainment.asset.annotation.CacheReadAnnotation;
import com.entertainment.asset.dao.other.AreaRepository;
import com.entertainment.asset.entity.other.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaService{

    @Autowired
    private AreaRepository areaRepository;

    /**********************使用redis缓存必须使用spring 代理 start**********************/
    @Autowired
    private ApplicationContext applicationContext;

    private static final String CACHE_KEY = "AREA";

    protected AreaService getSpringProxy() {
        return applicationContext.getBean(this.getClass());
    }

    public Object getByCityKey(Long cityId){
        return this.getSpringProxy().getByCityKey(CACHE_KEY,cityId);
    }
    /**********************使用redis缓存必须使用spring 代理 start**********************/

    @CacheReadAnnotation
    public Object getByCityKey(String ikey,Long cityId){
        return synAreaByCityId(cityId);
    }

    public Object synAreaByCityId(Long cityId){
        return findByCityIdFomDB(cityId);
    }

    public List<Area> findByCityIdFomDB(Long cityId){
        return  areaRepository.findByCityIdAndDeletedIsFalse(cityId);
    }
}
