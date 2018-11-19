package com.vpis.asset.utils;

import com.vpis.asset.service.pay.IPayService;
import com.vpis.common.utils.Preconditions;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: BeanContext
 *  @package: com.vpis.asset.utils
 *  @Date: Created in 2018/10/30 下午2:01
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 
 */    
@Component
public class BeanContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static Map<String, IPayService> payServiceMap;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BeanContext.applicationContext = applicationContext;
        if(Preconditions.isNotBlank(BeanContext.applicationContext)){
            payServiceMap = new HashMap<>();
            Map<String, IPayService> beansOfType = BeanContext.applicationContext.getBeansOfType(IPayService.class);
            payServiceMap.putAll(beansOfType);
        }
    }

    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T)applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clz) throws BeansException {
        return (T)applicationContext.getBean(clz);
    }
}