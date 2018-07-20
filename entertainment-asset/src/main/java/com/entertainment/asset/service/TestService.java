package com.entertainment.asset.service;

import com.entertainment.asset.dao.TestRepository;
import com.entertainment.asset.entity.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: TestService
 *  @package: com.entertainment.asset.service
 *  @Date: Created in 2018/7/20 下午5:49
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 
 */    
@Service
public class TestService {

    @Autowired
    private TestRepository testRepository;

    public Test findByKey(String key){
        return testRepository.findByMediaAdKeyAndDeletedIsFalse(key);
    }
}
