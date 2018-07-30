package com.entertainment.asset.config;

import com.alibaba.fastjson.JSONObject;
import com.entertainment.common.utils.CacheUtils;
import com.entertainment.common.utils.Preconditions;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: CacheReadAop
 *  @package: com.entertainment.asset.config
 *  @Date: Created in 2018/7/27 下午6:39
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 缓存读取Aop
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
                    // TODO 反序列化一次，匹配数据结构是否一致，不匹配再次读取数据库数据
//                    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//                    Class cls = signature.getReturnType();
//                    JSONObject.parseObject(obj.toString(),cls.getClass());
                    return obj;
                }
                return joinPoint.proceed();
            }
        }
        return null;
    }
}