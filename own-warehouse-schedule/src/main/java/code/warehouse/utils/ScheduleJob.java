package code.warehouse.utils;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import code.warehouse.common.utils.Constants;
import code.warehouse.common.utils.SpringContextUtils;
import code.warehouse.entity.ScheduleJobEntity;
import code.warehouse.entity.ScheduleJobLogEntity;
import code.warehouse.service.ScheduleJobLogService;

/**
 * 定时任务.
 * package code.warehouse.utils
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-17 11:00
 **/
public class ScheduleJob extends QuartzJobBean {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleJob.class);
    //单一线程池
    private ExecutorService executorService = Executors.newSingleThreadExecutor();


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        ScheduleJobEntity scheduleJob = (ScheduleJobEntity) jobExecutionContext.getMergedJobDataMap().get(Constants.JOB_PARAM_KEY);
        logger.info("---------------------------------executeInternal--------------------------------------------");
        //
        ScheduleJobLogEntity scheduleJobLog = new ScheduleJobLogEntity();
        scheduleJobLog.setJobId(scheduleJob.getJobId());
        scheduleJobLog.setBeanName(scheduleJob.getBeanName());
        scheduleJobLog.setMethodName(scheduleJob.getMethodName());
        scheduleJobLog.setParams(scheduleJob.getParams());
        scheduleJobLog.setCreateTime(new Date());

        //开始执行任务
        long startTime = System.currentTimeMillis();
        ScheduleJobLogService scheduleJobLogService = (ScheduleJobLogService) SpringContextUtils.getBean("scheduleJobLogService");

        try {
            logger.debug("任务准备执行，任务ID：" + scheduleJob.getJobId());
            ScheduleRunnable task = new ScheduleRunnable(scheduleJob);

            Future<?> future = executorService.submit(task);

            future.get();

            //任务执行时长
            long times = System.currentTimeMillis() - startTime;
            scheduleJobLog.setTimes((int) times);

            //任务状态 0:成功 1:失败
            scheduleJobLog.setStatus(0);

            logger.debug("任务执行完毕，任务ID：" + scheduleJob.getJobId() + "  总共耗时：" + times + "毫秒");
        } catch (Exception e) {
            logger.error("任务执行失败，任务ID：" + scheduleJob.getJobId(), e);

            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            scheduleJobLog.setTimes((int) times);

            //任务状态    0：成功    1：失败
            scheduleJobLog.setStatus(1);
            scheduleJobLog.setError(StringUtils.substring(e.toString(), 0, 2000));
        } finally {
            //保存执行日志
            scheduleJobLogService.save(scheduleJobLog);
        }

    }
}
