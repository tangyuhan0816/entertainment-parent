package com.vpis.common.utils;

import com.vpis.common.constant.BusinessConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: ResponseContent
 *  @package: com.entertainment.common.utils
 *  @Date: Created in 2018/4/2 下午6:15
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
@ApiModel
@Data
public class ResponseContent<T> {

    /**
     * 成功返回状态码
     */
    public static final int SUCCESS_CODE = 200;

    /**
     * 系统异常返回状态码(比如空指针)
     */
    public static final int INTERNAL_SERVER_ERROR_CODE = 500;

    /**
     * 业务异常返回状态码
     */
    public static final int BUSINESS_EXCEPTION_CODE = 1000;

    /**
     * shiro异常返回状态码(比如登陆密码输入错误)
     */
    public static final int SHIRO_EXCEPTION_CODE = 401;

    /**
     * 无权限返回状态码
     */
    public static final int NO_PERMISSION = 961;
    /**
     * 0
     */
    @ApiModelProperty("状态码200成功 500 服务器内部错误 1000自定义错误  401shiro错误")
    private int code;
    private String message;

    @ApiModelProperty
    private T data;

    public ResponseContent() {
    }

    public ResponseContent(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public ResponseContent(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public static ResponseContent buildSuccess() {
        return new ResponseContent(SUCCESS_CODE, BusinessConstant.OPERATION_SUCCESS);
    }

    public static ResponseContent buildSuccess(String message) {
        return new ResponseContent(SUCCESS_CODE, message);
    }

    public static ResponseContent buildSuccess(Object data) {
        return new ResponseContent(SUCCESS_CODE, BusinessConstant.OPERATION_SUCCESS , data);
    }

    public static <T> ResponseContent buildSuccess(String message, T data) {
        return new ResponseContent(SUCCESS_CODE, message, data);
    }

    public static ResponseContent buildFail() {
        return new ResponseContent(BUSINESS_EXCEPTION_CODE, BusinessConstant.OPERATION_FAIL);
    }

    public static ResponseContent buildFail(int code, String message) {
        return new ResponseContent(code, message);
    }

    public static ResponseContent buildFail(int code, String message, String detail) {
        return new ResponseContent(code, message, detail);
    }

    public static ResponseContent buildFail(int code, String message, Object data) {
        return new ResponseContent(code, message, data);
    }

    public static ResponseContent buildFail(String message) {
        return new ResponseContent(INTERNAL_SERVER_ERROR_CODE, message);
    }

    public static ResponseContent buildFail(String message, Object data) {
        return new ResponseContent(INTERNAL_SERVER_ERROR_CODE, message, data);
    }

    public static ResponseContent buildServerError(String message) {
        return new ResponseContent(INTERNAL_SERVER_ERROR_CODE, message);
    }

    public static ResponseContent buildCustomizedException(int code, String message) {
        return new ResponseContent(code, message);
    }

    public static <T> ResponseContent buildCustomizedException(int code, String message, T data) {
        return new ResponseContent(code, message, data);
    }

}