package com.entertainment.asset.controller;

import com.entertainment.common.constant.BusinessConstant;
import com.entertainment.common.exception.BusinessException;
import com.entertainment.common.exception.STException;
import com.entertainment.common.utils.ResponseContent;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: GlobalExceptionHandler
 *  @package: com.entertainment.asset.controller
 *  @Date: Created in 2018/7/20 下午2:26
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ShiroException.class)
    public ResponseContent handlShiroException(ShiroException ex) {
        logger.error(ex.getMessage(),ex);
        if(null != ex.getCause() && ex.getCause() instanceof BusinessException){
            BusinessException bex = (BusinessException) ex.getCause();
            return ResponseContent.buildFail(ResponseContent.SHIRO_EXCEPTION_CODE,bex.getCode());
        }else if(ex instanceof IncorrectCredentialsException){
            return ResponseContent.buildFail(ResponseContent.SHIRO_EXCEPTION_CODE, BusinessConstant.USER_LOGIN_WRONG_PASSWORD);
        }

        String message = ex.getMessage();
        return ResponseContent.buildFail(ResponseContent.SHIRO_EXCEPTION_CODE,message);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseContent handlBusinessException(BusinessException ex) {
        logger.error(ex.getMessage(),ex);
        String message = ex.getCode();
        return ResponseContent.buildFail(ResponseContent.BUSINESS_EXCEPTION_CODE,message);
    }

    @ExceptionHandler(STException.class)
    public ResponseContent handlSTException(STException ex) {
        logger.error(ex.getMessage(),ex);
        String message = ex.getMessage();
        return ResponseContent.buildFail(ResponseContent.BUSINESS_EXCEPTION_CODE,message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseContent globalException(HttpServletRequest request, Throwable ex) {
        logger.error(ex.getMessage(),ex);
        String message = BusinessConstant.SYSTEM_ERROR;
        return ResponseContent.buildFail(ResponseContent.INTERNAL_SERVER_ERROR_CODE,message);
    }
}
