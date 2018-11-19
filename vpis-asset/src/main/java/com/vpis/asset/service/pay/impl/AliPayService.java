package com.vpis.asset.service.pay.impl;

import com.vpis.asset.service.pay.IPayService;
import com.vpis.common.entity.pay.request.wechat.PayRequest;
import com.vpis.common.entity.pay.response.wechat.PayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: AliPayService
 *  @package: com.vpis.asset.service.pay.impl
 *  @Date: Created in 2018/11/19 下午8:05
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 支付宝
 */
@Slf4j
@Service("aliPayService")
public class AliPayService implements IPayService{

    public static final String TEST = "test";

    @Override
    public PayResponse pay(PayRequest payRequest) {
        return null;
    }
}
