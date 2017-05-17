package code.warehouse.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 代码生成器持久化层.
 * package code.warehouse.generator.dao
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-12 11:12
 **/
@Mapper
public interface GeneratorMapper {

    List<Map<String, Object>> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    Map<String, String> queryTable(String tableName);

    List<Map<String, String>> queryColumns(String tableName);
}
