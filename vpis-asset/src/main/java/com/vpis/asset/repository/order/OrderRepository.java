package com.vpis.asset.repository.order;

import com.vpis.common.base.BaseEntityRepository;
import com.vpis.common.entity.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: OrderRepository
 *  @package: com.vpis.asset.dao.order
 *  @Date: Created in 2018/11/8 下午3:03
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
public interface OrderRepository extends BaseEntityRepository<Order>, JpaSpecificationExecutor<Order>{

    Order findByOrderNoAndDeletedIsFalse(String orderNo);
}
