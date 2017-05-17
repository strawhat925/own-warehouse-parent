package code.warehouse.service;

import java.util.List;
import java.util.Map;

import code.warehouse.common.entity.SysLogEntity;

/**
 * 系统日志接口.
 * package code.warehouse.service
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-16 13:34
 **/
public interface SysLogService {


    SysLogEntity queryObject(Long id);

    List<SysLogEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(SysLogEntity sysLog);

    void update(SysLogEntity sysLog);

    void delete(Long id);

    void deleteBatch(Long[] ids);
}
