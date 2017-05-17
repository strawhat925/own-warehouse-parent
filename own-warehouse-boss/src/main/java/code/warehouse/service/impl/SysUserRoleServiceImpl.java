package code.warehouse.service.impl;

import com.google.common.collect.Maps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import code.warehouse.dao.SysUserRoleMapper;
import code.warehouse.service.SysUserRoleService;

/**
 * 用户与角色对应关系服务实现.
 * package code.warehouse.service.impl
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-15 15:47
 **/
@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    @Transactional
    public void saveOrUpdate(Long userId, List<Long> roleIdList) {
        if (roleIdList.size() == 0) {
            return;
        }

        //先删除用户与角色关系
        sysUserRoleMapper.delete(userId);

        //保存用户与角色关系
        Map<String, Object> params = Maps.newHashMap();
        params.put("userId", userId);
        params.put("roleIdList", roleIdList);
        sysUserRoleMapper.save(params);
    }

    @Override
    public List<Long> queryRoleIdList(Long userId) {
        return sysUserRoleMapper.queryRoleIdList(userId);
    }

    @Override
    public void delete(Long userId) {
        sysUserRoleMapper.delete(userId);
    }

    @Override
    public void deleteBatch(Long[] userIds) {
        sysUserRoleMapper.deleteBatch(userIds);
    }

    @Override
    public void deleteUserRole(Long[] roleIds) {
        sysUserRoleMapper.deleteUserRole(roleIds);
    }
}
