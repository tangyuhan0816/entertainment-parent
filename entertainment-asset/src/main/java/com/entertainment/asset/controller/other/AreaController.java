package com.entertainment.asset.controller.other;


import com.entertainment.asset.service.other.AreaService;
import com.entertainment.common.utils.ResponseContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/v1/asset/other/area")
public class AreaController {

    @Autowired
    private AreaService areaService;

    @RequestMapping(path = "/findArea/cityId/{cityId}", method = {RequestMethod.GET})
    public Object login(HttpServletRequest request, @PathVariable(value = "cityId") Long cityId){
        return ResponseContent.buildSuccess(areaService.findByCityId(cityId));
    }
}
