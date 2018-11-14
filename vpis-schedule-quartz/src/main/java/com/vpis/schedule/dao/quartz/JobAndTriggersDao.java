package com.vpis.schedule.dao.quartz;

import com.vpis.schedule.entity.JobAndTrigger;

import java.util.List;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: JobAndTriggersDao
 *  @package: com.vpis.schedule.dao
 *  @Date: Created in 2018/11/14 上午10:56
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
public interface JobAndTriggersDao {

    List<JobAndTrigger> getJobAndTriggerDetails();
}