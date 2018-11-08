package com.vpis.common.entity.order;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vpis.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: OrderItem
 *  @package: com.vpis.common.entity.order
 *  @Date: Created in 2018/11/8 下午2:56
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
@Data
@Entity
@Table(name = "order_item")
public class OrderItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键")
    @JSONField(name = "id")
    @JsonProperty("id")
    @Column(name = "id")
    private Long id;

    @ApiModelProperty(value = "订单id")
    @JSONField(name = "order_id")
    @JsonProperty("order_id")
    @Column(name = "order_id")
    private Long orderId;

    @ApiModelProperty(value = "商品名称")
    @JSONField(name = "product_name")
    @JsonProperty("product_name")
    @Column(name = "product_name")
    private String productName;

    @ApiModelProperty(value = "商品单价")
    @JSONField(name = "product_amount")
    @JsonProperty("product_amount")
    @Column(name = "product_amount")
    private BigDecimal productAmount;

    @ApiModelProperty(value = "商品总价")
    @JSONField(name = "product_amount_total")
    @JsonProperty("product_amount_total")
    @Column(name = "product_amount_total")
    private BigDecimal productAmountTotal;

    @ApiModelProperty(value = "商品市场总价格")
    @JSONField(name = "product_price_total")
    @JsonProperty("product_price_total")
    @Column(name = "product_price_total")
    private BigDecimal productPriceTotal;

    @ApiModelProperty(value = "商品数量")
    @JSONField(name = "product_count")
    @JsonProperty("product_count")
    @Column(name = "product_count")
    private Integer productCount;

    @ApiModelProperty(value = "价格单位")
    @JSONField(name = "price_unit")
    @JsonProperty("price_unit")
    @Column(name = "price_unit")
    private String priceUnit;

    @ApiModelProperty(value = "商品单位")
    @JSONField(name = "product_unit")
    @JsonProperty("product_unit")
    @Column(name = "product_unit")
    private String productUnit;

    @ApiModelProperty(value = "产品id")
    @JSONField(name = "product_id")
    @JsonProperty("product_id")
    @Column(name = "product_id")
    private Long productId;

}
