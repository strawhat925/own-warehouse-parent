package code.warehouse.service;

import java.util.List;

/**
 * 角色、菜单对应关系接口.
 * package code.warehouse.service
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-16 9:46
 **/
public interface SysRoleMenuService {

    /**
     * @param roleId
     * @param menuIdList
     */
    void saveOrUpdate(Long roleId, List<Long> menuIdList);

    /**
     * 根据角色ID，获取菜单ID列表
     *
     * @param roleId
     *
     * @return
     */
    List<Long> queryMenuIdList(Long roleId);


    void deleteBatch(Long[] menuIds);


    void deleteRoleMenu(Long[] roleIds);
}
