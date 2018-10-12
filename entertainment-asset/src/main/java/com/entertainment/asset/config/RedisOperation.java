package com.entertainment.asset.config;

import com.entertainment.asset.constant.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: RedisOperation
 *  @package: com.entertainment.asset.config
 *  @Date: Created in 2018/10/8 下午5:17
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 所有写入读取redis的操作入口都放在这里，封装redis key 为方，方法内部封装过期时间
 */
@Component
public class RedisOperation {

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 保存注册验证码
     *
     * @param mobile
     * @param verifyCode
     */
    public void saveRegisterVerifyCode(String mobile, String verifyCode) {
        BoundValueOperations valueOperations = redisTemplate.boundValueOps(RedisConstant.PREFIX_REGISTER_VERIFY_CODE_KEY + mobile);
        valueOperations.set(verifyCode, 10L, TimeUnit.MINUTES);
    }

    /**
     * 获取注册验证码
     *
     * @param mobile
     */
    public String getRegisterVerifyCode(String mobile) {
        BoundValueOperations valueOperations = redisTemplate.boundValueOps(RedisConstant.PREFIX_REGISTER_VERIFY_CODE_KEY + mobile);
        Object obj = valueOperations.get();
        if (null != obj) {
            return (String) obj;
        }
        return null;
    }

    /**
     * 保存登陆验证码
     *
     * @param mobile
     * @param verifyCode
     */
    public void saveLoginVerifyCode(String mobile, String verifyCode) {
        BoundValueOperations valueOperations = redisTemplate.boundValueOps(RedisConstant.PREFIX_LOGIN_VERIFY_CODE_KEY + mobile);
        valueOperations.set(verifyCode, 10L, TimeUnit.MINUTES);
    }

    /**
     * 获取登陆验证码
     *
     * @param mobile
     * @param mobile
     */
    public String getLoginVerifyCode(String mobile) {
        BoundValueOperations valueOperations = redisTemplate.boundValueOps(RedisConstant.PREFIX_LOGIN_VERIFY_CODE_KEY + mobile);
        Object obj = valueOperations.get();
        if (null != obj) {
            return (String) obj;
        }
        return null;
    }

}