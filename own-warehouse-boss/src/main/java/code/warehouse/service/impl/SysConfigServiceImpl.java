package code.warehouse.service.impl;

import com.alibaba.fastjson.JSON;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import code.warehouse.common.entity.SysConfigEntity;
import code.warehouse.common.exception.CommonException;
import code.warehouse.dao.SysConfigMapper;
import code.warehouse.service.SysConfigService;

/**
 * 系统配置服务实现.
 * package code.warehouse.service.impl
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-16 14:07
 **/
@Service
public class SysConfigServiceImpl implements SysConfigService {

    @Autowired
    private SysConfigMapper sysConfigMapper;

    @Override
    public void save(SysConfigEntity config) {
        sysConfigMapper.save(config);
    }

    @Override
    public void update(SysConfigEntity config) {
        sysConfigMapper.update(config);
    }

    @Override
    public void updateValueByKey(String key, String value) {
        sysConfigMapper.updateValueByKey(key, value);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        sysConfigMapper.deleteBatch(ids);
    }

    @Override
    public List<SysConfigEntity> queryList(Map<String, Object> map) {
        return sysConfigMapper.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysConfigMapper.queryTotal(map);
    }

    @Override
    public SysConfigEntity queryObject(Long id) {
        return sysConfigMapper.queryObject(id);
    }

    @Override
    public String getValue(String key, String defaultValue) {
        String value = sysConfigMapper.queryByKey(key);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return value;
    }

    @Override
    public <T> T getConfigObject(String key, Class<T> clazz) {
        String value = getValue(key, null);
        if (StringUtils.isNotBlank(value)) {
            return JSON.parseObject(value, clazz);
        }
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new CommonException("获取参数失败");
        }
    }
}
