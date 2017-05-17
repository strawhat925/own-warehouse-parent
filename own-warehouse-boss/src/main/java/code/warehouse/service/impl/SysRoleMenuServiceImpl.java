package code.warehouse.service.impl;

import com.google.common.collect.Maps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import code.warehouse.dao.SysRoleMenuMapper;
import code.warehouse.service.SysRoleMenuService;

/**
 * 角色、菜单对应关系服务实现.
 * package code.warehouse.service.impl
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-16 9:46
 **/
@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    @Transactional
    public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
        if (menuIdList.size() == 0) {
            return;
        }

        //先删除角色与菜单关系
        sysRoleMenuMapper.delete(roleId);

        //保存角色与菜单关系
        Map<String, Object> params = Maps.newHashMap();
        params.put("roleId", roleId);
        params.put("menuIdList", menuIdList);
        sysRoleMenuMapper.save(params);
    }

    @Override
    public List<Long> queryMenuIdList(Long roleId) {
        return sysRoleMenuMapper.queryMenuIdList(roleId);
    }

    @Override
    public void deleteBatch(Long[] menuIds) {
        sysRoleMenuMapper.deleteBatch(menuIds);
    }

    @Override
    public void deleteRoleMenu(Long[] roleIds) {
        sysRoleMenuMapper.deleteRoleMenu(roleIds);
    }
}
