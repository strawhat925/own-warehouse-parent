package code.warehouse.configuration.shiro;

import com.google.common.collect.Sets;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import code.warehouse.entity.SysMenu;
import code.warehouse.entity.SysUser;
import code.warehouse.service.SysMenuService;
import code.warehouse.service.SysUserService;
import code.warehouse.common.utils.Constants;

/**
 * 用户认证.
 * package code.warehouse.boss.configuration.shiro
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-10 11:56
 **/
@Component
public class UserRealm extends AbstractUserRealm {
    private final static Logger logger = LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 授权（验证权限时调用）
     *
     * @param principalCollection
     *
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SysUser sysUser = (SysUser) principalCollection.getPrimaryPrincipal();
        Long userId = sysUser.getUserId();

        List<String> permsList = null;
        //系统管理员权限，加载所有菜单
        if (userId == Constants.SUPER_ADMIN) {
            List<SysMenu> sysMenuList = sysMenuService.queryList(new HashMap<String, Object>());
            permsList = new ArrayList<String>(sysMenuList.size());
            for (SysMenu sysMenu : sysMenuList) {
                permsList.add(sysMenu.getPerms());
            }
        } else {
            permsList = sysUserService.queryAllPerms(userId);
        }

        //用户权限列表
        Set<String> permsSet = Sets.newHashSet();
        for (String perms : permsList) {
            if (StringUtils.isBlank(perms)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        logger.debug("用户名[{}]拥有权限列表,[perms={}]", sysUser.getUsername(), permsSet);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证（登录时调用）
     *
     * @param authenticationToken
     *
     * @return
     *
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        String password = new String((char[]) authenticationToken.getCredentials());

        SysUser sysUser = sysUserService.queryByUserName(username);

        logger.debug("用户登录认证.[username={},password={}]", username, password);
        logger.debug("----------->");
        logger.debug("           用户信息：" + sysUser.toString());

        //用户不存在
        if (sysUser == null) {
            throw new UnknownAccountException("账号或密码不正确");
        }

        //验证密码
        if (!StringUtils.equalsIgnoreCase(sysUser.getPassword(), password)) {
            throw new IncorrectCredentialsException("账号或密码不正确");
        }
        //账号冻结
        if (sysUser.getStatus() == Constants.UserStatus.INVALID.getValue()) {
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(sysUser, password, getName());
        return info;
    }
}
