package com.vpis.schedule.controller;

import com.github.pagehelper.PageInfo;
import com.vpis.schedule.entity.JobAndTrigger;
import com.vpis.schedule.service.QuartzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 *  @Author: Yuhan.Tang
 *  @ClassName: QuartzController
 *  @package: com.vpis.schedule.controller
 *  @Date: Created in 2018/11/13 下午5:24
 *  @email yuhan.tang@magicwindow.cn
 *  @Description: 
 */
@Slf4j
@RestController
@RequestMapping(value="/job")
public class QuartzController {

    @Autowired
    private QuartzService quartzService;


    @PostMapping(value = "/addjob")
    public void addjob(@RequestParam(value = "jobClassName") String jobClassName,
                       @RequestParam(value = "jobGroupName") String jobGroupName,
                       @RequestParam(value = "cronExpression") String cronExpression) throws Exception {
        log.info("addjob jobClassName:{},jobGroupName:{},cronExpression{}",jobClassName,jobGroupName,cronExpression);
        quartzService.addJob(jobClassName, jobGroupName, cronExpression);
    }



    @PostMapping(value = "/simple/addjob")
    public void addSimplejob(@RequestParam(value = "jobName") String jobName,
                             @RequestParam(value = "jobClassName") String jobClassName,
                             @RequestParam(value = "jobGroupName") String jobGroupName,
                             @RequestParam(value = "jobTime") Integer jobTime,
                             @RequestParam(value = "jobTimes") Integer jobTimes) throws Exception {
        log.info("simple/addjob jobName:{},jobClassName:{},jobGroupName:{},jobTime:{},jobTimes:{}",jobName,jobClassName,jobGroupName,jobTime,jobTimes);
        quartzService.addJobSimple(quartzService.getClass(jobClassName).getClass(), jobName,jobGroupName,jobTime,jobTimes);
    }


    @PostMapping(value = "/pausejob")
    public void pausejob(@RequestParam(value = "jobClassName") String jobClassName,
                         @RequestParam(value = "jobGroupName") String jobGroupName) throws Exception {
        log.info("暂停Job pausejob jobClassName:{},jobGroupName:{}",jobClassName,jobGroupName);
        quartzService.jobPause(jobClassName, jobGroupName);
    }


    @PostMapping(value = "/resumejob")
    public void resumejob(@RequestParam(value = "jobClassName") String jobClassName,
                          @RequestParam(value = "jobGroupName") String jobGroupName) throws Exception {
        log.info("恢复Job resumejob jobClassName:{},jobGroupName:{}",jobClassName,jobGroupName);
        quartzService.jobresume(jobClassName, jobGroupName);
    }


    @PostMapping(value = "/reschedulejob")
    public void rescheduleJob(@RequestParam(value = "jobClassName") String jobClassName,
                              @RequestParam(value = "jobGroupName") String jobGroupName,
                              @RequestParam(value = "cronExpression") String cronExpression) throws Exception {
        log.info("修改Job reschedulejob jobClassName:{},jobGroupName:{}，cronExpression:{}",jobClassName,jobGroupName,cronExpression);
        quartzService.jobreschedule(jobClassName, jobGroupName, cronExpression);
    }

    @PostMapping(value = "/simple/reschedulejob")
    public void rescheduleJobSimple(@RequestParam(value = "jobName") String jobName,
                                      @RequestParam(value = "jobGroupName") String jobGroupName,
                                      @RequestParam(value = "jobTime") int jobTime,
                                      @RequestParam(value = "jobTimes") int jobTimes) throws Exception {
        log.info("修改simpleJob simple/reschedulejob jobName:{},jobGroupName:{},jobTime:{},jobTimes:{}",jobName,jobGroupName,jobTime,jobTimes);
        quartzService.jobrescheduleSimple(jobName, jobGroupName, jobTime, jobTimes);
    }


    @PostMapping(value = "/deletejob")
    public void deletejob(@RequestParam(value = "jobClassName") String jobClassName,
                          @RequestParam(value = "jobGroupName") String jobGroupName) throws Exception {
        log.info("删除Job deletejob jobClassName:{},jobGroupName:{}",jobClassName,jobGroupName);
        quartzService.jobdelete(jobClassName, jobGroupName);
    }


    @GetMapping(value = "/queryjob")
    public Map<String, Object> queryjob(@RequestParam(value = "pageNum") Integer pageNum, @RequestParam(value = "pageSize") Integer pageSize) {
        PageInfo<JobAndTrigger> jobAndTrigger = quartzService.getJobAndTriggerDetails(pageNum, pageSize);
        Map<String, Object> map = new HashMap<>();
        map.put("JobAndTrigger", jobAndTrigger);
        map.put("number", jobAndTrigger.getTotal());
        return map;
    }
}
