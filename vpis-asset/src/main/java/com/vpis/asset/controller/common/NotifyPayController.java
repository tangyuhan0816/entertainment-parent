package com.vpis.asset.controller.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

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

    /**
     * 上报微信异步回调内容
     * @param notifyData
     * @return
     * @throws Exception
     */
    @PostMapping("/pay/notify.htm")
    public ModelAndView notify(@RequestBody String notifyData) {
        log.info("【异步回调】request={}", notifyData);
        try {
//            payOrderService.doWxNotify(notifyData);
            log.info("【异步回调】结束");
        } catch (Exception e) {
            log.info("【异步回调】异常, response={}", e.getMessage());
        }

        return new ModelAndView("pay/success");
    }
}
