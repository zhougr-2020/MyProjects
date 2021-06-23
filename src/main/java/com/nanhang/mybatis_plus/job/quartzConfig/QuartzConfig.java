package com.nanhang.mybatis_plus.job.quartzConfig;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

@Configuration
public class QuartzConfig {

    //    @Bean
//    public JobDetail printTimeJobDetail(){
//        return JobBuilder.newJob(DateTimeJob.class)//PrintTimeJob我们的业务类
//                .withIdentity("DateTimeJob")//可以给该JobDetail起一个id
//                //每个JobDetail内都有一个Map，包含了关联到这个Job的数据，在Job类中可以通过context获取
//                .usingJobData("msg", "Hello Quartz")//关联键值对
//                .storeDurably()//即使没有Trigger关联时，也不需要删除该JobDetail
//                .build();
//    }
//
//    @Bean
//    public Trigger printTimeJobTrigger() {
//        //定时规则
//        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/5 * * * * ?");
//        return TriggerBuilder.newTrigger()
//                .forJob(printTimeJobDetail())//关联上述的JobDetail
//                .withIdentity("quartzTaskService")//给Trigger起个名字
//                .withSchedule(cronScheduleBuilder)
//                .build();
//    }

    //配置定时任务
    @Bean
    public MethodInvokingJobDetailFactoryBean firstJobDetail(DateTimeJob dateTimeJob) {
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        //是否并行
        jobDetail.setConcurrent(false);
        //任务类
        jobDetail.setTargetObject(dateTimeJob);
        // 需要执行的方法
        jobDetail.setTargetMethod("DateJob");

        return jobDetail;
    }


    // 配置触发器
    @Bean(name = "firstTrigger")
    public SimpleTriggerFactoryBean firstTrigger(JobDetail firstJobDetail) {
        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();

//        可以使用cron表达式
//        CronTriggerFactoryBean trigger1=new CronTriggerFactoryBean();
//        trigger1.setDescription("0/5 * * * * ?");

        trigger.setJobDetail(firstJobDetail);
        // 设置任务启动延迟
        trigger.setStartDelay(0);
        // 每5秒执行一次
        trigger.setRepeatInterval(5000);

        return trigger;
    }

    // 配置Scheduler
    @Bean(name = "scheduler")
    public SchedulerFactoryBean schedulerFactory(Trigger firstTrigger) {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        // 延时启动，应用启动1秒后
        bean.setStartupDelay(1);
        // 注册触发器
        bean.setTriggers(firstTrigger);
        return bean;
    }
}