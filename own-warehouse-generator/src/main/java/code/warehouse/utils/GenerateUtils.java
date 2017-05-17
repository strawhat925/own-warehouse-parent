package code.warehouse.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import code.warehouse.common.exception.CommonException;
import code.warehouse.common.utils.DateUtils;
import code.warehouse.entity.ColumnEntity;
import code.warehouse.entity.TableEntity;

/**
 * 代码生成器工具类.
 * package code.warehouse.generator.utils
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-12 11:04
 **/
public class GenerateUtils {


    /**
     * 模板
     *
     * @return
     */
    public static List<String> getTemplateList() {
        List<String> templateList = Lists.newArrayList();
        templateList.add("template/Entity.java.vm");
        templateList.add("template/Dao.java.vm");
        templateList.add("template/Dao.xml.vm");
        templateList.add("template/Service.java.vm");
        templateList.add("template/ServiceImpl.java.vm");
        templateList.add("template/Controller.java.vm");
        templateList.add("template/list.html.vm");
        templateList.add("template/list.js.vm");
        templateList.add("template/menu.sql.vm");
        return templateList;
    }


    /**
     * 生成代码
     *
     * @param table
     * @param columns
     * @param zip
     */
    public static void generatorCode(Map<String, String> table, List<Map<String, String>> columns, ZipOutputStream zip) {
        Configuration configuration = getConfig();

        //表信息
        TableEntity tableEntity = new TableEntity();
        tableEntity.setTableName(table.get("tableName"));
        tableEntity.setComments(table.get("tableComment"));
        //表名转java类名
        String className = tableToJava(tableEntity.getTableName(), configuration.getString("tablePrefix"));
        tableEntity.setClassName(className);
        tableEntity.setClassname(StringUtils.uncapitalize(className));

        //列信息
        List<ColumnEntity> columnEntityList = Lists.newArrayList();
        for (Map<String, String> column : columns) {
            ColumnEntity columnEntity = new ColumnEntity();
            columnEntity.setColumnName(column.get("columnName"));
            columnEntity.setDataType(column.get("dataType"));
            columnEntity.setComments(column.get("columnComment"));
            columnEntity.setExtra(column.get("extra"));


            //列名转java属性名
            String attrName = columnToJava(columnEntity.getColumnName());
            columnEntity.setAttrName(attrName);
            columnEntity.setAttrname(StringUtils.uncapitalize(attrName));

            //列的数据类型转换java类型
            String attrType = configuration.getString(columnEntity.getDataType(), "unknowType");
            columnEntity.setAttrType(attrType);

            if (StringUtils.equalsIgnoreCase("PRI", column.get("columnKey")) && tableEntity.getPk() == null) {
                tableEntity.setPk(columnEntity);
            }
            columnEntityList.add(columnEntity);
        }

        tableEntity.setColumns(columnEntityList);

        //没主键，则第一个字段为主键
        if (tableEntity.getPk() == null) {
            tableEntity.setPk(tableEntity.getColumns().get(0));
        }

        //
        Properties properties = new Properties();
        properties.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(properties);

        //封装模板数据
        Map<String, Object> map = Maps.newHashMap();
        map.put("tableName", tableEntity.getTableName());
        map.put("comments", tableEntity.getComments());
        map.put("pk", tableEntity.getPk());
        map.put("className", tableEntity.getClassName());
        map.put("classname", tableEntity.getClassname());
        map.put("pathName", tableEntity.getClassname().toLowerCase());
        map.put("columns", tableEntity.getColumns());
        map.put("package", configuration.getString("package"));
        map.put("author", configuration.getString("author"));
        map.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_SECODE_PATTERN));
        VelocityContext context = new VelocityContext(map);

        //获取模板列表
        List<String> templates = getTemplateList();
        for (String template : templates) {
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);

            try {
                //添加到zip
                zip.putNextEntry(new ZipEntry(getFileName(template, tableEntity.getClassName(), configuration.getString("package"))));
                IOUtils.write(sw.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                throw new CommonException("渲染模板失败，表名：" + tableEntity.getTableName(), e);
            }
        }
    }


    /**
     * 表名转java类名
     *
     * @param tableName
     * @param tablePrefix
     *
     * @return
     */
    public static String tableToJava(String tableName, String tablePrefix) {
        if (StringUtils.isNotBlank(tablePrefix)) {
            tableName = tableName.replace(tablePrefix, "");
        }
        return columnToJava(tableName);
    }


    /**
     * 列名转换java属性名
     *
     * @param columnName
     *
     * @return
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{ '_' }).replace("_", "");
    }


    /**
     * 获取配置信息
     *
     * @return
     */
    public static Configuration getConfig() {
        try {
            return new PropertiesConfiguration("generator.properties");
        } catch (ConfigurationException e) {
            throw new CommonException("获取配置文件失败", e);
        }
    }


    /**
     * 获取文件名
     *
     * @param template
     * @param className
     * @param packageName
     *
     * @return
     */
    public static String getFileName(String template, String className, String packageName) {
        String packagePath = "src" + File.separator + "main" + File.separator + "java" + File.separator;
        if (org.apache.commons.lang.StringUtils.isNotBlank(packageName)) {
            packagePath += packageName.replace(".", File.separator) + File.separator;
        }

        if (template.contains("Entity.java.vm")) {
            return packagePath + "entity" + File.separator + className + "Entity.java";
        }

        if (template.contains("Dao.java.vm")) {
            return packagePath + "dao" + File.separator + className + "Dao.java";
        }

        if (template.contains("Dao.xml.vm")) {
            return packagePath + "dao" + File.separator + className + "Dao.xml";
        }

        if (template.contains("Service.java.vm")) {
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if (template.contains("ServiceImpl.java.vm")) {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if (template.contains("Controller.java.vm")) {
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }

        if (template.contains("list.html.vm")) {
            return "src" + File.separator + "main" + File.separator + "resources" + File.separator + "templates"
                    + File.separator + "generator" + File.separator + className.toLowerCase() + ".ftl";
        }

        if (template.contains("list.js.vm")) {
            return "src" + File.separator + "main" + File.separator + "static" + File.separator + "js" + File.separator + "generator" + File.separator + className.toLowerCase() + ".js";
        }

        if (template.contains("menu.sql.vm")) {
            return className.toLowerCase() + "_menu.sql";
        }

        return null;
    }
}
