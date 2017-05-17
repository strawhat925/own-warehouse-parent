package code.warehouse.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

import code.warehouse.common.dao.BaseMapper;
import code.warehouse.entity.SysRoleMenu;

/**
 * 角色、菜单对应关系持久化.
 * package code.warehouse.dao
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-16 9:46
 **/
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    /**
     * 根据角色ID，获取菜单ID列表
     *
     * @param roleId
     *
     * @return
     */
    List<Long> queryMenuIdList(Long roleId);


    int deleteRoleMenu(Object[] id);
}
