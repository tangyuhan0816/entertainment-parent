package com.vpis.schedule.dao.order;

import com.vpis.common.entity.order.Order;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: OrderDao
 *  @package: com.vpis.schedule.dao.order
 *  @Date: Created in 2018/11/14 上午11:20
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
public interface OrderDao {

    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}