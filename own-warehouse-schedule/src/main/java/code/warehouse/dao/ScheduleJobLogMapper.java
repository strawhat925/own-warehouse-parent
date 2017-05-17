package code.warehouse.dao;

import org.apache.ibatis.annotations.Mapper;

import code.warehouse.common.dao.BaseMapper;
import code.warehouse.entity.ScheduleJobLogEntity;

/**
 * 定时任务日志持久化.
 * package code.warehouse.dao
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-17 11:21
 **/
@Mapper
public interface ScheduleJobLogMapper extends BaseMapper<ScheduleJobLogEntity> {
}
