package code.warehouse.service;

import java.util.List;

/**
 * 用户与角色对应关系接口.
 * package code.warehouse.service
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-15 15:46
 **/
public interface SysUserRoleService {

    void saveOrUpdate(Long userId, List<Long> roleIdList);

    /**
     * 根据用户ID，获取角色ID列表
     */
    List<Long> queryRoleIdList(Long userId);

    void delete(Long userId);

    void deleteBatch(Long[] userIds);


    void deleteUserRole(Long[] roleIds);
}
