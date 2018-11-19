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

import java.util.List;
import java.util.Set;

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

    public Set<ShoppingBean> saveShop(Long houseId, Long userId, Integer productCount){

        ShoppingBean bean = buildBean(houseId,productCount);

        Set<ShoppingBean> shoppings = redisTemplateJackson.opsForSet().members(getShoppingKey(userId));

        if(Preconditions.isNotBlank(shoppings)){
            for(ShoppingBean shoppingBean : shoppings){
                if(shoppingBean.getHouseId().equals(bean.getHouseId())){
                    redisTemplateJackson.opsForSet().remove(getShoppingKey(userId), shoppingBean);
                    bean.setProductCount(shoppingBean.getProductCount() + bean.getProductCount());
                    break;
                }
            }
        }
        redisTemplateJackson.opsForSet().add(getShoppingKey(userId), bean);

        return findAll(userId);
    }

    public Set<ShoppingBean> delShop(List<Long> houseIds, Long userId){
        Set<ShoppingBean> shoppings = redisTemplateJackson.opsForSet().members(getShoppingKey(userId));

        if(Preconditions.isNotBlank(shoppings)){
            for(ShoppingBean shoppingBean : shoppings){
                if(houseIds.contains(shoppingBean.getHouseId())){
                    redisTemplateJackson.opsForSet().remove(getShoppingKey(userId), shoppingBean);
                }
            }
        }
        return findAll(userId);
    }

    public Set<ShoppingBean> findAll(Long userId){
        return redisTemplateJackson.opsForSet().members(getShoppingKey(userId));
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
        return bean;
    }

    private String getShoppingKey(Long userId){
        return String.format("%s%s",SHOPPING_KEY, userId);
    }
}
