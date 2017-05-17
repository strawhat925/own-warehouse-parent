package code.warehouse.controller;

import code.warehouse.entity.SysUser;
import code.warehouse.utils.ShiroUtils;

/**
 * 控制层公共组件
 * package code.warehouse.boss.controller
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-11 13:58
 **/
public abstract class AbstractController {

    protected SysUser getUser(){
        return ShiroUtils.getSysUserInfo();
    }

    protected Long getUserId(){
        return getUser().getUserId();
    }
}
