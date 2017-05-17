package code.warehouse.service;

import java.util.List;
import java.util.Map;

import code.warehouse.common.entity.SysFileEntity;

/**
 * 文件上传接口.
 * package code.warehouse.service
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-16 14:37
 **/
public interface SysUploadService {

    SysFileEntity queryObject(Long id);

    List<SysFileEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(SysFileEntity sysFileEntity);

    void update(SysFileEntity sysFileEntity);

    void delete(Long id);

    void deleteBatch(Long[] ids);
}
