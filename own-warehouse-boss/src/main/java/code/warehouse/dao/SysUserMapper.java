package code.warehouse.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

import code.warehouse.common.dao.BaseMapper;
import code.warehouse.entity.SysUser;

/**
 * 系统用户持久化.
 * package code.warehouse.boss.dao
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-10 17:47
 **/
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 查询用户的所有权限
     *
     * @param userId
     *         用户ID
     */
    List<String> queryAllPerms(Long userId);

    /**
     * 查询用户的所有菜单ID
     *
     * @param userId
     *         用户ID
     *
     * @return
     */
    List<Long> queryAllMenuId(Long userId);


    /**
     * 根据用户名，查询系统用户
     *
     * @param username
     *         用户名
     *
     * @return
     */
    SysUser queryByUserName(String username);

    /**
     * 修改密码
     *
     * @param map
     *
     * @return
     */
    int updatePassword(Map<String, Object> map);
}
