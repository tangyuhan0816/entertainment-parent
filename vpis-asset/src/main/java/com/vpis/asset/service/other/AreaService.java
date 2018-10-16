//package com.vpis.asset.service.other;
//
//import com.vpis.asset.annotation.CachePutAnnotation;
//import com.vpis.asset.annotation.CacheReadAnnotation;
//import com.vpis.asset.dao.other.AreaRepository;
//import com.vpis.asset.entity.other.Area;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class AreaService{
//
//    @Autowired
//    private AreaRepository areaRepository;
//
//    /**********************使用redis缓存必须使用spring 代理 start**********************/
//    @Autowired
//    private ApplicationContext applicationContext;
//
//    private static final String CACHE_KEY = "AREA";
//
//    protected AreaService getSpringProxy() {
//        return applicationContext.getBean(this.getClass());
//    }
//
//    public Object getByCityKey(Long cityId){
//        return this.getSpringProxy().getByCityKey(CACHE_KEY,cityId);
//    }
//    /**********************使用redis缓存必须使用spring 代理 start**********************/
//
//    @CacheReadAnnotation
//    public List<Area> getByCityKey(String ikey,Long cityId){
//        return synAreaByCityId(cityId);
//    }
//
//    @CachePutAnnotation
//    public List<Area> synAreaByCityId(Long cityId){
//        return findByCityIdFromDB(cityId);
//    }
//
//    public List<Area> findByCityIdFromDB(Long cityId){
//        return  areaRepository.findByCityIdAndDeletedIsFalse(cityId);
//    }
//}
