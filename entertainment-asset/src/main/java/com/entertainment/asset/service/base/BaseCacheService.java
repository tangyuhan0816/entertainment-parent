package com.entertainment.asset.service.base;

import com.entertainment.common.base.BaseEntity;
import org.springframework.data.redis.core.RedisTemplate;

public class BaseCacheService<T extends BaseEntity> {

    private RedisTemplate redisTemplateJackson;
}
