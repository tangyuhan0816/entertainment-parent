package com.entertainment.common.exception;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: STException
 *  @package: com.entertainment.common.exception
 *  @Date: Created in 2018/10/9 下午5:49
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 业务异常
 */
public class STException extends RuntimeException{


    public STException() {
        super();
    }

    public STException(String message) {
        super(message);
    }

    public STException(String message, Throwable cause) {
        super(message, cause);
    }

    public STException(Throwable cause) {
        super(cause);
    }

    protected STException(String message, Throwable cause,
                                   boolean enableSuppression,
                                   boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
