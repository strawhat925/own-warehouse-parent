package code.warehouse.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import code.warehouse.dao.ScheduleJobLogMapper;
import code.warehouse.entity.ScheduleJobLogEntity;
import code.warehouse.service.ScheduleJobLogService;

/**
 * 定时任务日志服务实现.
 * package code.warehouse.service.impl
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-17 11:19
 **/
@Service
public class ScheduleJobLogServiceImpl implements ScheduleJobLogService {

    @Autowired
    private ScheduleJobLogMapper scheduleJobLogMapper;

    @Override
    public ScheduleJobLogEntity queryObject(Long jobId) {
        return scheduleJobLogMapper.queryObject(jobId);
    }

    @Override
    public List<ScheduleJobLogEntity> queryList(Map<String, Object> map) {
        return scheduleJobLogMapper.queryList(map);
    }

    @Override
    public void save(ScheduleJobLogEntity scheduleJobLog) {
        scheduleJobLogMapper.save(scheduleJobLog);
    }
}
