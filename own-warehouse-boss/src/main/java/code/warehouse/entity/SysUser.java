package code.warehouse.entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import code.warehouse.common.validator.group.AddGroup;
import code.warehouse.common.validator.group.UpdateGroup;

/**
 * 用户.
 * package code.warehouse.boss.entity
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-10 11:57
 **/
public class SysUser implements Serializable {

    private static final long serialVersionUID = 5569848390013858406L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空", groups = { AddGroup.class, UpdateGroup.class })
    private String username;

    /**
     * 密码，不序列
     */
    @NotBlank(message = "密码不能为空", groups = { AddGroup.class })
    private transient String password;

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空", groups = { AddGroup.class, UpdateGroup.class })
    @Email(message = "邮箱格式不正确", groups = { AddGroup.class, UpdateGroup.class })
    private String email;

    /**
     * 手机号码
     */
    private String mobile;


    /**
     * 状态 0：禁用  1：正常
     */
    private Integer status;


    /**
     * 创建者id
     */
    private Long createUserId;


    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 角色id列表
     */
    private List<Long> roleIdList;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<Long> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<Long> roleIdList) {
        this.roleIdList = roleIdList;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", status=" + status +
                ", createUserId=" + createUserId +
                ", createTime=" + createTime +
                ", roleIdList=" + roleIdList +
                '}';
    }
}
