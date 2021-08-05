package com.nanhang.mybatis_plus.job.quartzConfig;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 定时任务
 */
@Component
@Slf4j
public class DateTimeJob  {

//    @Override
//    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        //获取JobDetail中关联的数据
//        String msg = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("msg");
//        System.out.println("current Time :" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "---" + msg);
//    }

    public void DateJob(){
        log.info("current Time :" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "---" );
    }
}