package code.warehouse.service.impl;

import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import code.warehouse.dao.SysMenuMapper;
import code.warehouse.entity.SysMenu;
import code.warehouse.service.SysMenuService;
import code.warehouse.service.SysRoleMenuService;
import code.warehouse.service.SysUserService;
import code.warehouse.common.utils.Constants;

/**
 * 系统菜单服务实现.
 * package code.warehouse.boss.service.impl
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-10 16:03
 **/
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;


    @Override
    public List<SysMenu> queryListParentId(Long parentId, List<Long> menuIdList) {
        List<SysMenu> sysMenuList = sysMenuMapper.queryListParentId(parentId);
        if (menuIdList == null) {
            return sysMenuList;
        }
        List<SysMenu> userMenuList = Lists.newArrayList();
        for (SysMenu sysMenu : sysMenuList) {
            if (menuIdList.contains(sysMenu.getMenuId())) {
                userMenuList.add(sysMenu);
            }
        }
        return userMenuList;
    }

    @Override
    public List<SysMenu> queryNotButtonList() {
        return sysMenuMapper.queryNotButtonList();
    }

    @Override
    public List<SysMenu> getUserMenuList(Long userId) {
        //系统管理员
        if (userId == Constants.SUPER_ADMIN) {
            return getAllMenuList(null);
        }
        //用户菜单列表
        List<Long> menuIdList = sysUserService.queryAllMenuId(userId);
        return getAllMenuList(menuIdList);
    }

    /**
     * 获取所有菜单列表
     */
    private List<SysMenu> getAllMenuList(List<Long> menuIdList) {
        //查询根菜单列表
        List<SysMenu> menuList = queryListParentId(0L, menuIdList);
        //递归获取子菜单
        getMenuTreeList(menuList, menuIdList);

        return menuList;
    }

    /**
     * 递归
     */
    private List<SysMenu> getMenuTreeList(List<SysMenu> menuList, List<Long> menuIdList) {
        List<SysMenu> subMenuList = Lists.newArrayList();

        for (SysMenu sysMenu : menuList) {
            if (sysMenu.getType() == Constants.MenuType.CATALOG.getValue()) {//目录
                sysMenu.setList(getMenuTreeList(queryListParentId(sysMenu.getMenuId(), menuIdList), menuIdList));
            }
            subMenuList.add(sysMenu);
        }

        return subMenuList;
    }


    @Override
    public SysMenu queryObject(Long menuId) {
        return sysMenuMapper.queryObject(menuId);
    }

    @Override
    public List<SysMenu> queryList(Map<String, Object> map) {
        return sysMenuMapper.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysMenuMapper.queryTotal(map);
    }

    @Override
    public void save(SysMenu menu) {
        sysMenuMapper.save(menu);
    }

    @Override
    public void update(SysMenu menu) {
        sysMenuMapper.update(menu);
    }

    @Override
    @Transactional
    public void deleteBatch(Long[] menuIds) {
        //删除角色菜单对应关系
        sysRoleMenuService.deleteBatch(menuIds);
        //删除菜单
        sysMenuMapper.deleteBatch(menuIds);
    }

    @Override
    public List<SysMenu> queryUserList(Long userId) {
        return sysMenuMapper.queryUserList(userId);
    }
}
