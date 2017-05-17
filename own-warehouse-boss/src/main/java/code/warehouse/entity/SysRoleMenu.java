package code.warehouse.entity;

import java.io.Serializable;

/**
 * 角色、菜单对应关系
 * package code.warehouse.entity
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-16 9:47
 **/
public class SysRoleMenu implements Serializable {
    private static final long serialVersionUID = 7613406480913920201L;


    private Long id;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 菜单ID
     */
    private Long menuId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
}
