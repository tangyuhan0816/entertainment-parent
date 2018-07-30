package com.entertainment.asset.config;

import com.entertainment.common.utils.CacheUtils;
import com.entertainment.common.utils.Preconditions;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: CachePutAop
 *  @package: com.entertainment.asset.config
 *  @Date: Created in 2018/7/27 下午6:39
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 缓存写入Aop
 */
@Aspect
@Component
public class CachePutAop {

    private final static Logger logger = LoggerFactory.getLogger(CachePutAop.class);

    @Autowired
    private RedisTemplate redisTemplateJackson;

    @Pointcut("@annotation(com.entertainment.asset.annotation.CachePutAnnotation)")
    public void filterMethod() {
    }

    @Around("filterMethod()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object o = joinPoint.proceed();
        if(Preconditions.isNotBlank(o)){
            Object [] objs = joinPoint.getArgs();
            String iKey = objs[0].toString();
            String cKey = objs[1].toString();
            if(Preconditions.isNotBlank(iKey) &&
                    Preconditions.isNotBlank(cKey)){
                ValueOperations operations = redisTemplateJackson.opsForValue();
                operations.set(CacheUtils.genCustomKey(iKey,cKey),o);
            }
        }
        return o;
    }
}