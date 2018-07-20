package com.entertainment.common.constant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: ResponseContent
 *  @package: com.entertainment.common.constant
 *  @Date: Created in 2018/7/20 上午11:00
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 接口返回定义类
 */    
@ApiModel
@Data
public class ResponseContent<T> {

    public static final int SUCCESS_CODE = 0;
    public static final int FAIL_CODE = 2;
    public static final int AUTH_FAIL_CODE = 1;
    public static final int SIGNATURE_ERROR_CODE = 3;
    public static final int INTERNAL_SERVER_ERROR_CODE = 500;
    public static final int BUSINESS_EXCEPTION_CODE = 1000;
    public static final int NO_PERMiSSION = 961;
    /**
     * 0
     */
    @ApiModelProperty("状态码0成功 1用户授权失败 500 服务器内部错误")
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

    public static ResponseContent buildSuccess(String message) {
        return new ResponseContent(0, message);
    }

    public static <T> ResponseContent buildSuccess(String message, T data) {
        return new ResponseContent(0, message, data);
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
        return new ResponseContent(2, message);
    }

    public static ResponseContent buildNoPermissionFail(String message) {
        return new ResponseContent(961, message);
    }

    public static ResponseContent buildFail(String message, Object data) {
        return new ResponseContent(2, message, data);
    }

    public static ResponseContent buildServerError(String message) {
        return new ResponseContent(500, message);
    }

    public static ResponseContent buildSignatureError(String message) {
        return new ResponseContent(3, message);
    }

    public static ResponseContent buildAuth(String msg) {
        return new ResponseContent(1, msg);
    }

    public static ResponseContent buildCustomizedException(int code, String message) {
        return new ResponseContent(code, message);
    }

    public static <T> ResponseContent buildCustomizedException(int code, String message, T data) {
        return new ResponseContent(code, message, data);
    }

}