package com.vpis.asset.controller.common;

import com.vpis.asset.utils.BeanContext;
import com.vpis.common.type.pay.PayTypeEnum;
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

    private static final Integer wxPayIndex = 3;

    private static final Integer aliPayIndex = 0;

    /**
     * 上报微信异步回调内容
     * @param notifyData
     * @return
     * @throws Exception
     */
    @PostMapping("/wpay/app/notify.htm")
    public ModelAndView wxnotify(@RequestBody String notifyData) {
        log.info("【异步回调】request={}", notifyData);
        try {
            BeanContext.payServiceMap.get(PayTypeEnum.getIndex(wxPayIndex)).doNotify(notifyData);
            log.info("【异步回调】结束");
        } catch (Exception e) {
            log.info("【异步回调】异常, response={}", e.getMessage());
        }

        return new ModelAndView("pay/success");
    }

    @PostMapping("/apay/app/notify.htm")
    public ModelAndView alnotify(@RequestBody String notifyData) {
        log.info("【异步回调】request={}", notifyData);
        try {
            BeanContext.payServiceMap.get(PayTypeEnum.getIndex(aliPayIndex)).doNotify(notifyData);
            log.info("【异步回调】结束");
        } catch (Exception e) {
            log.info("【异步回调】异常, response={}", e.getMessage());
        }

        return new ModelAndView("pay/success");
    }
}
