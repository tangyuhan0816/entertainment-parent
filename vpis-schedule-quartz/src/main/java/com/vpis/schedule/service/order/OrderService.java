package com.vpis.schedule.service.order;

import com.vpis.common.entity.order.Order;
import com.vpis.schedule.dao.order.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: OrderService
 *  @package: com.vpis.schedule.service.order
 *  @Date: Created in 2018/11/13 下午3:39
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 
 */    
@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    public List<Order> findByOrderStatus(){
        return orderDao.findByOrderStatus();
    }

    public void updateOrderStatus(List<Order> list){
        orderDao.updateStatus(list);
    }
}
