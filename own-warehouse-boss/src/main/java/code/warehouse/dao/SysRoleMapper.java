package code.warehouse.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

import code.warehouse.common.dao.BaseMapper;
import code.warehouse.entity.SysRole;

/**
 * 角色持久化.
 * package code.warehouse.dao
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-15 14:03
 **/
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 查询用户创建的角色ID列表
     *
     * @param createUserId
     *
     * @return
     */
    List<Long> queryRoleIdList(Long createUserId);
}
