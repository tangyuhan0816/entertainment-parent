package com.vpis.asset.controller.shop;

import com.alibaba.fastjson.JSONObject;
import com.vpis.asset.bean.vo.OrderVo;
import com.vpis.asset.service.jwt.JwtService;
import com.vpis.common.utils.ResponseContent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 *  @Author: Huan.Liu
 *  @ClassName: ShoppingController
 *  @package: com.vpis.asset.controller.shopping
 *  @Date: Created in 2018/11/16 下午19:42
 *  @email
 *  @Description:
 */
@RestController
@RequestMapping("/api/v1/vpis/shop/")
@Api(description = "购物车接口")
public class ShoppingController {
    private static final Logger logger = LoggerFactory.getLogger(ShoppingController.class);

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RedisTemplate redisTemplateJackson;

    @ApiOperation(value = "查询购物车 ，Owner: Huan.Liu")
    @RequestMapping(path = "/ShopQuery", method = {RequestMethod.POST})
    public ResponseContent query() {
        try {
            Long userId = getSessionUserId();
            logger.info("查询购物车 ShopQuery =======> {}", ResponseContent.buildSuccess(redisTemplateJackson.opsForSet().members("shopMap")));
            return ResponseContent.buildSuccess(redisTemplateJackson.opsForSet().members("shopMap"+userId));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(ResponseContent.INTERNAL_SERVER_ERROR_CODE, e.getMessage());
        }
    }

    @ApiOperation(value = "删除购物车 ，Owner: Huan.Liu")
    @RequestMapping(path = "/ShopDel", method = {RequestMethod.POST})
    public ResponseContent del( @RequestBody OrderVo orderVo) {
        try {
            logger.info("删除购物车 ShopDel =======> {}", JSONObject.toJSONString(orderVo));
            Long userId = getSessionUserId();
            HashSet<OrderVo> hashSet =(HashSet)redisTemplateJackson.opsForSet().members("shopMap"+userId);
            for (OrderVo orderVo1 : hashSet){
                if(orderVo1.getId().equals(orderVo.getId()+userId)){
                    hashSet.remove(orderVo1.getId());
                }
            }
            redisTemplateJackson.delete("shopMap"+userId);
            redisTemplateJackson.opsForSet().add("shopMap"+userId,hashSet);
            return ResponseContent.buildSuccess(redisTemplateJackson.opsForSet().members("shopMap"+userId));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(ResponseContent.INTERNAL_SERVER_ERROR_CODE, e.getMessage());
        }

    }

    @ApiOperation(value = "新增商品 ，Owner: Huan.Liu")
    @RequestMapping(path = "/ShopInsert", method = {RequestMethod.POST})
    public ResponseContent insert(@RequestBody OrderVo orderVo) {
        try {
            logger.info("新增商品 ShopInsert =======> {}", JSONObject.toJSONString(orderVo));
            Long userId = getSessionUserId();
            HashSet hashSet = new HashSet();
            orderVo.setId(orderVo.getId()+userId);
            hashSet.add(orderVo);
            redisTemplateJackson.opsForSet().add("shopMap"+userId,hashSet);
            return  ResponseContent.buildSuccess(redisTemplateJackson.opsForSet().members("shopMap"+userId));
        }catch (Exception e) {
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(ResponseContent.INTERNAL_SERVER_ERROR_CODE, e.getMessage());
        }
    }

    @ApiOperation(value = "修改商品 ，Owner: Huan.Liu")
    @RequestMapping(path = "/Shopupdate", method = {RequestMethod.POST})
    public ResponseContent update(@RequestBody OrderVo orderVo) {
        try {
            logger.info("修改商品 Shopupdate =======> {}", JSONObject.toJSONString(orderVo));
            Long userId = getSessionUserId();
            HashSet<OrderVo> hashSet =(HashSet)redisTemplateJackson.opsForSet().members("shopMap"+userId);
            for (OrderVo orderVo1 : hashSet){
                if(orderVo1.getId().equals(orderVo.getId()+userId)){
                    hashSet.remove(orderVo1.getId());
                }
            }
            redisTemplateJackson.delete("shopMap"+userId);
            hashSet.add(orderVo);
            redisTemplateJackson.opsForSet().add("shopMap"+userId,hashSet);

            return ResponseContent.buildSuccess(redisTemplateJackson.opsForSet().members("shopMap"+userId));
        }catch (Exception e) {
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(ResponseContent.INTERNAL_SERVER_ERROR_CODE, e.getMessage());
        }
    }

    protected Long getSessionUserId(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String authorization = request.getHeader("Authorization");
        return jwtService.getLongValueByParams(authorization,"user_id");
    }


}
