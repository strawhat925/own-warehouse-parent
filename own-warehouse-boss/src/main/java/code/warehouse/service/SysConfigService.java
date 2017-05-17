package code.warehouse.service;

import java.util.List;
import java.util.Map;

import code.warehouse.common.entity.SysConfigEntity;

/**
 * 系统配置接口.
 * package code.warehouse.service
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-16 14:07
 **/
public interface SysConfigService {

    void save(SysConfigEntity config);

    void update(SysConfigEntity config);

    void updateValueByKey(String key, String value);

    void deleteBatch(Long[] ids);

    List<SysConfigEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    SysConfigEntity queryObject(Long id);

    String getValue(String key, String defaultValue);

    <T> T getConfigObject(String key, Class<T> clazz);
}
