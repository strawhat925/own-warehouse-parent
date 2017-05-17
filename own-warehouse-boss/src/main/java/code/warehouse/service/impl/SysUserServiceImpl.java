package code.warehouse.service.impl;

import com.google.common.collect.Maps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

import code.warehouse.common.exception.CommonException;
import code.warehouse.common.utils.CipherUtils;
import code.warehouse.dao.SysUserMapper;
import code.warehouse.entity.SysUser;
import code.warehouse.service.SysRoleService;
import code.warehouse.service.SysUserRoleService;
import code.warehouse.service.SysUserService;
import code.warehouse.common.utils.Constants;

/**
 * 系统用户服务实现.
 * package code.warehouse.boss.service.impl
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-10 16:02
 **/
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    public List<String> queryAllPerms(Long userId) {
        return sysUserMapper.queryAllPerms(userId);
    }

    @Override
    public List<Long> queryAllMenuId(Long userId) {
        return sysUserMapper.queryAllMenuId(userId);
    }

    @Override
    public SysUser queryByUserName(String username) {
        return sysUserMapper.queryByUserName(username);
    }

    @Override
    public SysUser queryObject(Long userId) {
        return sysUserMapper.queryObject(userId);
    }

    @Override
    public List<SysUser> queryList(Map<String, Object> map) {
        return sysUserMapper.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return sysUserMapper.queryTotal(map);
    }

    @Override
    @Transactional
    public void save(SysUser user) {
        //
        user.setCreateTime(new Date());
        //sha256
        user.setPassword(CipherUtils.SHA256(user.getPassword()));
        sysUserMapper.save(user);

        //检查是否越权
        checkRole(user);
        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    @Override
    @Transactional
    public void update(SysUser user) {

        //sha256
        user.setPassword(CipherUtils.SHA256(user.getPassword()));
        sysUserMapper.update(user);

        //检查是否越权
        checkRole(user);
        //保存用户与角色关系
        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    @Override
    @Transactional
    public void deleteBatch(Long[] userId) {
        sysUserMapper.deleteBatch(userId);
        sysUserRoleService.deleteBatch(userId);
    }

    @Override
    public int updatePassword(Long userId, String password, String newPassword) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("userId", userId);
        map.put("password", CipherUtils.SHA256(password));
        map.put("newPassword", CipherUtils.SHA256(newPassword));
        return sysUserMapper.updatePassword(map);
    }


    private void checkRole(SysUser user) {
        //如果不是超级管理员，则需要判断用户的角色是否自己创建
        if (user.getUserId() == Constants.SUPER_ADMIN) {
            return;
        }

        //查询用户创建的角色列表id
        List<Long> roleIdList = sysRoleService.queryRoleIdList(user.getCreateUserId());
        //判断是否越权
        if (!roleIdList.containsAll(user.getRoleIdList())) {
            throw new CommonException("新增用户所选角色，不是本人创建");
        }
    }
}
