package com.entertainment.asset.api;

import com.entertainment.asset.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @Autowired
    private RedisTemplate redisTemplateJackson;

    @RequestMapping(path = "/find/{ke}" , method = {RequestMethod.GET})
    public Object aaaa(HttpServletRequest request,
                     @PathVariable("ke") String ke){
        return testService.findByKey(ke);
    }

    @RequestMapping(path = "/get/{key}/{value}", method = {RequestMethod.GET})
    public Object get(HttpServletRequest request,
                      @PathVariable("key") String key,
                      @PathVariable("value") String value){
        redisTemplateJackson.opsForValue().set(key,value);
        String keyValue = redisTemplateJackson.opsForValue().get(key).toString();
        return keyValue;
    }
}
