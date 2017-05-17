package code.warehouse.configuration.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Properties;

import javax.sql.DataSource;

/**
 * 定时任务config
 * package code.warehouse.configuration
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-17 10:36
 **/
@Configuration
public class ScheduleConfiguration {

    @Autowired
    private DataSource dataSource;

    @Bean(name = "scheduler")
    public SchedulerFactoryBean scheduler() {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        factoryBean.setDataSource(dataSource);

        Properties properties = new Properties();
        properties.setProperty("org.quartz.scheduler.instanceName", "WarehouseSchedule");
        properties.setProperty("org.quartz.scheduler.instanceId", "AUTO");
        //线程池配置
        properties.setProperty("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        properties.setProperty("org.quartz.threadPool.threadCount", "20");
        properties.setProperty("org.quartz.threadPool.threadPriority", "5");

        //JobStore 配置
        properties.setProperty("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        //集群配置
        properties.setProperty("org.quartz.jobStore.isClustered", "true");
        properties.setProperty("org.quartz.jobStore.clusterCheckinInterval", "15000");
        properties.setProperty("org.quartz.jobStore.maxMisfiresToHandleAtATime", "1");

        properties.setProperty("org.quartz.jobStore.misfireThreshold", "12000");
        //表前缀
        properties.setProperty("org.quartz.jobStore.tablePrefix", "QRTZ_");

        factoryBean.setQuartzProperties(properties);

        factoryBean.setSchedulerName("WarehouseSchedule");
        //延时启动
        factoryBean.setStartupDelay(30);
        factoryBean.setApplicationContextSchedulerContextKey("applicationContext");
        //可选，QuartzScheduler 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
        factoryBean.setOverwriteExistingJobs(true);
        //设置自动启动，默认为true
        factoryBean.setAutoStartup(true);

        return factoryBean;
    }


}
