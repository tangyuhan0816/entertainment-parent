package com.vpis.asset.service.order;

import com.alibaba.fastjson.JSONObject;
import com.vpis.asset.bean.order.OrderBean;
import com.vpis.asset.bean.order.OrderItemBean;
import com.vpis.asset.bean.vo.HouseVo;
import com.vpis.asset.bean.vo.OrderItemHouseVo;
import com.vpis.asset.bean.vo.OrderVo;
import com.vpis.asset.dao.order.OrderDao;
import com.vpis.asset.dao.order.OrderItemDao;
import com.vpis.asset.repository.order.OrderItemRepository;
import com.vpis.asset.repository.order.OrderRepository;
import com.vpis.asset.service.house.HouseService;
import com.vpis.asset.service.pay.IPayService;
import com.vpis.asset.service.sys.MouldsService;
import com.vpis.asset.service.sys.SysUserService;
import com.vpis.asset.utils.BeanContext;
import com.vpis.common.entity.order.Order;
import com.vpis.common.entity.order.OrderItem;
import com.vpis.common.entity.pay.request.PayAsyncRequest;
import com.vpis.common.entity.pay.request.PayRequest;
import com.vpis.common.entity.pay.response.wechat.PayResponse;
import com.vpis.common.entity.sys.Moulds;
import com.vpis.common.entity.sys.TbUser;
import com.vpis.common.exception.STException;
import com.vpis.common.page.PageableResponse;
import com.vpis.common.type.order.OrderStatus;
import com.vpis.common.type.pay.PayTypeEnum;
import com.vpis.common.utils.DateUtils;
import com.vpis.common.utils.OrderUtil;
import com.vpis.common.utils.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: OrderService
 *  @package: com.vpis.asset.service.order
 *  @Date: Created in 2018/11/8 下午3:04
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private HouseService houseService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private MouldsService mouldsService;

    private static final String SUM_COUNT = "sum_count";
    private static final String SUM_PRICE = "sum_price";

    private static final String PRICE_UNIT = "RMB";
    private static final String ORDER_NAME = "视投科技-楼盘下单";

    private static final String SUBJECT = "视投科技";

    /**
     * 校验订单项
     * @param orderItemBeans
     */
    public Map<String, Object> checkOrderItem(List<OrderItemBean> orderItemBeans){
        if(Preconditions.isBlank(orderItemBeans)){
            throw new STException("订单项为空");
        }

        Map<String, Object> resultMap = new HashMap<>(5);

        Integer sumCount = 0;

        BigDecimal sumPrice = BigDecimal.ZERO;

        for(OrderItemBean orderItem : orderItemBeans){
            HouseVo house = houseService.findDetail(orderItem.getProductId());
            if(Preconditions.isBlank(house)){
                throw new STException("小区未查询到数据");
            }
            if(orderItem.getCount() > house.getAdviceNum()){
                throw new STException("选择项超出可投放数量");
            }
            sumCount = sumCount + orderItem.getCount();
            sumPrice = sumPrice.add(house.getPrice());
        }

        resultMap.put(SUM_COUNT, sumCount);

        resultMap.put(SUM_PRICE, sumPrice);

        return resultMap;
    }


    /**
     * 创建订单
     */
    @Transactional(rollbackFor = Exception.class)
    public PayResponse createOrderInfo(Long userId,String spbillCreateIp, OrderBean orderBean){

        if(Preconditions.isBlank(orderBean) ||
                Preconditions.isBlank(orderBean.getStartDate()) ||
                Preconditions.isBlank(orderBean.getEndDate()) ||
                Preconditions.isBlank(orderBean.getOrderItemList()) ||
                Preconditions.isBlank(orderBean.getSumCount())){
            throw new STException("参数不全");
        }

        if(orderBean.getEndDate().compareTo(orderBean.getStartDate()) <= 0){
            throw new STException("时间异常");
        }

        List<Integer> countList = orderBean.getOrderItemList().stream().map(OrderItemBean::getCount).collect(Collectors.toList());

        for (Integer integer : countList) {
            if(integer < 1){
                throw new STException("商品数量不允许小于1");
            }
        }

        Map<String, Object> resultMap = checkOrderItem(orderBean.getOrderItemList());

        if(!resultMap.get(SUM_COUNT).equals(orderBean.getSumCount())){
            throw new STException("总数量与订单项数量不匹配");
        }

        TbUser user = sysUserService.findById(userId);

        if(Preconditions.isBlank(user)){
            throw new STException("未查询到用户信息");
        }

        //支付渠道
        PayTypeEnum payTypeEnum = PayTypeEnum.getIndex(orderBean.getPayType());

        if(Preconditions.isBlank(payTypeEnum)){
            throw new STException("未查询到支付渠道");
        }

        Moulds mould = mouldsService.findByIdAndUserId(orderBean.getMouldId(), userId);

        if(Preconditions.isBlank(mould)){
            throw new STException("未查询到模版信息");
        }

        List<OrderItem> orderItemList = new ArrayList<>(orderBean.getOrderItemList().size());

        for(OrderItemBean orderItemBean : orderBean.getOrderItemList()){
            HouseVo house = houseService.findDetail(orderItemBean.getProductId());
            OrderItem orderItem = createOrderItem(house,orderItemBean.getCount());
            orderItemList.add(orderItem);
        }

        //创建订单
        Order order = createOrder(user, orderItemList, payTypeEnum.ordinal(), mould);

        //给订单项赋值Order Id
        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderId(order.getId());
        }

        //创建订单项
        orderItemRepository.saveAll(orderItemList);

        IPayService iPayService = BeanContext.payServiceMap.get(PayTypeEnum.getIndex(orderBean.getPayType()).getServiceName());;

        // 对接第三方处理下单，支付逻辑
        PayRequest payRequest = new PayRequest();
        payRequest.setOrderAmount(order.getOrderAmountTotal().doubleValue());
        payRequest.setOrderId(order.getOrderNo());
        //商品描述交易字段格式根据不同的应用场景按照以下格式：
        //APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayType(payTypeEnum);
        payRequest.setSpbillCreateIp(spbillCreateIp);
        payRequest.setSubject(SUBJECT);
        PayResponse payResponse = iPayService.pay(payRequest);
        if(Preconditions.isBlank(payRequest)){
            throw new STException("统一下单异常");
        }

        String paymentConfigInfo = JSONObject.toJSONString(payResponse);
        order.setPaymentConfigInfo(paymentConfigInfo);
        orderRepository.save(order);

        return payResponse;
    }

    public Order createOrder(TbUser user, List<OrderItem> orderItemList, Integer payType, Moulds mould){
        Order order = new Order();

        String orderNo = OrderUtil.generateOrderId("H");
        order.setOrderNo(orderNo);

        BigDecimal amountTotal = orderItemList.stream().map(OrderItem::getProductAmountTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setOrderAmountTotal(amountTotal.setScale(2, BigDecimal.ROUND_UP));
        order.setProductAmountTotal(amountTotal.setScale(2, BigDecimal.ROUND_UP));

        order.setPriceUnit(PRICE_UNIT);

        order.setPhoneNumber(user.getPhoneNum());

        order.setName(user.getNick());

        order.setUserId(user.getUserId());

        order.setProviderId(user.getParentId());

        order.setMouldCode(mould.getMouldCode());

        order.setMouldId(mould.getId());

        //0 购买   1其它方式
        order.setOrderType(0);

        order.setPayChannel(payType);

        order.setOrderStatus(OrderStatus.PENDING.ordinal());

        return orderRepository.save(order);
    }

    private OrderItem createOrderItem(HouseVo house, Integer productCount){
        OrderItem orderItem = new OrderItem();

        orderItem.setProductName(house.getHouseName());

        //商品单价
        orderItem.setProductAmount(house.getPrice());

        //商品总价格
        BigDecimal amountTotle = house.getPrice().multiply(new BigDecimal(productCount));
        orderItem.setProductAmountTotal(amountTotle);

        //商品市场总价
        BigDecimal marketPrice = house.getMarketPrice().multiply(new BigDecimal(productCount));
        orderItem.setProductPriceTotal(marketPrice);

        orderItem.setProductCount(productCount);

        orderItem.setPriceUnit(PRICE_UNIT);

        orderItem.setPriceUnit(PRICE_UNIT);

        orderItem.setProductId(house.getId());

        return orderItem;
    }

    /**
     * 查询订单列表
     * @param userId
     * @param pageSize
     * @param pageNumber
     */
    public PageableResponse<OrderVo> findOrderByList(Long userId, Integer pageNumber, Integer pageSize, Integer orderStatus){

        Page<Order> page = orderRepository.findByUserIdAndOrderStatusAndDeletedIsFalseOrderByCreateDateTimeDesc(userId, orderStatus, PageRequest.of(pageNumber, pageSize));

        PageableResponse<OrderVo> response = new PageableResponse<>();

        List<OrderVo> result = new ArrayList<>();

        for(Order order : page.getContent()){
            List<OrderItemHouseVo> orderHouse = orderItemDao.findPage(order.getId());
            OrderVo vo = new OrderVo();
            vo.setHouses(orderHouse);
            vo.setOrderStatus(order.getOrderStatus());
            vo.setId(order.getId());
            vo.setOrderAmountTotal(order.getOrderAmountTotal());
            vo.setOrderNo(order.getOrderNo());
            result.add(vo);
        }

        response.setList(result);

        response.setTotalCount(page.getTotalElements());

        response.setTotalPages(page.getTotalPages());
        return response;
    }

    /**
     * 二次支付
     * @param orderNo
     * @return
     */
    public PayResponse payAgen(String orderNo){
        Order order = orderRepository.findByOrderNoAndDeletedIsFalse(orderNo);
        if(Preconditions.isBlank(order)){
            throw new STException("order not found");
        }
        if(Preconditions.isBlank(order.getPaymentConfigInfo())){
            throw new STException("该订单未发起支付");
        }
        return JSONObject.parseObject(order.getPaymentConfigInfo(), PayResponse.class);
    }

    public BigDecimal findAmountByOrderNo(String orderNo){
        Order order = orderRepository.findByOrderNoAndDeletedIsFalse(orderNo);
        if(Preconditions.isNotBlank(order)){
            return order.getOrderAmountTotal();
        }
        return BigDecimal.ZERO;
    }

    public void updateOrderError(String orderNo){
        Order order = orderRepository.findByOrderNoAndDeletedIsFalse(orderNo);
        if(Preconditions.isNotBlank(order)){
            order.setOrderStatus(OrderStatus.FAIL.ordinal());
            orderRepository.save(order);
        }
    }

    public void updateOrderSuccess(String orderNo, String tradeNo){
        Order order = orderRepository.findByOrderNoAndDeletedIsFalse(orderNo);
        if(Preconditions.isNotBlank(order)){

            if (order.getOrderStatus().equals(OrderStatus.PROCESSING.ordinal())) {
                throw new STException("【回调SUCCESS】订单已完成，请勿重复支付！");
            }

            if (order.getOrderStatus().equals(OrderStatus.FAIL.ordinal())) {
                throw new STException("【回调SUCCESS】订单已取消");
            }
            order.setOrderStatus(OrderStatus.PROCESSING.ordinal());
            order.setPayTime(new Date());
            order.setPaymentOrderNo(tradeNo);
            orderRepository.save(order);
        }
    }

}
