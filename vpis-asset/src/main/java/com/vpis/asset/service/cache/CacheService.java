package com.vpis.asset.service.cache;

import com.vpis.common.utils.ResponseContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    @Autowired
    private RedisTemplate redisTemplateJackson;

    public ResponseContent emailTemplate(String key,String template){
        redisTemplateJackson.opsForValue().set(key,template);
        return ResponseContent.buildSuccess();
    }

    public ResponseContent config(String key,String value){
        return ResponseContent.buildSuccess();
    }
}
