package com.vpis.asset.bean.order;

import com.vpis.asset.bean.base.BaseBean;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "产品ID")
    private Long productId;

    @ApiModelProperty(value = "购买产品数量")
    private Integer count;
}
