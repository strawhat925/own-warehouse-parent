package code.warehouse.utils;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import code.warehouse.common.exception.CommonException;
import code.warehouse.common.utils.Constants;
import code.warehouse.entity.ScheduleJobEntity;

/**
 * 定时任务工具类.
 * package code.warehouse.utils
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-17 11:23
 **/
public class ScheduleUtils {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleUtils.class);
    private static final String JOB_NAME = "TASK_";


    /**
     * 获取触发器key
     *
     * @param jobId
     *
     * @return
     */
    public static TriggerKey getTriggerKey(Long jobId) {
        return TriggerKey.triggerKey(JOB_NAME + jobId);
    }


    /**
     * 获取jobkey
     *
     * @param jobId
     *
     * @return
     */
    public static JobKey getJobKey(Long jobId) {
        return JobKey.jobKey(JOB_NAME + jobId);
    }


    /**
     * 获取定时任务表达式触发器
     *
     * @param scheduler
     * @param jobId
     *
     * @return
     */
    public static CronTrigger getCronTrigger(Scheduler scheduler, Long jobId) {
        try {
            return (CronTrigger) scheduler.getTrigger(getTriggerKey(jobId));
        } catch (SchedulerException e) {
            throw new CommonException("获取定时任务表达式触发器异常", e);
        }
    }


    /**
     * 创建定时任务
     *
     * @param scheduler
     * @param scheduleJob
     */
    public static void createScheduleJob(Scheduler scheduler, ScheduleJobEntity scheduleJob) {
        try {

            logger.debug("正在创建定时任务，任务ID:{}", scheduleJob.getJobId());
            //构建job信息
            JobDetail jobDetail = JobBuilder.newJob(ScheduleJob.class).withIdentity(getJobKey(scheduleJob.getJobId())).build();

            //表达式调度构建器
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression()).withMisfireHandlingInstructionDoNothing();

            //按新的表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(scheduleJob.getJobId())).withSchedule(cronScheduleBuilder).build();

            //放入参数，运行时的方法可以获取到
            jobDetail.getJobDataMap().put(Constants.JOB_PARAM_KEY, scheduleJob);
            scheduler.scheduleJob(jobDetail, trigger);

            //暂停任务
            if (scheduleJob.getStatus() == Constants.ScheduleStatus.PAUSE.getValue()) {
                pauseJob(scheduler, scheduleJob.getJobId());
            }

        } catch (SchedulerException e) {
            throw new CommonException("创建定时任务失败", e);
        }
    }


    /**
     * 更新定时任务
     *
     * @param scheduler
     * @param scheduleJob
     */
    public static void updateScheduleJob(Scheduler scheduler, ScheduleJobEntity scheduleJob) {
        try {
            logger.debug("正在更新定时任务，任务ID:{}", scheduleJob.getJobId());
            TriggerKey triggerKey = getTriggerKey(scheduleJob.getJobId());

            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression()).withMisfireHandlingInstructionDoNothing();

            CronTrigger trigger = getCronTrigger(scheduler, scheduleJob.getJobId());

            //按新的表达式构建新的tigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();

            //参数
//            JobDetail jobDetail = scheduler.getJobDetail(getJobKey(scheduleJob.getJobId()));
//            jobDetail.getJobDataMap().put(ScheduleJobEntity.JOB_PARAM_KEY, scheduleJob);

            trigger.getJobDataMap().put(Constants.JOB_PARAM_KEY, scheduleJob);

            scheduler.rescheduleJob(triggerKey, trigger);

            //暂停任务
            if (scheduleJob.getStatus() == Constants.ScheduleStatus.PAUSE.getValue()) {
                pauseJob(scheduler, scheduleJob.getJobId());
            }
        } catch (SchedulerException e) {
            throw new CommonException("更新定时任务失败", e);
        }
    }


    /**
     * 立即执行定时任务
     *
     * @param scheduler
     * @param scheduleJob
     */
    public static void run(Scheduler scheduler, ScheduleJobEntity scheduleJob) {
        try {
            //参数
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put(Constants.JOB_PARAM_KEY, scheduleJob);

            scheduler.triggerJob(getJobKey(scheduleJob.getJobId()), jobDataMap);
        } catch (SchedulerException e) {
            throw new CommonException("立即执行定时任务失败", e);
        }
    }


    /**
     * 暂停任务
     *
     * @param scheduler
     * @param jobId
     */
    public static void pauseJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.pauseJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new CommonException("暂停定时任务失败", e);
        }
    }

    /**
     * 恢复任务
     *
     * @param scheduler
     * @param jobId
     */
    public static void resumeJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.resumeJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new CommonException("暂停定时任务失败", e);
        }
    }

    /**
     * 删除定时任务
     *
     * @param scheduler
     * @param jobId
     */
    public static void deleteScheduleJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.deleteJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            throw new CommonException("删除定时任务失败", e);
        }
    }
}
