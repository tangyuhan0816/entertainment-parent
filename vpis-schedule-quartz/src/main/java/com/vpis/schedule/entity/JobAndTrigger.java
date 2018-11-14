package com.vpis.schedule.entity;

import lombok.Data;

import java.math.BigInteger;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: JobAndTrigger
 *  @package: com.vpis.schedule.entity
 *  @Date: Created in 2018/11/14 上午10:54
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */
@Data
public class JobAndTrigger {
    private String JOB_NAME;
    private String JOB_GROUP;
    private String JOB_CLASS_NAME;
    private String TRIGGER_NAME;
    private String TRIGGER_GROUP;
    private BigInteger REPEAT_INTERVAL;
    private BigInteger TIMES_TRIGGERED;
    private String CRON_EXPRESSION;
    private String TIME_ZONE_ID;
}
