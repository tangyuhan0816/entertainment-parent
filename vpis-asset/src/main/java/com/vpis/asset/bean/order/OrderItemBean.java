package com.vpis.asset.bean.order;

import com.vpis.asset.bean.base.BaseBean;
import lombok.Data;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: OrderItemBean
 *  @package: com.vpis.asset.bean.order
 *  @Date: Created in 2018/11/8 下午3:35
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 订单项bean
 */
@Data
public class OrderItemBean extends BaseBean{

    private Long productId;

    private Integer count;
}
