package code.warehouse.service;

import java.util.List;
import java.util.Map;

import code.warehouse.entity.SysRole;

/**
 * 系统角色接口.
 * package code.warehouse.service
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-15 14:00
 **/
public interface SysRoleService {

    SysRole queryObject(Long roleId);

    List<SysRole> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(SysRole role);

    void update(SysRole role);

    void deleteBatch(Long[] roleIds);

    /**
     * 查询用户创建的角色ID列表
     */
    List<Long> queryRoleIdList(Long createUserId);
}
