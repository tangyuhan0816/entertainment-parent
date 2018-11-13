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
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "订单查询 ，Owner: yuhan.tang", response = ResponseContent.class)
    @RequestMapping(path = "/page", method = {RequestMethod.GET})
    public ResponseContent page(@RequestParam(name = "page_number", defaultValue = "0") Integer pageNumber,
                                @RequestParam(name = "page_size", defaultValue = "10") Integer pageSize,
                                @RequestParam(name = "order_status", defaultValue = "", required = false) Integer orderStatus){
        logger.info("订单查询 page =======> {},{},{}", pageNumber,pageSize,orderStatus);
        Long userId = getSessionUserId();
        return ResponseContent.buildSuccess(orderService.findOrderByList(userId, pageNumber,pageSize,orderStatus));
    }
}
