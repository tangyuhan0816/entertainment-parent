package com.entertainment.asset.api;

import com.entertainment.asset.service.TestService;
import com.entertainment.common.entity.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping(path = "/find/{key}" , method = {RequestMethod.GET})
    public Object init(HttpServletRequest request,
                     @RequestParam("key") String key){
        return testService.findByKey(key);
    }
}
