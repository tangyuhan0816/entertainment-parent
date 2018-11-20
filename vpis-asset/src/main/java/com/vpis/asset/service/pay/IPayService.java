package com.vpis.asset.service.pay;

import com.vpis.common.entity.pay.request.wechat.PayRequest;
import com.vpis.common.entity.pay.response.wechat.PayResponse;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: IPayService
 *  @package: com.vpis.asset.service.pay
 *  @Date: Created in 2018/11/18 下午3:21
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
public interface IPayService {

    PayResponse pay(PayRequest payRequest);

    void doNotify(String notifyData);

}
