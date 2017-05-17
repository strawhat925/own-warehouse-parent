package code.warehouse.dao;

import java.util.List;

import code.warehouse.common.dao.BaseMapper;
import code.warehouse.entity.SysUserRole;

/**
 * 用户与角色对应关系持久化.
 * package code.warehouse.dao
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-15 15:48
 **/
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 根据用户ID，获取角色ID列表
     *
     * @param userId
     *
     * @return
     */
    List<Long> queryRoleIdList(Long userId);

    /**
     * 根据角色ID，删除角色
     *
     * @param id
     *
     * @return
     */
    int deleteUserRole(Object[] id);
}
