package com.vpis.asset.service.sys;

import com.vpis.asset.bean.sys.RegisterBean;
import com.vpis.asset.config.RedisOperation;
import com.vpis.asset.config.YunpianSmsSender;
import com.vpis.asset.utils.AccountValidatorUtil;
import com.vpis.asset.utils.StringUtil;
import com.vpis.common.entity.sys.TbUser;
import com.vpis.common.exception.STException;
import com.vpis.common.utils.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: SendSmsService
 *  @package: com.vpis.asset.service.sys
 *  @Date: Created in 2018/10/23 下午2:36
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 发送短信服务
 */
@Service
public class SendSmsService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisOperation redisOperation;

    @Autowired
    private YunpianSmsSender yunpianSmsSender;

    @Value("${test.smscode}")
    private String testCode;

    /**
     * 短信发送等待时间(单位：秒)
     */
    private static final Long SEND_TIME = 120L;

    /**
     * 发送注册短信验证码
     * @param phone
     */
    public void send(String phone,String content, String sendKey,String vKey) throws STException {
        if(Preconditions.isBlank(phone)){
            throw new STException("手机号码为空");
        }
        AccountValidatorUtil.isMobile(phone);
        String mobile = "+" + phone;
        checkVerifyCodeSendTime(mobile,vKey,sendKey);
        String verifyCode = StringUtil.generateRandomNumStr(6);
        redisOperation.saveLoginVerifyCode(mobile, verifyCode, vKey);
        String key = String.format("%s%s", sendKey, mobile);
        redisTemplate.opsForValue().set(key, "TIME", SEND_TIME, TimeUnit.SECONDS);
        yunpianSmsSender.registerSmsForContent(phone, String.format(content, verifyCode));
    }

    /**
     * 校验短信验证码是否发送频繁
     *
     * @param mobile
     */
    public void checkVerifyCodeSendTime(String mobile, String vKey, String sendKey) {
        String code = redisOperation.getVerifyCode(mobile,vKey);
        if (Preconditions.isNotBlank(code)) {
            String key = String.format("%s%s", sendKey, mobile);
            Long time = redisTemplate.getExpire(key);
            if (Preconditions.isNotBlank(time) && time > 0) {
                throw new STException(String.format("验证码发送过于频繁，请%s秒后重试", time));
            }
        }
    }

    /**
     * 验证输入的登陆验证码是否正确
     * @return
     */
    public void checkVerifyCode(String smsCode,String phone,String vKey){
        if(!testCode.equals(smsCode)) {
            String mobile = "+" + phone;
            String code = redisOperation.getVerifyCode(mobile, vKey);
            if (Preconditions.isBlank(smsCode)) {
                throw new STException("验证码为空");
            }
            if (Preconditions.isBlank(code)) {
                throw new STException("验证码已过期");
            }
            if (!code.equals(smsCode)) {
                //TODO 验证码输入错误次数过多处理
                throw new STException("验证码输入错误");
            }
        }
    }
}
