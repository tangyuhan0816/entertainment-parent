package com.vpis.asset.controller.sys;

import com.alibaba.fastjson.JSONObject;
import com.vpis.asset.bean.sys.MouldsBean;
import com.vpis.asset.controller.BaseController;
import com.vpis.asset.service.sys.MouldsService;
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
 *  @ClassName: MouldsController
 *  @package: com.vpis.asset.controller.sys
 *  @Date: Created in 2018/10/31 下午2:52
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
@RestController
@RequestMapping("/api/v1/vpis/moulds/")
@Api(description = "广告模版接口")
public class MouldsController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(MouldsController.class);

    @Autowired
    private MouldsService mouldsService;

    @ApiOperation(value = "查询通用配置模版 ，Owner: yuhan.tang")
    @RequestMapping(path = "/find/common", method = {RequestMethod.GET})
    public ResponseContent findCommonMoulds(){
        try{
            Long agentId = getSessionAgentId();
            logger.info("查询通用配置模版 find/common =======> {}", agentId);
            return ResponseContent.buildSuccess(mouldsService.findCommonMoulds(agentId));
        }catch(STException e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(e.getMessage());
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(ResponseContent.INTERNAL_SERVER_ERROR_CODE, e.getMessage());
        }
    }

    @ApiOperation(value = "查询我的配置模版 ，Owner: yuhan.tang")
    @RequestMapping(path = "/find/me", method = {RequestMethod.GET})
    public ResponseContent findMyMoulds(){
        try{
            Long userId = getSessionUserId();
            logger.info("查询我的配置模版 find/me =======> {}", userId);
            return ResponseContent.buildSuccess(mouldsService.findMyMoulds(userId));
        }catch(STException e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(e.getMessage());
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(ResponseContent.INTERNAL_SERVER_ERROR_CODE, e.getMessage());
        }
    }

    @ApiOperation(value = "保存或修改我的广告模版配置 ，Owner: yuhan.tang")
    @RequestMapping(path = "/save", method = {RequestMethod.POST})
    public ResponseContent save(@RequestBody MouldsBean bean){
        try{
            Long userId = getSessionUserId();
            logger.info("保存或修改我的广告模版配置 moulds save =======> {},{}", JSONObject.toJSONString(bean), userId);
            mouldsService.saveMould(bean, userId);
            return ResponseContent.buildSuccess();
        }catch(STException e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(e.getMessage());
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(ResponseContent.INTERNAL_SERVER_ERROR_CODE, e.getMessage());
        }
    }

    @ApiOperation(value = "删除我的广告模版配置 ，Owner: yuhan.tang")
    @RequestMapping(path = "/del", method = {RequestMethod.GET})
    public ResponseContent del(@RequestParam(value = "id",defaultValue = "") Long id){
        try{
            Long userId = getSessionUserId();
            logger.info("删除我的广告模版配置 moulds del =======> {},{}",id, userId);
            mouldsService.del(id,userId);
            return ResponseContent.buildSuccess();
        }catch(STException e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(e.getMessage());
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(ResponseContent.INTERNAL_SERVER_ERROR_CODE, e.getMessage());
        }
    }
}
