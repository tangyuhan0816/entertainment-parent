package com.vpis.asset.constant;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: RedisConstant
 *  @package: com.vpis.asset.constant
 *  @Date: Created in 2018/10/23 下午2:56
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: redis通用key
 */
public class RedisConstant {

    /**
     * app短信注册key
     */
    public static final String PREFIX_REGISTER_VERIFY_CODE_KEY = "ST:registerVerifyCode:";

    /**
     * app短信注册发送时间限制key
     */
    public static final String PREFIX_REGISTER_VERIFY_CODE_SEND_TIME_KEY = "ST:registerVerifyCode:sendTime:";

    /**
     * app短信登陆key
     */
    public static final String PREFIX_LOGIN_VERIFY_CODE_KEY = "ST:loginVerifyCode:";

    /**
     * app短信登陆发送时间限制key
     */
    public static final String PREFIX_LOGIN_VERIFY_CODE_SEND_TIME_KEY = "ST:loginVerifyCode:sendTime:";

    /**
     * app找回密码key
     */
    public static final String PREFIX_BACK_PASS_VERIFY_CODE_KEY = "ST:backPassVerifyCode:";

    /**
     * app找回密码时间限制key
     */
    public static final String PREFIX_BACK_PASS_VERIFY_CODE_SEND_TIME_KEY = "ST:backPassVerifyCode:sendTime:";

}
