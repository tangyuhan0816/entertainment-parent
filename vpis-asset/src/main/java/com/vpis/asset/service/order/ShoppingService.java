package com.vpis.asset.service.order;

import com.vpis.asset.bean.order.ShoppingBean;
import com.vpis.asset.service.house.HouseService;
import com.vpis.common.entity.house.House;
import com.vpis.common.exception.STException;
import com.vpis.common.utils.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: ShoppingService
 *  @package: com.vpis.asset.bean.order
 *  @Date: Created in 2018/11/19 下午1:18
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 购物车
 */
@Slf4j
@Service
public class ShoppingService {

    @Autowired
    private RedisTemplate redisTemplateJackson;

    @Autowired
    private HouseService houseService;

    private static final String SHOPPING_KEY = "SHOPPING_CARD:USER_";

    public List<ShoppingBean> saveShop(Long houseId, Long userId, Integer productCount){

        ShoppingBean bean = buildBean(houseId,productCount);

        List<ShoppingBean> shoppings = redisTemplateJackson.opsForList().range(getShoppingKey(userId), 0,-1);

        Boolean isSave = Boolean.TRUE;
        if(Preconditions.isNotBlank(shoppings)){
            for(int i = 0;i < shoppings.size(); i++){
                ShoppingBean shoppingBean = shoppings.get(i);
                if(shoppingBean.getHouseId().equals(bean.getHouseId())){
                    bean.setProductCount(shoppingBean.getProductCount() + bean.getProductCount());
                    redisTemplateJackson.opsForList().set(getShoppingKey(userId), i, bean);
                    isSave = Boolean.FALSE;
                    break;
                }
            }
        }

        if(isSave){
            redisTemplateJackson.opsForList().rightPush(getShoppingKey(userId), bean);
        }

        return findAll(userId);
    }

    public List<ShoppingBean> delShop(List<Long> houseIds, Long userId){
        List<ShoppingBean> shoppings = redisTemplateJackson.opsForList().range(getShoppingKey(userId), 0,-1);

        if(Preconditions.isNotBlank(shoppings)){

            for(int i = 0; i < shoppings.size(); i++){
                if(houseIds.contains(shoppings.get(i).getHouseId())){
                    redisTemplateJackson.opsForList().remove(getShoppingKey(userId),-1,shoppings.get(i));
                }
            }
        }
        return findAll(userId);
    }

    public List<ShoppingBean> findAll(Long userId){
        return redisTemplateJackson.opsForList().range(getShoppingKey(userId), 0, -1);
    }

    private ShoppingBean buildBean(Long houseId,Integer productCount){
        House house = houseService.findById(houseId);
        if(Preconditions.isBlank(house)){
            throw new STException("house is null");
        }
        ShoppingBean bean = new ShoppingBean();
        bean.setHouseId(house.getId());
        bean.setHouseName(house.getHouseName());
        bean.setBannerUrl(house.getBannerUrl());
        bean.setPersonNumber(house.getPersonNum());
        bean.setPrice(house.getPrice());
        bean.setProductCount(productCount);
        bean.setCreateDate(new Date());
        return bean;
    }

    private String getShoppingKey(Long userId){
        return String.format("%s%s",SHOPPING_KEY, userId);
    }
}
