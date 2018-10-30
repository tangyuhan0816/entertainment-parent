package com.vpis.asset.controller.common;

import com.vpis.asset.controller.house.HousesController;
import com.vpis.asset.service.common.CommonService;
import com.vpis.common.exception.STException;
import com.vpis.common.utils.ResponseContent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: CommonController
 *  @package: com.vpis.asset.controller.common
 *  @Date: Created in 2018/10/29 下午1:53
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 通用controller
 */
@RestController
@RequestMapping("/api/v1/vpis/commons/")
@Api(description = "公共服务接口")
public class CommonController {

    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private CommonService commonService;

    @ApiOperation(value = "上传图片异步接口，Owner: yuhan.tang")
    @RequestMapping(path = "/upload/image", method = {RequestMethod.POST})
    public ResponseContent uploadImage(@RequestBody MultipartFile file) {
        try{
            logger.info("上传图片 uploadImage =======> {},{}m", file.getOriginalFilename(),file.getSize());
            return ResponseContent.buildSuccess("图片上传成功，等待数据同步", commonService.uploadImage(file));
        }catch(STException e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(e.getMessage());
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(ResponseContent.INTERNAL_SERVER_ERROR_CODE, e.getMessage());
        }
    }

    @ApiOperation(value = "上传视频异步接口，Owner: yuhan.tang")
    @RequestMapping(path = "/upload/video", method = {RequestMethod.POST})
    public ResponseContent uploadVideo(@RequestBody MultipartFile file) {
        try{
            logger.info("上传视频 uploadVideo =======> {},{}m", file.getOriginalFilename(), file.getSize()/1024);
            return ResponseContent.buildSuccess(commonService.uploadVideo(file));
        }catch(STException e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(e.getMessage());
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(ResponseContent.INTERNAL_SERVER_ERROR_CODE, e.getMessage());
        }
    }

    @ApiOperation(value = "查看上传接口(同步完存储两小时)，Owner: yuhan.tang")
    @RequestMapping(path = "/get/upload/key", method = {RequestMethod.POST})
    public ResponseContent uploadVideo(@RequestParam(value = "key" ,defaultValue = "") String key) {
        try{
            logger.info("查看上传图片 /get/upload/key =======> {}", key);
            return ResponseContent.buildSuccess(commonService.getUploadKey(key));
        }catch(STException e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(e.getMessage());
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(ResponseContent.INTERNAL_SERVER_ERROR_CODE, e.getMessage());
        }
    }

    @ApiOperation(value = "经纬度定位城市，Owner: yuhan.tang")
    @RequestMapping(path = "/gps/city", method = {RequestMethod.GET})
    public ResponseContent gpsCity(@RequestParam(value = "lat",defaultValue = "0") String lat,
                                       @RequestParam(value = "log",defaultValue = "0") String log) {
        try{
            logger.info("经纬度定位城市 gps/city =======> {},{}", lat,log);
            return ResponseContent.buildSuccess(commonService.positioning(lat,log));
        }catch(STException e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(e.getMessage());
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(ResponseContent.INTERNAL_SERVER_ERROR_CODE, e.getMessage());
        }
    }

}
