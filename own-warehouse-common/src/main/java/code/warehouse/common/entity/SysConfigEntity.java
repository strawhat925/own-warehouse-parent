package code.warehouse.common.entity;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 系统配置信息.
 * package code.warehouse.common.entity
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-12 14:01
 **/
public class SysConfigEntity {

    private Long id;
    @NotBlank(message = "参数名不能为空")
    private String key;
    @NotBlank(message = "参数值不能为空")
    private String value;
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
