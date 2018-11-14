package com.vpis.schedule.mapper;

import com.vpis.common.entity.quartz.JobAndTriggers;
import com.vpis.common.entity.quartz.JobAndTriggersKey;

public interface JobAndTriggersMapper {
    int deleteByPrimaryKey(JobAndTriggersKey key);

    int insert(JobAndTriggers record);

    int insertSelective(JobAndTriggers record);

    JobAndTriggers selectByPrimaryKey(JobAndTriggersKey key);

    int updateByPrimaryKeySelective(JobAndTriggers record);

    int updateByPrimaryKeyWithBLOBs(JobAndTriggers record);

    int updateByPrimaryKey(JobAndTriggers record);
}