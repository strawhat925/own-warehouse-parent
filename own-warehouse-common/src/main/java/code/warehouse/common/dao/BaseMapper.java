package code.warehouse.common.dao;

import java.util.List;
import java.util.Map;

/**
 * 基础DAO.
 * package code.warehouse.boss.configuration.mybatis
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-10 17:47
 **/
public interface BaseMapper<T> {

    void save(T t);

    void save(Map<String, Object> map);

    void saveBatch(List<T> list);

    int update(T t);

    int update(Map<String, Object> map);

    int delete(Object id);

    int delete(Map<String, Object> map);

    int deleteBatch(Object[] id);

    T queryObject(Object id);

    List<T> queryList(Map<String, Object> map);

    List<T> queryList(Object id);

    int queryTotal(Map<String, Object> map);

    int queryTotal();
}
