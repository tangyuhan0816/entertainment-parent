package com.vpis.schedule.dao.business.order;

import com.vpis.common.entity.order.Order;

import java.util.List;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: OrderDao
 *  @package: com.vpis.schedule.dao.business.order
 *  @Date: Created in 2018/11/14 上午11:20
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
public interface OrderDao {

    int insert(Order record);

    Order findById(Long id);

    /**
     * 获取一小时没支付的订单
     * @return
     */
    List<Order> findByOrderStatus();

    Integer updateStatus(List<Order> list);
}