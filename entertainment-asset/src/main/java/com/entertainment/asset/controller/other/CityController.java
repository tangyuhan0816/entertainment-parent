package com.entertainment.asset.controller.other;


import com.entertainment.asset.entity.other.City;
import com.entertainment.asset.service.jwt.JwtService;
import com.entertainment.asset.service.other.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/")
public class CityController {

    @Autowired
    private CityService CityService;

    @Autowired
    private JwtService jwtService;

    private static final String LOGIN_MESSAGE = "login_success";

    @RequestMapping(path = "/getCity", method = {RequestMethod.POST})
    public Object login(HttpServletRequest request, @RequestBody City City){
       List<City> listCity=CityService.findByProvinceId(City.getParentId());
        return listCity;
    }
}
