package com.vpis.asset.controller.shop;

import com.alibaba.fastjson.JSONObject;
import com.vpis.asset.bean.vo.OrderVo;
import com.vpis.asset.controller.BaseController;
import com.vpis.asset.service.order.ShoppingService;
import com.vpis.common.utils.ResponseContent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

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
//@Api(description = "购物车接口(目前不用)")
public class ShoppingController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ShoppingController.class);

    @Autowired
    private RedisTemplate redisTemplateJackson;

    @Autowired
    private ShoppingService shoppingService;

    @Deprecated
//    @ApiOperation(value = "查询购物车 ，Owner: Huan.Liu")
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

    @Deprecated
//    @ApiOperation(value = "删除购物车 ，Owner: Huan.Liu")
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

    @Deprecated
//    @ApiOperation(value = "新增商品 ，Owner: Huan.Liu")
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

    @Deprecated
//    @ApiOperation(value = "修改商品 ，Owner: Huan.Liu")
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






    /***************************************************************************************************************/

//    @ApiOperation(value = "新增商品 ，Owner: Huan.Liu")
    @RequestMapping(path = "/insert", method = {RequestMethod.POST})
    public ResponseContent insertShop(@RequestParam(name = "house_id") Long houseId,
                                      @RequestParam(name = "product_count") Integer productCount) {
        try {
            logger.info("新增商品 ShopInsert =======> {},{}", houseId,productCount);
            Long userId = getSessionUserId();
            return  ResponseContent.buildSuccess(shoppingService.saveShop(houseId,userId,productCount));
        }catch (Exception e) {
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(ResponseContent.INTERNAL_SERVER_ERROR_CODE, e.getMessage());
        }
    }


//    @ApiOperation(value = "查询购物车 ，Owner: Huan.Liu")
    @RequestMapping(path = "/findList", method = {RequestMethod.POST})
    public ResponseContent findAll() {
        try {
            Long userId = getSessionUserId();
            logger.info("查询购物车 findAll =======> {}", userId);
            return ResponseContent.buildSuccess(shoppingService.findAll(userId));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(ResponseContent.INTERNAL_SERVER_ERROR_CODE, e.getMessage());
        }
    }

//    @ApiOperation(value = "删除购物车 ，Owner: Huan.Liu")
    @RequestMapping(path = "/del", method = {RequestMethod.POST})
    public ResponseContent delShop(@RequestParam(name = "ids")List<Long> houseIds) {
        try {
            logger.info("删除购物车 ShopDel =======> {}", JSONObject.toJSONString(houseIds));
            Long userId = getSessionUserId();
            return ResponseContent.buildSuccess(shoppingService.delShop(houseIds,userId));
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ResponseContent.buildFail(ResponseContent.INTERNAL_SERVER_ERROR_CODE, e.getMessage());
        }

    }


}
