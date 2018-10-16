package com.entertainment.common.utils;

import com.entertainment.common.exception.HttpServiceException;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaders;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.asynchttpclient.*;
import org.asynchttpclient.config.AsyncHttpClientConfigDefaults;
import org.asynchttpclient.proxy.ProxyServer;
import org.asynchttpclient.request.body.multipart.Part;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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


    /**
     * async Post method
     *
     * @param url  post url
     * @param body parameter in body
     * @return
     */
    public static ListenableFuture<Response> asyncPost(String url, String body) {
        return asyncPost(url, body, null, null);
    }

    /**
     * async Post method
     *
     * @param url
     * @param body
     * @param httpHeaders
     * @return
     */
    public static ListenableFuture<Response> asyncPost(String url, String body, HttpHeaders httpHeaders) {
        return asyncPost(url, body, httpHeaders, null, AsyncHttpClientConfigDefaults.defaultRequestTimeout());
    }


    public static String syncPost(String url, String body, HttpHeaders httpHeaders) throws HttpServiceException {
        return syncPost(url, body, httpHeaders, null, AsyncHttpClientConfigDefaults.defaultRequestTimeout(), AsyncHttpClientConfigDefaults.defaultReadTimeout());
    }

    public static String syncPost(String url, String body) throws HttpServiceException {
        return syncPost(url, body, null, null);
    }

    public static String syncPost(String url, String body, HttpHeaders httpHeaders,
                                  long requestTimeoutInMill, long readTimeoutInMill) throws HttpServiceException {
        String response = null;
        try {
            final ListenableFuture<Response> execute = asyncPost(url, body, httpHeaders, null, requestTimeoutInMill);
            response = execute.get(readTimeoutInMill, TimeUnit.MILLISECONDS).getResponseBody();
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug("HTTP POST ERROR", e);
            }
            throw new HttpServiceException("HTTP POST ERROR", e);
        }
        return response;
    }


    public static ListenableFuture<Response> asyncPost(String url, String body, ProxyServer proxyServer) {
        return asyncPost(url, body, null, proxyServer);
    }

    public static ListenableFuture<Response> asyncPost(String url, String body, HttpHeaders httpHeaders, ProxyServer proxyServer) {
        return asyncPost(url, body, httpHeaders, proxyServer, AsyncHttpClientConfigDefaults.defaultRequestTimeout());
    }


    public static String syncPost(String url, String body, HttpHeaders httpHeaders, ProxyServer proxyServer) throws HttpServiceException {
        return syncPost(url, body, httpHeaders, proxyServer, AsyncHttpClientConfigDefaults.defaultRequestTimeout(), AsyncHttpClientConfigDefaults.defaultReadTimeout());
    }

    public static String syncPost(String url, String body, ProxyServer proxyServer) throws HttpServiceException {
        return syncPost(url, body, null, proxyServer);
    }

    public static String syncPost(String url, String body, HttpHeaders httpHeaders, ProxyServer proxyServer, long requestTimeoutInMill, long readTimeoutInMill) throws HttpServiceException {
        String response = null;
        try {
            final ListenableFuture<Response> execute = asyncPost(url, body, httpHeaders, proxyServer, requestTimeoutInMill);
            response = execute.get(readTimeoutInMill, TimeUnit.MILLISECONDS).getResponseBody();
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug("HTTP POST ERROR", e);
            }
            throw new HttpServiceException("HTTP POST ERROR", e);
        }
        return response;
    }

    /**
     * @param url
     * @param body
     * @param httpHeaders
     * @param proxyServer
     * @param requestTimeoutInMill
     * @return
     */
    public static ListenableFuture<Response> asyncPost(String url, String body, HttpHeaders httpHeaders, ProxyServer proxyServer, long requestTimeoutInMill) {
        AsyncHttpClient tmpAsyncHttpClient;
        if (Preconditions.isNotBlank(proxyServer)) {
            AsyncHttpClientConfig ahcCfg = new DefaultAsyncHttpClientConfig.Builder()
                    .setProxyServer(proxyServer).build();
            tmpAsyncHttpClient = new DefaultAsyncHttpClient(ahcCfg);
        } else {
            tmpAsyncHttpClient = asyncHttpClient;
        }
        return tmpAsyncHttpClient
                .preparePost(url)
                .setHeaders(getHttpHeaders(httpHeaders))
                .setBody(body)
                .setRequestTimeout((int) requestTimeoutInMill)
                .execute(new AsyncCompletionHandler<Response>() {

                    @Override
                    public Response onCompleted(Response response) throws Exception {
                        if (logger.isDebugEnabled()) {
                            logger.debug("HTTP POST COMPLETE: " + response);
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

    public static ListenableFuture<Response> asyncPost(String url, List<Part> params) {
        return asyncPost(url, params, null, null);
    }

    public static String syncPost(String url, List<Part> params) throws HttpServiceException {
        return syncPost(url, params, null, null);
    }

    public static ListenableFuture<Response> asyncPost(String url, List<Part> params, HttpHeaders httpHeaders) {
        return asyncPost(url, params, httpHeaders, null, AsyncHttpClientConfigDefaults.defaultRequestTimeout());
    }

    public static String syncPost(String url, List<Part> params, HttpHeaders httpHeaders) throws HttpServiceException {
        return syncPost(url, params, httpHeaders, null);
    }

    public static String syncPost(String url, List<Part> params, HttpHeaders httpHeaders,
                                  long requestTimeoutInMill, long readTimeoutInMill) throws HttpServiceException {
        String response = null;
        try {
            final ListenableFuture<Response> execute = asyncPost(url, params, httpHeaders, null, requestTimeoutInMill);
            response = execute.get(readTimeoutInMill, TimeUnit.MILLISECONDS).getResponseBody();
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug("HTTP POST ERROR", e);
            }
            throw new HttpServiceException("HTTP POST ERROR", e);
        }
        return response;
    }

    public static ListenableFuture<Response> asyncPost(String url, List<Part> params, ProxyServer proxyServer) {
        return asyncPost(url, params, null, proxyServer);
    }

    public static String syncPost(String url, List<Part> params, ProxyServer proxyServer) throws HttpServiceException {
        return syncPost(url, params, null, proxyServer);
    }

    public static ListenableFuture<Response> asyncPost(String url, List<Part> params, HttpHeaders httpHeaders, ProxyServer proxyServer) {
        return asyncPost(url, params, httpHeaders, proxyServer, AsyncHttpClientConfigDefaults.defaultRequestTimeout());
    }

    public static String syncPost(String url, List<Part> params, HttpHeaders httpHeaders, ProxyServer proxyServer) throws HttpServiceException {
        return syncPost(url, params, httpHeaders, proxyServer, AsyncHttpClientConfigDefaults.defaultRequestTimeout(), AsyncHttpClientConfigDefaults.defaultReadTimeout());
    }

    public static String syncPost(String url, List<Part> params, HttpHeaders httpHeaders, ProxyServer proxyServer, long requestTimeoutInMill, long readTimeoutInMill) throws HttpServiceException {
        String response = null;
        try {
            final ListenableFuture<Response> execute = asyncPost(url, params, httpHeaders, proxyServer, requestTimeoutInMill);
            response = execute.get(readTimeoutInMill, TimeUnit.MILLISECONDS).getResponseBody();
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug("HTTP POST ERROR", e);
            }
            throw new HttpServiceException("HTTP POST ERROR", e);
        }
        return response;
    }
    // proxy end

    public static ListenableFuture<Response> asyncPost(String url, List<Part> params, HttpHeaders httpHeaders,
                                                       ProxyServer proxyServer, long requestTimeoutInMill) {
        AsyncHttpClient tmpAsyncHttpClient;
        if (Preconditions.isNotBlank(proxyServer)) {
            AsyncHttpClientConfig ahcCfg = new DefaultAsyncHttpClientConfig.Builder()
                    .setProxyServer(proxyServer).build();
            tmpAsyncHttpClient = new DefaultAsyncHttpClient(ahcCfg);
        } else {
            tmpAsyncHttpClient = asyncHttpClient;
        }
        return tmpAsyncHttpClient
                .preparePost(url)
                .setHeaders(getHttpHeaders(httpHeaders))
                .setBodyParts(params)
                .setRequestTimeout((int) requestTimeoutInMill)
                .execute(new AsyncCompletionHandler<Response>() {
                    @Override
                    public Response onCompleted(Response response) throws Exception {
                        if (logger.isDebugEnabled()) {
                            logger.debug("HTTP POST COMPLETE: " + response);
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

    /****************************** get method *********************************/
    public static String syncGet(String url, Map<String, String> queryParams, long requestTimeoutInMill, long readTimeoutInMill) throws HttpServiceException {
        return syncGet(url, queryParams, null, requestTimeoutInMill, readTimeoutInMill);
    }


    public static String syncGet(String url, Map<String, String> queryParams, HttpHeaders httpHeaders) throws HttpServiceException {
        return syncGet(url, queryParams, httpHeaders, null);
    }

    public static String syncGet(String url, Map<String, String> queryParams) throws HttpServiceException {
        return syncGet(url, queryParams, null, null);
    }


    public static String syncGet(String url, Map<String, String> queryParams, ProxyServer proxyServer, long requestTimeoutInMill, long readTimeoutInMill) throws HttpServiceException {
        return syncGet(url, queryParams, null, proxyServer, requestTimeoutInMill, readTimeoutInMill);
    }

    public static String syncGet(String url, Map<String, String> queryParams, HttpHeaders httpHeaders, ProxyServer proxyServer) throws HttpServiceException {
        return syncGet(url, queryParams, httpHeaders, proxyServer, AsyncHttpClientConfigDefaults.defaultRequestTimeout(), AsyncHttpClientConfigDefaults.defaultReadTimeout());
    }

    public static String syncGet(String url, Map<String, String> queryParams, ProxyServer proxyServer) throws HttpServiceException {
        return syncGet(url, queryParams, null, proxyServer);
    }

    public static String syncGet(String url, Map<String, String> queryParams, HttpHeaders httpHeaders, ProxyServer proxyServer, long requestTimeoutInMill, long readTimeoutInMill) throws HttpServiceException {
        ListenableFuture<Response> response = asyncGet(url, queryParams, httpHeaders, proxyServer, requestTimeoutInMill);

        String res = null;
        try {
            res = response.get(readTimeoutInMill, TimeUnit.MILLISECONDS).getResponseBody();
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug("HTTP GET ERROR", e);
            }
            throw new HttpServiceException("HTTP GET ERROR", e);
        }

        return res;
    }

    public static ListenableFuture<Response> asyncGet(String url, Map<String, String> queryParams, HttpHeaders httpHeaders, ProxyServer proxyServer, long requestTimeoutInMill) throws HttpServiceException {
        List<Param> params = new ArrayList<>();
        if (queryParams != null) {
            // init query parameters;
            for (String key : queryParams.keySet()) {
                final String val = queryParams.get(key);
                final Param param = new Param(key, val);
                params.add(param);
            }
        }
        AsyncHttpClient tmpAsyncHttpClient;
        if (Preconditions.isNotBlank(proxyServer)) {
            AsyncHttpClientConfig ahcCfg = new DefaultAsyncHttpClientConfig.Builder()
                    .setProxyServer(proxyServer).build();
            tmpAsyncHttpClient = new DefaultAsyncHttpClient(ahcCfg);
        } else {
            tmpAsyncHttpClient = asyncHttpClient;
        }
        ListenableFuture<Response> future = tmpAsyncHttpClient
                .prepareGet(url)
                .setHeaders(getHttpHeaders(httpHeaders))
                .setQueryParams(params)
                .setRequestTimeout((int) requestTimeoutInMill)
                .execute(new AsyncCompletionHandler<Response>() {
                    @Override
                    public Response onCompleted(Response response) throws Exception {
                        if (logger.isDebugEnabled()) {
                            logger.debug("HTTP GET COMPLETE: " + response);
                        }
                        return response;
                    }
                });

        return future;
    }

    /**
     * 设置 httpHeader 的信息，如果不设置使用默认设置
     * Content-Type: application/json;charset=UTF-8
     *
     * @param httpHeaders
     * @return
     */
    private static HttpHeaders getHttpHeaders(HttpHeaders httpHeaders) {
        if (Preconditions.isBlank(httpHeaders)) {
            httpHeaders = new DefaultHttpHeaders();
            httpHeaders.set("Content-Type", "application/json;charset=UTF-8");
        }
        return httpHeaders;
    }


}
