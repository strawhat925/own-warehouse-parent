package code.warehouse.controller;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import code.warehouse.common.annotation.Transparent;
import code.warehouse.common.utils.Code;
import code.warehouse.common.utils.Result;
import code.warehouse.common.validator.ValidatorUtils;
import code.warehouse.common.validator.group.AddGroup;
import code.warehouse.common.validator.group.UpdateGroup;
import code.warehouse.entity.SysUser;
import code.warehouse.service.SysUserRoleService;
import code.warehouse.service.SysUserService;
import code.warehouse.common.utils.Constants;

/**
 * 菜单.
 * package code.warehouse.boss.controller
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-11 10:20
 **/
@SuppressWarnings("ALL")
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(SysUserController.class);

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 列表
     *
     * @param params
     *
     * @return
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:user:list")
    public String list(@RequestParam Map<String, Object> params) {
        //分页参数
        //当前页码
        int pageNum = Integer.parseInt(params.get("page").toString());
        //每页条数
        int pageSize = Integer.parseInt(params.get("limit").toString());
        Page<List<Map<String, Object>>> page = PageHelper.startPage(pageNum, pageSize);
        sysUserService.queryList(params);
        return Result.newResult(Code.SUCCESS, new PageInfo(page)).toJson();
    }

    /**
     * 用户信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/info")
    public String info() {
        return Result.newResult(Code.SUCCESS, getUser()).toJson();
    }


    @RequestMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    public String info(@PathVariable Long userId) {
        SysUser user = sysUserService.queryObject(userId);

        //获取用户的角色列表
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);

        return Result.newResult(Code.SUCCESS, user).toJson();
    }


    /**
     * 修改登录密码
     *
     * @param password
     *         旧密码
     * @param newPassword
     *         新密码
     *
     * @return
     */
    @RequestMapping("/password")
    public String password(String password, String newPassword) {
        int count = sysUserService.updatePassword(getUserId(), password, newPassword);
        if (count == 0) {
            Result.newResult(Code.FAIL, "原密码不正确");
        }
        return Result.newResult(Code.SUCCESS).toJson();
    }


    /**
     * 新增用户信息
     *
     * @param user
     *
     * @return
     */
    @Transparent(value = "保存用户")
    @RequestMapping("/save")
    @RequiresPermissions("sys:user:save")
    public String save(@RequestBody SysUser user) {
        ValidatorUtils.validateEntity(user, AddGroup.class);

        logger.debug("新增用户信息.[user={}]", user.toString());
        user.setCreateUserId(getUserId());
        sysUserService.save(user);

        return Result.SUCCESS;
    }


    /**
     * 修改用户信息
     *
     * @param user
     *
     * @return
     */
    @Transparent(value = "修改用户")
    @RequestMapping("/update")
    @RequiresPermissions("sys:user:update")
    public String update(@RequestBody SysUser user) {
        ValidatorUtils.validateEntity(user, UpdateGroup.class);

        user.setCreateUserId(getUserId());
        sysUserService.update(user);

        return Result.SUCCESS;
    }


    /**
     * 删除用户信息
     *
     * @param userIds
     *
     * @return
     */
    @Transparent(value = "删除用户")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public String delete(@RequestBody Long[] userIds) {
        //系统管理员不能删除
        if (ArrayUtils.contains(userIds, Constants.SUPER_ADMIN)) {
            return Result.newResult(Code.FAIL, "系统管理员不能删除").toJson();
        }

        if (ArrayUtils.contains(userIds, getUserId())) {
            return Result.newResult(Code.FAIL, "当前用户不能删除").toJson();
        }
        logger.debug("删除用户信息，用户ID[userIds={}]", userIds);
        sysUserService.deleteBatch(userIds);
        return Result.SUCCESS;
    }
}
