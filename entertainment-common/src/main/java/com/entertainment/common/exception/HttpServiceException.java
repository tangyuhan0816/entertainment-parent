package com.entertainment.common.exception;

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
