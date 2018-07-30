package com.entertainment.common.exception;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: HttpServiceException
 *  @package: com.entertainment.common.exception
 *  @Date: Created in 2018/7/20 下午5:48
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
public class HttpServiceException extends Exception{

    public HttpServiceException() {
        super();
    }

    public HttpServiceException(String message) {
        super(message);
    }

    public HttpServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpServiceException(Throwable cause) {
        super(cause);
    }

    protected HttpServiceException(String message, Throwable cause,
                        boolean enableSuppression,
                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
