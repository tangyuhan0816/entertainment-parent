package com.entertainment.asset.controller.other;


import com.entertainment.asset.entity.other.City;
import com.entertainment.asset.entity.other.Province;
import com.entertainment.asset.service.jwt.JwtService;
import com.entertainment.asset.service.other.CityService;
import com.entertainment.asset.service.other.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/")
public class ProvinceController {

    @Autowired
    private ProvinceService ProvinceService;

    @Autowired
    private JwtService jwtService;

    private static final String LOGIN_MESSAGE = "login_success";

    @RequestMapping(path = "/getprovince", method = {RequestMethod.POST})
    public Object login(HttpServletRequest request, @RequestBody Province Province){
       List<Province> listProvince=ProvinceService.findBy();
        return listProvince;
    }
}
