package code.warehouse.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

import code.warehouse.common.dao.BaseMapper;
import code.warehouse.entity.SysMenu;

/**
 * 菜单持久化.
 * package code.warehouse.boss.dao
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-10 17:55
 **/
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 根据父菜单，查询子菜单
     *
     * @param parentId
     *         父菜单ID
     */
    List<SysMenu> queryListParentId(Long parentId);

    /**
     * 获取不包含按钮的菜单列表
     */
    List<SysMenu> queryNotButtonList();

    /**
     * 查询用户的权限列表
     */
    List<SysMenu> queryUserList(Long userId);
}
