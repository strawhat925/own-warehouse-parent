package code.warehouse.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

import code.warehouse.common.exception.CommonException;
import code.warehouse.dao.SysRoleMapper;
import code.warehouse.entity.SysRole;
import code.warehouse.service.SysRoleMenuService;
import code.warehouse.service.SysRoleService;
import code.warehouse.service.SysUserRoleService;
import code.warehouse.service.SysUserService;
import code.warehouse.common.utils.Constants;

/**
 * 系统角色服务实现.
 * package code.warehouse.service.impl
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-15 14:02
 **/
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    public SysRole queryObject(Long roleId) {
        return sysRoleMapper.queryObject(roleId);
    }

    @Override
    public List<SysRole> queryList(Map<String, Object> map) {
        return sysRoleMapper.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysRoleMapper.queryTotal(map);
    }

    @Override
    @Transactional
    public void save(SysRole role) {
        role.setCreateTime(new Date());
        sysRoleMapper.save(role);

        //检查是否越权
        checkPerms(role);
        //保存角色、菜单关系
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }

    @Override
    @Transactional
    public void update(SysRole role) {
        sysRoleMapper.update(role);

        //检查是否越权
        checkPerms(role);
        //更新角色、菜单关系
        sysRoleMenuService.saveOrUpdate(role.getRoleId(), role.getMenuIdList());
    }

    @Override
    @Transactional
    public void deleteBatch(Long[] roleIds) {
        //删除角色、菜单关系
        sysRoleMenuService.deleteRoleMenu(roleIds);
        //删除用户、角色关系
        sysUserRoleService.deleteUserRole(roleIds);
        //删除角色
        sysRoleMapper.deleteBatch(roleIds);
    }

    @Override
    public List<Long> queryRoleIdList(Long createUserId) {
        return sysRoleMapper.queryRoleIdList(createUserId);
    }


    private void checkPerms(SysRole role) {
        //如果不是超级管理员，则需要判断角色的权限是否超过自己的权限
        if (role.getCreateUserId() == Constants.SUPER_ADMIN) {
            return;
        }

        //查询用户所拥有的的菜单列表
        List<Long> menuIdList = sysUserService.queryAllMenuId(role.getCreateUserId());

        if (!menuIdList.containsAll(role.getMenuIdList())) {
            throw new CommonException("新增角色的权限，已超出你的权限范围");
        }
    }
}
