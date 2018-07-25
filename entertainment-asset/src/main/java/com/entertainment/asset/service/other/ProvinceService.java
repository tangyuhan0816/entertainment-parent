package com.entertainment.asset.service.other;

import com.entertainment.asset.dao.other.ProvinceRepository;
import com.entertainment.asset.entity.other.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinceService{

    @Autowired
    private ProvinceRepository provinceRepository;

    public List<Province> findAll(){
        return  provinceRepository.findAllByDeletedIsFalse();
    }

}
