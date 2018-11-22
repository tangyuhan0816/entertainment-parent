package com.vpis.asset.bean.order;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpis.asset.bean.base.BaseBean;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: OrderBean
 *  @package: com.vpis.asset.bean
 *  @Date: Created in 2018/11/8 下午3:33
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 支付bean
 */
@Data
public class OrderBean extends BaseBean {

    @ApiModelProperty(value = "订单项")
    @JSONField(name = "order_item_list")
    @JsonProperty("order_item_list")
    private List<OrderItemBean> orderItemList;

    @ApiModelProperty(value = "总数量")
    @JSONField(name = "sum_count")
    @JsonProperty("sum_count")
    private Integer sumCount;

    @ApiModelProperty(value = "总数量")
    @JSONField(name = "start_date")
    @JsonProperty("start_date")
    private Date startDate;

    @ApiModelProperty(value = "总数量")
    @JSONField(name = "end_date")
    @JsonProperty("end_date")
    private Date endDate;

    @ApiModelProperty(value = "支付类型")
    @JSONField(name = "pay_type")
    @JsonProperty("pay_type")
    private Integer payType;

    @ApiModelProperty(value = "模版ID")
    private Long mouldId;

    @ApiModelProperty(value = "模版投放区域code")
    private Integer code;
}