package com.vpis.asset.utils;

import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.vpis.common.utils.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
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

    @Value("${qiniu.access_key}")
    private String accessKey;

    @Value("${qiniu.secret_key}")
    private String secretKey;

    @Value("${qiniu.bucket.image.name}")
    private String bucketImageName;

    @Value("${qiniu.bucket.video.name}")
    private String bucketVideoName;

    @Value("${qiniu.bucket.image.domain}")
    private String domainImageName;

    @Value("${qiniu.bucket.video.domain}")
    private String domainVideoName;

    public String upload(MultipartFile file, Boolean isImage) throws IOException {
        byte[] uploadBytes = file.getBytes();
        Configuration cfg = new Configuration(Zone.zone0());
        UploadManager uploadManager = new UploadManager(cfg);

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = null;
        String iDomain = null;
        if (isImage) {
            iDomain = domainImageName;
            upToken = auth.uploadToken(bucketImageName);
        } else {
            iDomain = domainVideoName;
            upToken = auth.uploadToken(bucketVideoName);
        }

        String fileName = file.getOriginalFilename();
        String extName = getExtensionName(fileName);
        String resourceName = UUID.randomUUID().toString().replaceAll("-", "") + "." + extName;

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
