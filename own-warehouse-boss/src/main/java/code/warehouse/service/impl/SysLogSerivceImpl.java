package code.warehouse.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import code.warehouse.common.entity.SysLogEntity;
import code.warehouse.dao.SysLogMapper;
import code.warehouse.service.SysLogService;

/**
 * 系统日志服务实现.
 * package code.warehouse.service.impl
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-16 13:34
 **/
@Service
public class SysLogSerivceImpl implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public SysLogEntity queryObject(Long id) {
        return sysLogMapper.queryObject(id);
    }

    @Override
    public List<SysLogEntity> queryList(Map<String, Object> map) {
        return sysLogMapper.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysLogMapper.queryTotal(map);
    }

    @Override
    public void save(SysLogEntity sysLog) {
        sysLogMapper.save(sysLog);
    }

    @Override
    public void update(SysLogEntity sysLog) {
        sysLogMapper.update(sysLog);
    }

    @Override
    public void delete(Long id) {
        sysLogMapper.delete(id);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        sysLogMapper.deleteBatch(ids);
    }
}
