package com.entertainment.common.exception;

import lombok.Data;

/**
 * 业务异常
 * 说明：支持业务层抛出和controlle层抛出
 * 1.controller层抛出支持带参数的异常
 * 2.业务层只支持code异常抛出，考虑到多语言的问题，service层不考虑语言问题，只处理业务，有特殊情况特殊处理
 */
@Data
public class BusinessException extends RuntimeException{
    /**
     * 异常编号
     */
    private String code ;
    /**
     * 引发业务异常的异常，可以为空
     */
    private Exception cause;

    /**
     * 消息参数处理，例如提示 手机号XXX已经被使用
     */
    private Object[] args;

    public BusinessException(String code){
        this.code = code;
    }

    /**
     * service 分离时不推荐使用此方法
     */
    public BusinessException(String code, Object[] args){
        this.code = code;
        this.args = args;
    }

    public BusinessException(String code, Object[] args, Exception cause){
        this.code = code;
        this.args = args;
        this.cause = cause;
    }


}
