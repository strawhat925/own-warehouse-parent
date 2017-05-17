package code.warehouse.service;

import java.util.List;
import java.util.Map;

import code.warehouse.entity.ScheduleJobEntity;

/**
 * 定时任务接口.
 * package code.warehouse.service
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-17 10:50
 **/
public interface ScheduleJobService {

    /**
     * 根据ID，查询定时任务
     *
     * @param jobId
     *
     * @return
     */
    ScheduleJobEntity queryObject(Long jobId);

    /**
     * 查询定时任务列表
     *
     * @param map
     *
     * @return
     */
    List<ScheduleJobEntity> queryList(Map<String, Object> map);


    /**
     * 保存定时任务
     *
     * @param scheduleJob
     */
    void save(ScheduleJobEntity scheduleJob);

    /**
     * 更新定时任务
     *
     * @param scheduleJob
     */
    void update(ScheduleJobEntity scheduleJob);

    /**
     * 批量删除定时任务
     *
     * @param jobIds
     */
    void deleteBatch(Long[] jobIds);

    /**
     * 批量更新定时任务状态
     *
     * @param jobIds
     * @param status
     *
     * @return
     */
    int updateBatch(Long[] jobIds, int status);

    /**
     * 立即执行
     *
     * @param jobIds
     */
    void run(Long[] jobIds);

    /**
     * 暂停运行
     *
     * @param jobIds
     */
    void pause(Long[] jobIds);

    /**
     * 恢复运行
     *
     * @param jobIds
     */
    void resume(Long[] jobIds);
}
