package code.warehouse.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import code.warehouse.common.entity.SysFileEntity;
import code.warehouse.dao.SysUploadMapper;
import code.warehouse.service.SysUploadService;

/**
 * 文件上传服务实现.
 * package code.warehouse.service.impl
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-16 14:38
 **/
@Service
public class SysUploadServiceImpl implements SysUploadService {

    @Autowired
    private SysUploadMapper sysUploadMapper;

    @Override
    public SysFileEntity queryObject(Long id) {
        return sysUploadMapper.queryObject(id);
    }

    @Override
    public List<SysFileEntity> queryList(Map<String, Object> map) {
        return sysUploadMapper.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysUploadMapper.queryTotal(map);
    }

    @Override
    public void save(SysFileEntity sysFileEntity) {
        sysUploadMapper.save(sysFileEntity);
    }

    @Override
    public void update(SysFileEntity sysFileEntity) {
        sysUploadMapper.update(sysFileEntity);
    }

    @Override
    public void delete(Long id) {
        sysUploadMapper.delete(id);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        sysUploadMapper.deleteBatch(ids);
    }
}
