package com.entertainment.asset.service.base;

import com.entertainment.common.base.BaseEntity;
import org.springframework.data.redis.core.RedisTemplate;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: BaseCacheService
 *  @package: com.entertainment.asset.service.base
 *  @Date: Created in 2018/7/20 下午5:49
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
public class BaseCacheService<T extends BaseEntity> {

    private RedisTemplate redisTemplateJackson;
}
