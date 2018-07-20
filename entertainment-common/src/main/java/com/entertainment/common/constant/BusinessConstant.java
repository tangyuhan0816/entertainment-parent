package com.entertainment.common.constant;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: BusinessConstant
 *  @package: com.entertainment.common.constant
 *  @Date: Created in 2018/7/20 下午12:06
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 
 */    
public abstract class BusinessConstant {

    /**
     * 通用异常
     */
    public static final String SYSTEM_ERROR = "system.error";
    public static final String NOT_FOUBND = "not.found";
    public static final String FIELD_ERROR = "field.error";

    /**************************************LoginError*************************************/
    public static final String USER_NAME_NOT_FOUND = "username.not.found";
    public static final String USER_NAME_IS_NULL = "username.is.null";
    public static final String LOGIN_USER_NAME_ERROR = "username.error";
    //登陆 密码错误
    public static final String USER_LOGIN_WRONG_PASSWORD = "user.login.wrongpassword";

}
