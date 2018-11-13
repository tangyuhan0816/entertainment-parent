package com.vpis.common.type.order;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: OrderStatus
 *  @package: com.vpis.common.type.order
 *  @Date: Created in 2018/11/8 下午3:32
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 订单状态
 */
public enum OrderStatus {
    //未支付
    PENDING("PENDING", "0"),
    //已支付 == 待审核
    PROCESSING("PROCESSING", "1"),
    //待投放
    AWAITING("AWAITING", "2"),
    //已完成
    COMPLETE("COMPLETE", "3"),
    //支付失败
    FAIL("FAIL", "4"),
    //已取消
    CANCELED("CANCELED", "5"),
    //已退款
    REFUNDED("REFUNDED", "6"),

    SYNCHING("SYNCHING", "7");

    private String name;

    private String index;

    OrderStatus(String name, String index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public static String getOrderStatus(int index) {
        switch (index) {
            case 0:
                return "未支付";
            case 1:
                return "已支付";
            case 2:
                return "待审核";
            case 4:
                return "已完成";
            default:
                return null;
        }
    }
}
