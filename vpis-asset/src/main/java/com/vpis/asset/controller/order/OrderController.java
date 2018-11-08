package com.vpis.asset.controller.order;

import com.alibaba.fastjson.JSONObject;
import com.vpis.asset.bean.order.OrderBean;
import com.vpis.asset.controller.BaseController;
import com.vpis.asset.service.order.OrderService;
import com.vpis.common.utils.ResponseContent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: OrderController
 *  @package: com.vpis.asset.controller.order
 *  @Date: Created in 2018/11/8 下午6:17
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 订单接口
 */
@RestController
@RequestMapping("/api/v1/vpis/order/")
@Api(description = "订单接口")
public class OrderController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "统一创建订单接口 ，Owner: yuhan.tang", response = ResponseContent.class)
    @RequestMapping(path = "/create", method = {RequestMethod.POST})
    public ResponseContent create(@RequestBody OrderBean bean){
        logger.info("统一创建订单接口 create =======> {}", JSONObject.toJSONString(bean));
        Long userId = getSessionUserId();
        orderService.createOrderInfo(userId, bean);
        return ResponseContent.buildSuccess();
    }
}
