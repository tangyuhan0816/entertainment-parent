package com.entertainment.asset.controller.cache;

import com.entertainment.common.utils.ResponseContent;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/asset/cache")

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: CacheController
 *  @package: com.entertainment.asset.controller.cache
 *  @Date: Created in 2018/8/3 下午4:07
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: redis 缓存接口
 */    
public class CacheController {

    public ResponseContent emailTemplate(){
        return ResponseContent.buildSuccess();
    }
}
