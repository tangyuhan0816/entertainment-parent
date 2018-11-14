package com.vpis.schedule.dao.quartz;

import com.vpis.common.entity.quartz.JobAndTriggers;
import com.vpis.common.entity.quartz.JobAndTriggersKey;
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

    int deleteByPrimaryKey(JobAndTriggersKey key);

    int insert(JobAndTriggers record);

    int insertSelective(JobAndTriggers record);

    JobAndTriggers selectByPrimaryKey(JobAndTriggersKey key);

    int updateByPrimaryKeySelective(JobAndTriggers record);

    int updateByPrimaryKeyWithBLOBs(JobAndTriggers record);

    int updateByPrimaryKey(JobAndTriggers record);

    List<JobAndTrigger> getJobAndTriggerDetails();
}