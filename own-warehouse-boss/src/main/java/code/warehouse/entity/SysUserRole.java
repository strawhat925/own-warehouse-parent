package code.warehouse.entity;

import java.io.Serializable;

/**
 * 用户角色.
 * package code.warehouse.entity
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-15 15:50
 **/
public class SysUserRole implements Serializable {
    private static final long serialVersionUID = -2996148341881605507L;

    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
