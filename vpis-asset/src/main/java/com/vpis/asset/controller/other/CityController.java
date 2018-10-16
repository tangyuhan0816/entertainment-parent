//package com.vpis.asset.controller.other;
//
//
//import com.vpis.asset.service.other.CityService;
//import com.vpis.common.utils.ResponseContent;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//
//
//@RestController
//@RequestMapping("/v1/asset/other/city")
//public class CityController {
//
//    @Autowired
//    private CityService cityService;
//
//    @RequestMapping(path = "/findCity/provinceId/{provinceId}", method = {RequestMethod.GET})
//    public Object findCity(HttpServletRequest request, @PathVariable(value = "provinceId") Long provinceId){
//        return ResponseContent.buildSuccess(cityService.findByProvinceId(provinceId));
//    }
//}
