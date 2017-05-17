package code.warehouse.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

import code.warehouse.common.dao.BaseMapper;
import code.warehouse.entity.ScheduleJobEntity;

/**
 * 定时任务持久化.
 * package code.warehouse.dao
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-17 10:54
 **/
@Mapper
public interface ScheduleJobMapper extends BaseMapper<ScheduleJobEntity>{

    /**
     * 批量更新状态
     *
     * @param map
     *
     * @return
     */
    int updateBatch(Map<String, Object> map);
}
