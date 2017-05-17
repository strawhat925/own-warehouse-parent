package code.warehouse.controller;

import com.google.common.collect.Maps;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import code.warehouse.common.annotation.Transparent;
import code.warehouse.common.utils.Code;
import code.warehouse.common.utils.Result;
import code.warehouse.common.validator.ValidatorUtils;
import code.warehouse.entity.SysRole;
import code.warehouse.service.SysRoleMenuService;
import code.warehouse.service.SysRoleService;
import code.warehouse.common.utils.Constants;

/**
 * 角色.
 * package code.warehouse.controller
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-15 14:00
 **/
@SuppressWarnings("ALL")
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @RequestMapping("/list")
    @RequiresPermissions("sys:role:list")
    public String list(@RequestParam Map<String, Object> params) {
        //当前页码
        int pageNum = Integer.parseInt(params.get("page").toString());
        //每页条数
        int pageSize = Integer.parseInt(params.get("limit").toString());

        Page<List<Map<String, Object>>> page = PageHelper.startPage(pageNum, pageSize);
        sysRoleService.queryList(params);
        return Result.newResult(Code.SUCCESS, new PageInfo(page)).toJson();
    }


    /**
     * 用户角色列表
     *
     * @return
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:role:select")
    public String select() {
        Map<String, Object> params = Maps.newHashMap();

        //不是系统管理员，只能查询自己所拥有的角色
        if (getUserId() != Constants.SUPER_ADMIN) {
            params.put("createUserId", getUserId());
        }
        List<SysRole> sysRoleList = sysRoleService.queryList(params);
        return Result.newResult(Code.SUCCESS, sysRoleList).toJson();
    }


    /**
     * 角色信息
     *
     * @param roleId
     *
     * @return
     */
    @RequestMapping("/info/{roleId}")
    @RequiresPermissions("sys:role:info")
    public String info(@PathVariable("roleId") Long roleId) {
        SysRole role = sysRoleService.queryObject(roleId);

        //查询角色对应的菜单
        List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
        role.setMenuIdList(menuIdList);

        return Result.newResult(Code.SUCCESS, role).toJson();
    }


    /**
     * 新增角色
     *
     * @param role
     *
     * @return
     */
    @Transparent(value = "保存角色")
    @RequestMapping("/save")
    @RequiresPermissions("sys:role:save")
    public String save(@RequestBody SysRole role) {
        ValidatorUtils.validateEntity(role);

        role.setCreateUserId(getUserId());
        sysRoleService.save(role);
        return Result.SUCCESS;
    }


    /**
     * 修改角色
     *
     * @param role
     *
     * @return
     */
    @Transparent(value = "修改角色")
    @RequestMapping("/update")
    @RequiresPermissions("sys:role:update")
    public String update(@RequestBody SysRole role) {
        ValidatorUtils.validateEntity(role);

        role.setCreateUserId(getUserId());
        sysRoleService.update(role);
        return Result.SUCCESS;
    }


    /**
     * 删除角色
     *
     * @param roleIds
     *
     * @return
     */
    @Transparent(value = "删除角色")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    public String delete(@RequestBody Long[] roleIds) {
        sysRoleService.deleteBatch(roleIds);
        return Result.SUCCESS;
    }
}
