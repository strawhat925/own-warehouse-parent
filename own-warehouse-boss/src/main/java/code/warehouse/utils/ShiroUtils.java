package code.warehouse.utils;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import code.warehouse.entity.SysUser;


/**
 * shiro util
 * package code.warehouse.boss.utils
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-10 17:14
 **/
public class ShiroUtils {

    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }


    public static SysUser getSysUserInfo() {
        return (SysUser) SecurityUtils.getSubject().getPrincipal();
    }


    public static Long getSysUserId() {
        return getSysUserInfo().getUserId();
    }

    public static void setAttribute(Object key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static Object getAttribute(Object key) {
        return getSession().getAttribute(key);
    }

    public static boolean isLogin() {
        return getSubject().getPrincipal() != null;
    }

    public static void logout() {
        getSubject().logout();
    }

    public static String getKaptcha(String key) {
        String kaptcha = ObjectUtils.toString(getAttribute(key));
        getSession().removeAttribute(key);

        return kaptcha;
    }

}
