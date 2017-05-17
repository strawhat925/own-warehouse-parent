package code.warehouse.service.impl;

import com.google.common.collect.Maps;

import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import code.warehouse.common.utils.Constants;
import code.warehouse.dao.ScheduleJobMapper;
import code.warehouse.entity.ScheduleJobEntity;
import code.warehouse.service.ScheduleJobService;
import code.warehouse.utils.ScheduleUtils;

/**
 * 定时任务服务实现.
 * package code.warehouse.service.impl
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-17 10:50
 **/
@Service
public class ScheduleJobServiceImpl implements ScheduleJobService {

    @Autowired
    private Scheduler scheduler;
    @Autowired
    private ScheduleJobMapper scheduleJobMapper;

    /**
     * 项目启动时初始化
     */
    @PostConstruct
    public void init() {
        List<ScheduleJobEntity> scheduleJobEntityList = scheduleJobMapper.queryList(new HashMap<String, Object>());
        for (ScheduleJobEntity scheduleJob : scheduleJobEntityList) {
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
            } else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
            }
        }
    }


    @Override
    public ScheduleJobEntity queryObject(Long jobId) {
        return scheduleJobMapper.queryObject(jobId);
    }

    @Override
    public List<ScheduleJobEntity> queryList(Map<String, Object> map) {
        return scheduleJobMapper.queryList(map);
    }

    @Override
    public void save(ScheduleJobEntity scheduleJob) {
        scheduleJob.setCreateTime(new Date());
        scheduleJob.setStatus(Constants.ScheduleStatus.NORMAL.getValue());
        scheduleJobMapper.save(scheduleJob);

        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
    }

    @Override
    public void update(ScheduleJobEntity scheduleJob) {
        scheduleJobMapper.update(scheduleJob);

        ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
    }

    @Override
    public void deleteBatch(Long[] jobIds) {
        for (Long jobId : jobIds) {
            ScheduleUtils.deleteScheduleJob(scheduler, jobId);
        }

        scheduleJobMapper.deleteBatch(jobIds);
    }

    @Override
    public int updateBatch(Long[] jobIds, int status) {

        Map<String, Object> params = Maps.newHashMap();
        params.put("list", jobIds);
        params.put("status", status);
        return scheduleJobMapper.updateBatch(params);
    }

    @Override
    public void run(Long[] jobIds) {
        for (Long jobId : jobIds) {
            ScheduleUtils.run(scheduler, queryObject(jobId));
        }
    }

    @Override
    public void pause(Long[] jobIds) {
        for (Long jobId : jobIds) {
            ScheduleUtils.pauseJob(scheduler, jobId);
        }

        updateBatch(jobIds, Constants.ScheduleStatus.PAUSE.getValue());
    }

    @Override
    public void resume(Long[] jobIds) {
        for (Long jobId : jobIds) {
            ScheduleUtils.resumeJob(scheduler, jobId);
        }

        updateBatch(jobIds, Constants.ScheduleStatus.NORMAL.getValue());
    }
}
