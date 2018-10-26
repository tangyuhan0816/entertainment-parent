//package com.vpis.asset.service.other;
//
//import com.vpis.asset.entity.other.City;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class CityService{
//
//    @Autowired
//    private com.vpis.asset.repository.other.CityRepository CityRepository;
//
//    public List<City> findByProvinceId(Long provnceId){
//        return  CityRepository.findByProvinceIdAndDeletedIsFalse(provnceId);
//    }
//
//
//}
