package code.warehouse.service;

import java.util.List;
import java.util.Map;

import code.warehouse.entity.ScheduleJobLogEntity;

/**
 * 定时任务日志接口.
 * package code.warehouse.service
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-17 11:19
 **/
public interface ScheduleJobLogService {


    /**
     * 根据ID，查询定时任务日志
     *
     * @param jobId
     *
     * @return
     */
    ScheduleJobLogEntity queryObject(Long jobId);

    /**
     * 查询定时任务日志列表
     *
     * @param map
     *
     * @return
     */
    List<ScheduleJobLogEntity> queryList(Map<String, Object> map);


    /**
     * 保存定时任务日志
     *
     * @param scheduleJobLog
     */
    void save(ScheduleJobLogEntity scheduleJobLog);
}
