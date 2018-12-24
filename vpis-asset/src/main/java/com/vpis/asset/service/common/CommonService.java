package com.vpis.asset.service.common;

import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.vpis.asset.utils.BeanContext;
import com.vpis.asset.utils.QiNiuUtils;
import com.vpis.common.exception.BusinessException;
import com.vpis.common.exception.HttpServiceException;
import com.vpis.common.exception.STException;
import com.vpis.common.utils.AsyncHttpUtils;
import com.vpis.common.utils.Preconditions;
import com.vpis.common.utils.ResponseContent;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: CommonService
 *  @package: com.vpis.asset.service.common
 *  @Date: Created in 2018/10/29 下午2:14
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 公共服务service
 */
@Service
public class CommonService {

    private static final Logger logger = LoggerFactory.getLogger(CommonService.class);

    private static final String IMAGE_KEY = "SYNC:IMAGE:";

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${api.aliyun.gps}")
    private String aliyunGpsHost;

    @Value("${api.gaode.poi}")
    private String gaodeGpsHost;

    @Value("${api.gaode.key}")
    private String gaodeGpsKey;

    private static final Integer POOL_SIZE = 3;

    private static ThreadFactory threadFactory = new ThreadFactoryBuilder()
            .setNameFormat("common-upload-%d").build();

    private static ExecutorService executorService = new ThreadPoolExecutor(POOL_SIZE,
            POOL_SIZE, 0L,
            TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(), threadFactory,
            new ThreadPoolExecutor.AbortPolicy());

    public String uploadImage(MultipartFile file) throws IOException {

        String articleKey = UUID.randomUUID().toString().replaceAll("-", "");

        executorService.execute(new MyUpTask(file,articleKey,1));

        return articleKey;
    }

    public String uploadVideo(MultipartFile file) throws IOException {
        String articleKey = UUID.randomUUID().toString().replaceAll("-", "");

        executorService.execute(new MyUpTask(file,articleKey,2));

        return articleKey;
    }

    public String getUploadKey(String key){
        Object obj = redisTemplate.opsForValue().get(String.format("%s%s",IMAGE_KEY,key));
        if(Preconditions.isBlank(obj)){
            return "同步中，请稍后重试";
        }
        return String.valueOf(obj);
    }

    private static final Integer MAX_RETRY = 3;

    /**
     * 根据经纬度获取所在地
     * @param lat
     * @param log
     * @return
     * @throws STException
     * @throws HttpServiceException
     */
    private String getAliyunAreaByLatAndLog(String lat,String log) throws STException, HttpServiceException {
        String areaCode = null;

        logger.info("根据经纬度获取所在地 positioning ======> {},{}", lat, log);

        String url = String.format(aliyunGpsHost, lat, log);

        String repsonse = AsyncHttpUtils.syncPost(url, (String) null);

        if (Preconditions.isBlank(repsonse)) {

            throw new BusinessException("获取定位失败");

        }

        logger.info("根据经纬度获取所在地，返回参数 positioning ======> {}", repsonse);

        JSONObject result = JSONObject.parseObject(repsonse);

        JSONObject city = JSONObject.parseObject(result.getJSONArray("addrList").get(0).toString());

        areaCode = city.getString("admCode");

        if (Preconditions.isBlank(areaCode)) {

            throw new STException("定位失败");

        }

        return areaCode;
    }


    /**
     * 根据经纬度获取所在地
     * @param lat
     * @param log
     * @return
     * @throws STException
     * @throws HttpServiceException
     */
    private String getGaodeAreaByLatAndLog(String lat,String log) throws STException, HttpServiceException {
        String areaCode = null;

        logger.info("根据经纬度获取所在地 positioning ======> {},{}", lat, log);

        String url = String.format(gaodeGpsHost,gaodeGpsKey, lat, log, 1, 1);

        //Creates CloseableHttpClient instance with default configuration.
        CloseableHttpClient httpCilent = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        String response = null;
        try {
            HttpResponse httpResponse = httpCilent.execute(httpGet);
            response = EntityUtils.toString(httpResponse.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                httpCilent.close();//释放资源
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (Preconditions.isBlank(response)) {

            throw new BusinessException("获取定位失败");

        }

        logger.info("根据经纬度获取所在地，返回参数 positioning ======> {}", response);

        JSONObject result = JSONObject.parseObject(response);

        areaCode = result.getJSONArray("pois").getJSONObject(0).getString("adcode");

        if (Preconditions.isBlank(areaCode)) {

            throw new STException("定位失败");

        }

        return areaCode;
    }

    public String positioning(String lat,String log) throws HttpServiceException, InterruptedException {
        String areaCode = null;

        Integer count = 0;

        do {

            try {

                count++;

                areaCode = getGaodeAreaByLatAndLog(lat, log);

            } catch (Exception e) {

                Thread.sleep(50);

                logger.error(e.getMessage(),e);

                if(MAX_RETRY.equals(count)){

                    throw e;
                }
            }
        } while (areaCode == null && count < MAX_RETRY);

        return areaCode;
    }

    class MyUpTask implements Runnable{

        private MultipartFile file;

        private String key;

        private Integer type;

        public MyUpTask(MultipartFile file, String key, Integer type){
            this.key = key;
            this.file = file;
            this.type = type;
        }

        @Override
        public void run() {
            try {
                QiNiuUtils qiNiuUtils = BeanContext.getApplicationContext().getBean(QiNiuUtils.class);
                RedisTemplate redisTemplate = BeanContext.getApplicationContext().getBean("redisTemplate", RedisTemplate.class);
                String url;
                logger.info("文件名:{}，大小:{}KB，key:{} =========> 上传中。。。。",file.getOriginalFilename(),file.getSize()/1024, key);
                if(1 == type){
                    url = qiNiuUtils.upload(file,Boolean.TRUE);
                } else {
                    url = qiNiuUtils.upload(file,Boolean.FALSE);
                }
                logger.info("上传完毕Url:{}",url);
                redisTemplate.opsForValue().set(String.format("%s%s",IMAGE_KEY,key), ResponseContent.buildSuccess("success",url), 60 * 60, TimeUnit.SECONDS);
            } catch (SocketTimeoutException e1) {
                logger.error("MyUpTask timeout: {},key{}", e1.getMessage(),key);
                redisTemplate.opsForValue().set(String.format("%s%s",IMAGE_KEY,key), ResponseContent.buildServerError("error:timeout 请重新上传"), 60 * 60, TimeUnit.SECONDS);
            } catch (IOException e) {
                logger.error("MyUpTask error: {} ,key{} , {}",e.getMessage(),key,e);
                redisTemplate.opsForValue().set(String.format("%s%s",IMAGE_KEY,key), ResponseContent.buildServerError("error:"+e.getMessage()), 60 * 60, TimeUnit.SECONDS);
            }
        }
    }

}
