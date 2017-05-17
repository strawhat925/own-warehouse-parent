package code.warehouse.entity;

/**
 * 列属性.
 * package code.warehouse.generator.entity
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-12 11:41
 **/
public class ColumnEntity {

    /**
     * 列名
     */
    private String columnName;
    /**
     * 类型
     */
    private String dataType;
    /**
     * 注释
     */
    private String comments;
    /**
     * 属性名称(第一个字母大写)，如：user_name => UserName
     */
    private String attrName;
    /**
     * 属性名称(第一个字母小写)，如：user_name => userName
     */
    private String attrname;
    /**
     * 属性类型
     */
    private String attrType;
    /**
     * 扩展
     */
    private String extra;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrname() {
        return attrname;
    }

    public void setAttrname(String attrname) {
        this.attrname = attrname;
    }

    public String getAttrType() {
        return attrType;
    }

    public void setAttrType(String attrType) {
        this.attrType = attrType;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
}
