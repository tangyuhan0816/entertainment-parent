package com.vpis.schedule.job;

import com.vpis.schedule.service.order.OrderService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class TestJob extends QuartzJobBean{

    @Autowired
    private OrderService orderService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        orderService.play();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        orderService.study();
    }
}
