package com.vpis.schedule.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vpis.common.utils.Preconditions;
import com.vpis.schedule.dao.quartz.JobAndTriggersDao;
import com.vpis.schedule.entity.JobAndTrigger;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: QuartzService
 *  @package: com.vpis.schedule.service
 *  @Date: Created in 2018/11/13 下午3:33
 *  @email yuhan.tang@magicwindow.cn
 *  @Description:
 */    
@Service
public class QuartzService {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private JobAndTriggersDao jobAndTriggersDao;

    @PostConstruct
    public void startScheduler() {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 增加一个job
     *
     * @param jobClass
     *            任务实现类
     * @param jobName
     *            任务名称
     * @param jobGroupName
     *            任务组名
     * @param jobTime
     *            时间表达式 （如：0/5 * * * * ? ）
     */
    public void addJob(Class<? extends QuartzJobBean> jobClass, String jobName, String jobGroupName, String jobTime) {
        try {
            // 创建jobDetail实例，绑定Job实现类
            // 指明job的名称，所在组的名称，以及绑定job类
            // 任务名称和组构成任务key
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName)
                    .build();
            // 定义调度触发规则
            // 使用cornTrigger规则
            // 触发器key
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroupName)
                    .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.SECOND))
                    .withSchedule(CronScheduleBuilder.cronSchedule(jobTime)).startNow().build();
            // 把作业和触发器注册到任务调度中
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改 一个job的 时间表达式
     *
     * @param jobName
     * @param jobGroupName
     * @param jobTime
     */
    public void updateJob(String jobName, String jobGroupName, String jobTime) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                    .withSchedule(CronScheduleBuilder.cronSchedule(jobTime)).build();
            // 重启触发器
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除任务一个job
     *
     * @param jobName
     *            任务名称
     * @param jobGroupName
     *            任务组名
     */
    public void deleteJob(String jobName, String jobGroupName) {
        try {
            scheduler.deleteJob(new JobKey(jobName, jobGroupName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 暂停一个job
     *
     * @param jobName
     * @param jobGroupName
     */
    public void pauseJob(String jobName, String jobGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 恢复一个job
     *
     * @param jobName
     * @param jobGroupName
     */
    public void resumeJob(String jobName, String jobGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 立即执行一个job
     *
     * @param jobName
     * @param jobGroupName
     */
    public void runAJobNow(String jobName, String jobGroupName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有计划中的任务列表
     *
     * @return
     */
    public List<Map<String, Object>> queryAllJob() {
        List<Map<String, Object>> jobList = null;
        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
            jobList = new ArrayList<Map<String, Object>>();
            for (JobKey jobKey : jobKeys) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("jobName", jobKey.getName());
                    map.put("jobGroupName", jobKey.getGroup());
                    map.put("description", "触发器:" + trigger.getKey());
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    map.put("jobStatus", triggerState.name());
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        String cronExpression = cronTrigger.getCronExpression();
                        map.put("jobTime", cronExpression);
                    }
                    jobList.add(map);
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jobList;
    }

    /**
     * 获取所有正在运行的job
     *
     * @return
     */
    public List<Map<String, Object>> queryRunJob() {
        List<Map<String, Object>> jobList = null;
        try {
            List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
            jobList = new ArrayList<>(executingJobs.size());
            for (JobExecutionContext executingJob : executingJobs) {
                Map<String, Object> map = new HashMap<>();
                JobDetail jobDetail = executingJob.getJobDetail();
                JobKey jobKey = jobDetail.getKey();
                Trigger trigger = executingJob.getTrigger();
                map.put("jobName", jobKey.getName());
                map.put("jobGroupName", jobKey.getGroup());
                map.put("description", "触发器:" + trigger.getKey());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                map.put("jobStatus", triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    map.put("jobTime", cronExpression);
                }
                jobList.add(map);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jobList;
    }


    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;
    /****************************************************************JOB**************************************************************/


    /**
     * 增加一个job
     *
     * @param jobClass
     *            任务实现类
     * @param jobName
     *            任务名称
     * @param jobGroupName
     *            任务组名
     * @param jobTime
     *            时间表达式 (这是每隔多少秒为一次任务)
     * @param jobTimes
     *            运行的次数 （<0:表示不限次数）
     */
    public void addJobSimple(Class<? extends QuartzJobBean> jobClass, String jobName, String jobGroupName, int jobTime,
                             int jobTimes) {
        try {
            // 任务名称和组构成任务key
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName)
                    .build();
            // 使用simpleTrigger规则
            Trigger trigger = null;
            if (jobTimes < 0) {
                trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroupName)
                        .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1).withIntervalInSeconds(jobTime))
                        .startNow().build();
            } else {
                trigger = TriggerBuilder
                        .newTrigger().withIdentity(jobName, jobGroupName).withSchedule(SimpleScheduleBuilder
                                .repeatSecondlyForever(1).withIntervalInSeconds(jobTime).withRepeatCount(jobTimes))
                        .startNow().build();
            }
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void addJob(String jobClassName, String jobGroupName, String cronExpression) throws Exception {

        // 启动调度器
        schedulerFactoryBean.getScheduler().start();

        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass()).withIdentity(jobClassName, jobGroupName).build();

        //表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobClassName, jobGroupName)
                .withSchedule(scheduleBuilder).build();

        try {
            schedulerFactoryBean.getScheduler().scheduleJob(jobDetail, trigger);

        } catch (SchedulerException e) {
            System.out.println("创建定时任务失败" + e);
            throw new Exception("创建定时任务失败");
        }
    }


    public QuartzJobBean getClass(String classname) throws Exception {
        Class<?> class1 = Class.forName(classname);
        return (QuartzJobBean) class1.newInstance();
    }


    public void jobdelete(String jobClassName, String jobGroupName) throws Exception {
        schedulerFactoryBean.getScheduler().pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
        schedulerFactoryBean.getScheduler().unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroupName));
        schedulerFactoryBean.getScheduler().deleteJob(JobKey.jobKey(jobClassName, jobGroupName));
    }


    public void jobrescheduleSimple(String jobName, String jobGroupName, int jobTime, int jobTimes) throws Exception {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);

            SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.repeatSecondlyForever(1).withIntervalInSeconds(jobTime);

            SimpleTrigger trigger = (SimpleTrigger) schedulerFactoryBean.getScheduler().getTrigger(triggerKey);

            // 按新的jobTime表达式重新构建trigger
            if(jobTimes < 0){
                trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(simpleScheduleBuilder).build();
            }else {
                trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroupName).withSchedule(
                        simpleScheduleBuilder.withRepeatCount(jobTimes)).build();
            }

            // 按新的trigger重新设置job执行
            schedulerFactoryBean.getScheduler().rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            System.out.println("更新定时任务失败" + e);
            throw new Exception("更新定时任务失败");
        }
    }


    public void jobreschedule(String jobClassName, String jobGroupName, String cronExpression) throws Exception {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName, jobGroupName);
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

            CronTrigger trigger = (CronTrigger) schedulerFactoryBean.getScheduler().getTrigger(triggerKey);

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            schedulerFactoryBean.getScheduler().rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            System.out.println("更新定时任务失败" + e);
            throw new Exception("更新定时任务失败");
        }
    }


    public void jobresume(String jobClassName, String jobGroupName) throws Exception {
        schedulerFactoryBean.getScheduler().resumeJob(JobKey.jobKey(jobClassName, jobGroupName));
    }


    public void jobPause(String jobClassName, String jobGroupName) throws Exception {
        schedulerFactoryBean.getScheduler().pauseJob(JobKey.jobKey(jobClassName, jobGroupName));
    }

    public PageInfo<JobAndTrigger> getJobAndTriggerDetails(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<JobAndTrigger> list = jobAndTriggersDao.getJobAndTriggerDetailsAndSimple();
        for(JobAndTrigger jobAndTrigger : list){
            if(Preconditions.isNotBlank(jobAndTrigger.getREPEAT_INTERVAL())){
                BigDecimal fen = new BigDecimal(jobAndTrigger.getREPEAT_INTERVAL()).divide(new BigDecimal("60000"),2, BigDecimal.ROUND_HALF_EVEN);
                jobAndTrigger.setCRON_EXPRESSION(String.valueOf(fen) + "m");
            }
        }
        PageInfo<JobAndTrigger> page = new PageInfo<JobAndTrigger>(list);
        return page;
    }
}
