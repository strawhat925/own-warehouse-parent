package code.warehouse.service;

import java.util.List;
import java.util.Map;

/**
 * 代码生成器服务层.
 * package code.warehouse.generator.service
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-12 11:10
 **/
public interface GeneratorService {

    List<Map<String, Object>> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    Map<String, String> queryTable(String tableName);

    List<Map<String, String>> queryColumns(String tableName);

    /**
     * 生成代码
     */
    byte[] generatorCode(String[] tableNames);
}
