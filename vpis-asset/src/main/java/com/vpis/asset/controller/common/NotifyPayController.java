package com.vpis.asset.controller.common;

import com.alibaba.fastjson.JSONObject;
import com.vpis.asset.utils.BeanContext;
import com.vpis.common.type.pay.PayTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: NotifyPayController
 *  @package: com.vpis.asset.controller.common
 *  @Date: Created in 2018/11/19 下午9:29
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 
 */    
@Slf4j
@Controller
public class NotifyPayController {

    private static final Integer WXPAYINDEX = 3;

    /**
     * 上报微信异步回调内容
     * @param notifyData
     * @return
     * @throws Exception
     */
    @PostMapping("/pay/wpay/notify.htm")
    public ModelAndView wechatNotify(@RequestBody String notifyData) {
        log.info("【微信异步回调】request={}", notifyData);
        try {
            BeanContext.payServiceMap.get(PayTypeEnum.getIndex(WXPAYINDEX).getServiceName()).doNotify(notifyData);
            log.info("【微信异步回调】结束");
        } catch (Exception e) {
            log.info("【微信异步回调】异常, response={}", e.getMessage());
        }

        return new ModelAndView("wechat/pay/success");
    }

    private static final Integer ALIPAYINDEX = 0;

    @PostMapping("/pay/apay/notify.htm")
    public ModelAndView aliPayNotify(HttpServletRequest request) {
        String notifyData = JSONObject.toJSONString(request.getParameterMap());
        log.info("【支付宝异步回调】request={}", notifyData);
        try {
            BeanContext.payServiceMap.get(PayTypeEnum.getIndex(ALIPAYINDEX).getServiceName()).doNotify(request);
            log.info("【支付宝异步回调】结束");
        } catch (Exception e) {
            log.info("【支付宝异步回调】异常, response={}", e.getMessage());
        }

        return new ModelAndView("alipay/pay/success");
    }
}
