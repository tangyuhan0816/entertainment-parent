package com.vpis.asset.controller.house;


import com.vpis.asset.bean.house.HouseBean;
import com.vpis.asset.service.house.HousesService;
import com.vpis.common.exception.STException;
import com.vpis.common.utils.ResponseContent;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: HousesController
 *  @package: com.vpis.asset.controller.house
 *  @Date: Created in 2018/10/26 下午4:40
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
@RestController
@RequestMapping("/api/v1/vpis/houses/")
public class HousesController {

    private static final Logger logger = LoggerFactory.getLogger(HousesController.class);

    @Autowired
    private HousesService housesService;

    @ApiOperation(value = "楼盘分页查询 ，Owner: yuhan.tang")
    @RequestMapping(path = "/page", method = {RequestMethod.POST})
    public ResponseContent page(@RequestBody HouseBean bean){
        try{
            return ResponseContent.buildSuccess(housesService.page(bean));
        }catch(STException e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(e.getMessage());
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(ResponseContent.INTERNAL_SERVER_ERROR_CODE, e.getMessage());
        }
    }

}
