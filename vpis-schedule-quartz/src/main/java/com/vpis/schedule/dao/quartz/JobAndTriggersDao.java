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

    /**
     * 仅查询复杂(Cron)Job
     * @return
     */
    List<JobAndTrigger> getJobAndTriggerDetails();

    /**
     * 查询包含Simple类型的Job
     * @return
     */
    List<JobAndTrigger> getJobAndTriggerDetailsAndSimple();
}