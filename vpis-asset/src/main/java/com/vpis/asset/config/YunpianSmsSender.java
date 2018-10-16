package com.vpis.asset.config;

import com.vpis.asset.utils.YunpianSmsUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.text.MessageFormat;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: YunpianSmsSender
 *  @package: com.entertainment.asset.config
 *  @Date: Created in 2018/10/8 下午5:05
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
@Configuration
public class YunpianSmsSender {

    @Value("${sms.yunpian.apiKey}")
    private String apiKey;

    @Value("${sms.text.registerSaveInfo}")
    private String registerSaveInfoText;

    /**
     * 短信发送验证码
     *
     * @param verifyCode
     */
    public String getRegisterSmsText(String verifyCode) {
        String text = MessageFormat.format(registerSaveInfoText, new String[]{verifyCode});
        return text;
    }

    /**
     * 短信发送验证码
     *
     * @param mobile
     * @param verifyCode
     */
    public void registerSms(String mobile, String verifyCode) {
        String text = MessageFormat.format(registerSaveInfoText, new String[]{verifyCode});
        try {
            YunpianSmsUtils.sendSms(apiKey, text, mobile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 短信发送验证码
     *
     * @param mobile
     */
    public void registerSmsForContent(String mobile, String content) {
        try {
            YunpianSmsUtils.sendSms(apiKey, content, mobile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
