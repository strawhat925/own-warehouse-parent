package code.warehouse.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单.
 * package code.warehouse.boss.entity
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-10 13:38
 **/
public class SysMenu implements Serializable {
    private static final long serialVersionUID = 7938657779534534295L;

    /**
     * 菜单ID
     */
    private Long menuId;

    /**
     * 父菜单ID，一级菜单为0
     */
    private Long parentId;

    /**
     * 父菜单名称
     */
    private String parentName;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单URL
     */
    private String url;

    /**
     * 授权(多个用逗号分隔，如：user:list,user:create)
     */
    private String perms;

    /**
     * 类型     0：目录   1：菜单   2：按钮
     */
    private Integer type;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * ztree属性
     */
    private Boolean open;

    private List<?> list;


    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }


    @Override
    public String toString() {
        return "SysMenu{" +
                "menuId=" + menuId +
                ", parentId=" + parentId +
                ", parentName='" + parentName + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", perms='" + perms + '\'' +
                ", type=" + type +
                ", icon='" + icon + '\'' +
                ", orderNum=" + orderNum +
                ", open=" + open +
                ", list=" + list +
                '}';
    }
}
