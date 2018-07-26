package com.entertainment.asset.config;

import com.entertainment.common.utils.CacheUtils;
import com.entertainment.common.utils.Preconditions;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * 利用AOP技术对往数据库中插入学生信息作权限验证
 */
 
@Aspect
@Component
public class CacheReadAop {

    private final static Logger logger = LoggerFactory.getLogger(CacheReadAop.class);

    @Autowired
    private RedisTemplate redisTemplateJackson;

    @Pointcut("@annotation(com.entertainment.asset.annotation.CacheReadAnnotation)")
    public void filterMethod() {
    }

    @Around("filterMethod()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object [] objs = joinPoint.getArgs();
        if(Preconditions.isNotBlank(objs)){
            String iKey = objs[0].toString();
            String cKey = objs[1].toString();
            if(Preconditions.isNotBlank(iKey) &&
                    Preconditions.isNotBlank(cKey)){
                ValueOperations operations = redisTemplateJackson.opsForValue();
                Object obj = operations.get(CacheUtils.genCustomKey(iKey,cKey));
                if(Preconditions.isNotBlank(obj)){
                    return obj;
                }
                return joinPoint.proceed();
            }
        }
        return null;
    }
}