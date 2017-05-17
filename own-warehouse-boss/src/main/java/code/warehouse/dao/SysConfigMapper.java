package code.warehouse.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import code.warehouse.common.dao.BaseMapper;
import code.warehouse.common.entity.SysConfigEntity;

/**
 * 系统配置持久化.
 * package code.warehouse.dao
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-16 14:08
 **/
@Mapper
public interface SysConfigMapper extends BaseMapper<SysConfigEntity> {

    /**
     * @param paramKey
     *
     * @return
     */
    String queryByKey(String paramKey);

    /**
     * @param key
     * @param value
     *
     * @return
     */
    int updateValueByKey(@Param("key") String key, @Param("value") String value);
}
