package com.vpis.schedule.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: TestJob
 *  @package: com.vpis.schedule.job
 *  @Date: Created in 2018/12/4 下午1:30
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 测试JOB
 */
public class TestJob extends QuartzJobBean{

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("开始-----------");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("结束-----------");
    }
}
