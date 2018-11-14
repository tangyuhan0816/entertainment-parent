package com.vpis.schedule.job.order;

import com.alibaba.fastjson.JSONObject;
import com.vpis.common.entity.order.Order;
import com.vpis.schedule.service.order.OrderService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: OrderJob
 *  @package: com.vpis.schedule.job.order
 *  @Date: Created in 2018/11/14 上午11:23
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: Order定时任务
 */
public class OrderJob extends QuartzJobBean {

    private static Logger logger = LoggerFactory.getLogger(OrderJob.class);

    @Autowired
    private OrderService orderService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Order order = orderService.find();
        logger.info(JSONObject.toJSONString(order));
    }
}
