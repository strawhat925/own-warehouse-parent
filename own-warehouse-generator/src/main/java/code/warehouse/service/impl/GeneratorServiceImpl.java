package code.warehouse.service.impl;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import code.warehouse.dao.GeneratorMapper;
import code.warehouse.service.GeneratorService;
import code.warehouse.utils.GenerateUtils;

/**
 * 代码生成器服务实现层.
 * package code.warehouse.generator.service.impl
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-12 11:10
 **/
@Service("GeneratorService")
public class GeneratorServiceImpl implements GeneratorService {


    @Autowired
    private GeneratorMapper generatorMapper;

    @Override
    public List<Map<String, Object>> queryList(Map<String, Object> map) {
        return generatorMapper.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return generatorMapper.queryTotal(map);
    }

    @Override
    public Map<String, String> queryTable(String tableName) {
        return generatorMapper.queryTable(tableName);
    }

    @Override
    public List<Map<String, String>> queryColumns(String tableName) {
        return generatorMapper.queryColumns(tableName);
    }

    @Override
    public byte[] generatorCode(String[] tableNames) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        for (String tableName : tableNames) {
            //查询表信息
            Map<String, String> table = queryTable(tableName);
            //查询列信息
            List<Map<String, String>> columns = queryColumns(tableName);
            //生成代码
            GenerateUtils.generatorCode(table, columns, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }
}
