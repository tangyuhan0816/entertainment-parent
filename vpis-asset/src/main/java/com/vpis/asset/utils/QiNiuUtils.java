package com.vpis.asset.utils;

import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.vpis.common.utils.Preconditions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: QiNiuUtils
 *  @package: com.vpis.asset.utils
 *  @Date: Created in 2018/10/26 下午8:59
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 七牛云盘工具类
 */
@Component
public class QiNiuUtils {

    private static String accessKey;

    private static String secretKey;

    private static String bucket;

    private static String domain;

    @Value("${qiniu.image.access_key}")
    public void setAccessKey(String iaccessKey) {
        accessKey = iaccessKey;
    }

    @Value("${qiniu.image.secret_key}")
    public void setSecretKey(String isecretKey) {
        secretKey = isecretKey;
    }

    @Value("${qiniu.image.bucket}")
    public void setBucket(String ibucket) {
        bucket = ibucket;
    }

    @Value("${qiniu.image.domain}")
    public void setDomain(String idomain) {
        domain = idomain;
    }

    public static String uploadVideo(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String extName = getExtensionName(fileName);
        String resourceName = "vpis_image_" + UUID.randomUUID().toString().replaceAll("-","") + "." + extName;
        return upload(file,resourceName);
    }

    public static String uploadImage(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String extName = getExtensionName(fileName);
        String resourceName = "vpis_video_" + UUID.randomUUID().toString().replaceAll("-","") + "." + extName;
        return upload(file,resourceName);
    }

    public static String upload(MultipartFile file,String resourceName) throws IOException {

        Configuration cfg = new Configuration(Zone.zone0());
        UploadManager uploadManager = new UploadManager(cfg);

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        byte[] uploadBytes = file.getBytes();
        Response response = uploadManager.put(uploadBytes, resourceName, upToken);

        //解析上传成功的结果
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        String url = null;
        if (Preconditions.isNotBlank(putRet.hash)) {
            url = domain + resourceName;
        }

        return url;
    }

    private static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }
}
