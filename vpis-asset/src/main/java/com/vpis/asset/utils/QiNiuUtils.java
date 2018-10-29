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

    private static String bucketImageName;

    private static String bucketVideoName;

    private static String domainImageName;

    private static String domainVideoName;

    @Value("${qiniu.access_key}")
    private void setAccessKey(String iaccessKey) {
        accessKey = iaccessKey;
    }

    @Value("${qiniu.secret_key}")
    private void setSecretKey(String isecretKey) {
        secretKey = isecretKey;
    }

    @Value("${qiniu.bucket.image.name}")
    private void setBucketImageName(String ibucketImageName) {
        bucketImageName = ibucketImageName;
    }

    @Value("${qiniu.bucket.image.domain}")
    private void setDomainImageName(String idomainImageName) {
        domainImageName = idomainImageName;
    }

    @Value("${qiniu.bucket.video.name}")
    private void setBucketVideoName(String ibucketVideoName) {
        bucketVideoName = ibucketVideoName;
    }

    @Value("${qiniu.bucket.video.domain}")
    private void setDomainVideoName(String idomainVideoName) {
        domainVideoName = idomainVideoName;
    }

    public String uploadImage(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String extName = getExtensionName(fileName);
        String resourceName = "vpis_image_" + UUID.randomUUID().toString().replaceAll("-","") + "." + extName;
        return upload(file,resourceName ,Boolean.FALSE);
    }

    public String uploadVideo(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String extName = getExtensionName(fileName);
        String resourceName = "vpis_video_" + UUID.randomUUID().toString().replaceAll("-","") + "." + extName;
        return upload(file,resourceName, Boolean.TRUE);
    }

    private String upload(MultipartFile file,String resourceName, Boolean isImage) throws IOException {

        Configuration cfg = new Configuration(Zone.zone0());
        UploadManager uploadManager = new UploadManager(cfg);

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = null;
        String iDomain = null;
        if(isImage){
            iDomain = domainImageName;
            upToken = auth.uploadToken(bucketImageName);
        }else {
            iDomain = domainVideoName;
            upToken = auth.uploadToken(bucketVideoName);
        }

        byte[] uploadBytes = file.getBytes();
        Response response = uploadManager.put(uploadBytes, resourceName, upToken);

        //解析上传成功的结果
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        String url = null;
        if (Preconditions.isNotBlank(putRet.hash)) {
            url = iDomain + resourceName;
        }

        return url;
    }

    private String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }
}
