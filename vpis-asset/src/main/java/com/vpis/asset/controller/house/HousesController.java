package com.vpis.asset.controller.house;


import com.alibaba.fastjson.JSONObject;
import com.vpis.asset.bean.SessionUser;
import com.vpis.asset.bean.house.HouseBean;
import com.vpis.asset.bean.vo.HouseVo;
import com.vpis.asset.controller.BaseController;
import com.vpis.asset.service.house.HouseService;
import com.vpis.common.exception.STException;
import com.vpis.common.utils.ResponseContent;
import io.swagger.annotations.Api;
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
@Api(description = "楼盘")
public class HousesController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(HousesController.class);

    @Autowired
    private HouseService housesService;

    @ApiOperation(value = "楼盘分页查询 ，Owner: yuhan.tang", response = HouseVo.class)
    @RequestMapping(path = "/page", method = {RequestMethod.POST})
    public ResponseContent page(@RequestBody HouseBean bean){
        try{
            logger.info("楼盘分页查询 page =======> {}", JSONObject.toJSONString(bean));
            return ResponseContent.buildSuccess(housesService.page(bean));
        }catch(STException e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(e.getMessage());
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(ResponseContent.INTERNAL_SERVER_ERROR_CODE, e.getMessage());
        }
    }

    @ApiOperation(value = "楼盘详情查询 ，Owner: yuhan.tang", response = HouseVo.class)
    @RequestMapping(path = "/find/detail", method = {RequestMethod.POST})
    public ResponseContent page(@RequestParam(value = "id") Long id){
        try{
            logger.info("楼盘详情查询 detail =======> {}", id);
            return ResponseContent.buildSuccess(housesService.findDetail(id));
        }catch(STException e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(e.getMessage());
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(ResponseContent.INTERNAL_SERVER_ERROR_CODE, e.getMessage());
        }
    }

    @ApiOperation(value = "banner图列表 ，Owner: yuhan.tang")
    @RequestMapping(path = "/find/banner", method = {RequestMethod.POST})
    public ResponseContent findBanner(){
        try{
            String phone = getSessionPhone();
            logger.info("banner图列表 /find/banner =======> {}", phone);
            return ResponseContent.buildSuccess(housesService.findBannerUrl(phone));
        }catch(STException e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(e.getMessage());
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(ResponseContent.INTERNAL_SERVER_ERROR_CODE, e.getMessage());
        }
    }

}
