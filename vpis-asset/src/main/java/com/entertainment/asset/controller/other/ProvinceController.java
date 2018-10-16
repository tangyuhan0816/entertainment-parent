package com.entertainment.asset.controller.other;


import com.entertainment.asset.service.jwt.JwtService;
import com.entertainment.asset.service.other.ProvinceService;
import com.entertainment.common.utils.ResponseContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/v1/asset/other/province")
public class ProvinceController {

    @Autowired
    private ProvinceService provinceService;

    @RequestMapping(path = "/findProvince", method = {RequestMethod.GET})
    public Object findProvince(HttpServletRequest request){
        return ResponseContent.buildSuccess(provinceService.findAll());
    }
}
