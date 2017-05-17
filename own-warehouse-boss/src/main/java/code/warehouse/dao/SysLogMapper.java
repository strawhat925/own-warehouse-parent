package code.warehouse.dao;

import org.apache.ibatis.annotations.Mapper;

import code.warehouse.common.dao.BaseMapper;
import code.warehouse.common.entity.SysLogEntity;

/**
 * 系统日志持久化.
 * package code.warehouse.dao
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-16 13:36
 **/
@Mapper
public interface SysLogMapper extends BaseMapper<SysLogEntity> {


}
