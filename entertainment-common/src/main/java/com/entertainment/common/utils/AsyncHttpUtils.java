package com.entertainment.common.utils;

import com.entertainment.common.exception.HttpServiceException;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaders;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.asynchttpclient.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: AsyncHttpUtils
 *  @package: com.entertainment.common.utils
 *  @Date: Created in 2018/7/20 下午3:43
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 
 */    
public class AsyncHttpUtils {


    public static final Log logger = LogFactory.getLog(AsyncHttpUtils.class);

    private static AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient();

    public static ListenableFuture<Response> asynPost(String url,String body,HttpHeaders header,int requestTimeoutInMill) throws HttpServiceException {
        return asyncHttpClient
                .preparePost(url)
                .setBody(body)
                .setHeaders(getHttpHeader(header))
                .setRequestTimeout(requestTimeoutInMill)
                .execute(new AsyncCompletionHandler<Response>() {

                    @Override
                    public Response onCompleted(Response response) throws Exception {
                        if (logger.isDebugEnabled()) {
                            logger.debug("HTTP GET COMPLETE: " + response);
                        }
                        return response;
                    }

                    @Override
                    public void onThrowable(Throwable t) {
                        // Something wrong happened.
                        logger.error("HTTP POST ERROR", t);
                    }
                });
    }

    public static ListenableFuture<Response> asynGet(String url, Map<String, String> queryParams, HttpHeaders header, int requestTimeoutInMill) throws HttpServiceException {
        List<Param> params = new ArrayList<>();
        for(Map.Entry<String,String> entry : queryParams.entrySet()){
            final Param param = new Param(entry.getKey(),entry.getValue());
            params.add(param);
        }
        return asyncHttpClient
                .prepareGet(url)
                .setHeaders(getHttpHeader(header))
                .setQueryParams(params)
                .setRequestTimeout(requestTimeoutInMill)
                .execute(new AsyncCompletionHandler<Response>() {
                    @Override
                    public Response onCompleted(Response response) throws Exception {
                        if (logger.isDebugEnabled()) {
                            logger.debug("HTTP GET COMPLETE: " + response);
                        }
                        return response;
                    }
                });
    }

    public static HttpHeaders getHttpHeader(HttpHeaders header) throws HttpServiceException {
        if(Preconditions.isBlank(header)){
            header = new DefaultHttpHeaders();
            header.set("Content-Type", "application/json;charset=UTF-8");
        }
        return header;
    }

}
