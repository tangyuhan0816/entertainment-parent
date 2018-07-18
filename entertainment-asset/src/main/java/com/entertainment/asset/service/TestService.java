package com.entertainment.asset.service;

import com.entertainment.asset.dao.TestRepository;
import com.entertainment.common.entity.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    public Test findByKey(String key){
        return testRepository.findByMediaAdKeyAndDeletedIsFalse(key);
    }
}
