package code.warehouse.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import code.warehouse.common.annotation.Transparent;
import code.warehouse.common.exception.ValidationException;
import code.warehouse.common.utils.Code;
import code.warehouse.common.utils.Result;
import code.warehouse.entity.SysMenu;
import code.warehouse.service.SysMenuService;
import code.warehouse.common.utils.Constants;

/**
 * 系统用户.
 * package code.warehouse.boss.controller
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-11 14:03
 **/
@SuppressWarnings("ALL")
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends AbstractController {

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 用户拥有的菜单列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/user")
    public String getUserMenuList() {
        List<SysMenu> sysMenuList = sysMenuService.getUserMenuList(getUserId());

        System.out.println(sysMenuList.toString());
        return Result.newResult(Code.SUCCESS, sysMenuList).toJson();
    }


    /**
     * 菜单列表
     *
     * @param params
     *
     * @return
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:menu:list")
    public String list(@RequestParam Map<String, Object> params) {

        //分页参数
        //当前页码
        int pageNum = Integer.parseInt(params.get("page").toString());
        //每页条数
        int pageSize = Integer.parseInt(params.get("limit").toString());
        Page<List<Map<String, Object>>> page = PageHelper.startPage(pageNum, pageSize);
        sysMenuService.queryList(params);
        return Result.newResult(Code.SUCCESS, new PageInfo(page)).toJson();
    }


    /**
     * 选择菜单（添加、修改菜单）
     *
     * @return
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:menu:select")
    public String select() {
        //查询列表数据
        List<SysMenu> sysMenuList = sysMenuService.queryNotButtonList();

        //添加顶级菜单
        SysMenu root = new SysMenu();
        root.setMenuId(0L);
        root.setName("一级菜单");
        root.setParentId(-1L);
        root.setOpen(true);
        sysMenuList.add(root);

        return Result.newResult(Code.SUCCESS, sysMenuList).toJson();
    }


    /**
     * 角色授权菜单
     *
     * @return
     */
    @RequestMapping("/perms")
    @RequiresPermissions("sys:menu:perms")
    public String perms() {
        //查询列表数据
        List<SysMenu> sysMenuList = null;
        if (getUserId() == Constants.SUPER_ADMIN) {
            sysMenuList = sysMenuService.queryList(new HashMap<String, Object>());
        } else {
            sysMenuList = sysMenuService.queryUserList(getUserId());
        }

        return Result.newResult(Code.SUCCESS, sysMenuList).toJson();
    }


    /**
     * 菜单信息
     *
     * @param menuId
     *
     * @return
     */
    @RequestMapping("/info/{menuId}")
    @RequiresPermissions("sys:menu:info")
    public String info(@PathVariable("menuId") Long menuId) {
        SysMenu menu = sysMenuService.queryObject(menuId);
        return Result.newResult(Code.SUCCESS, menu).toJson();
    }


    /**
     * 新增菜单
     *
     * @return
     */
    @Transparent(value = "保存菜单")
    @RequestMapping("/save")
    @RequiresPermissions("sys:menu:save")
    public String save(@RequestBody SysMenu menu) {
        //验证表单
        verifyForm(menu);

        sysMenuService.save(menu);

        return Result.SUCCESS;
    }


    /**
     * 修改菜单
     *
     * @param menu
     *
     * @return
     */
    @Transparent(value = "修改菜单")
    @RequestMapping("/update")
    @RequiresPermissions("sys:menu:update")
    public String update(@RequestBody SysMenu menu) {
        //验证表单
        verifyForm(menu);
        sysMenuService.update(menu);

        return Result.SUCCESS;
    }


    /**
     * 删除菜单
     *
     * @param menuIds
     *
     * @return
     */
    @Transparent(value = "删除菜单")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:menu:delete")
    public String delete(@RequestBody Long[] menuIds) {
        for (Long menuId : menuIds) {
            if (menuId.longValue() <= 30) {
                return Result.newResult(Code.FAIL, "系统菜单不能删除").toJson();
            }
        }

        sysMenuService.deleteBatch(menuIds);

        return Result.SUCCESS;
    }


    /**
     * 验证菜单表单
     *
     * @param menu
     */
    private void verifyForm(SysMenu menu) {
        if (StringUtils.isBlank(menu.getName())) {
            throw new ValidationException("菜单名称不能为空");
        }

        if (menu.getParentId() == null) {
            throw new ValidationException("上级菜单不能为空");
        }
        //菜单
        if (menu.getType() == Constants.MenuType.MENU.getValue()) {
            if (StringUtils.isBlank(menu.getUrl())) {
                throw new ValidationException("菜单URL不能为空");
            }
        }

        //上级菜单类型
        int parentType = Constants.MenuType.CATALOG.getValue();
        if (menu.getParentId() != 0) {
            SysMenu parentMenu = sysMenuService.queryObject(menu.getParentId());
            parentType = parentMenu.getType();
        }

        //目录、菜单
        if (menu.getType() == Constants.MenuType.CATALOG.getValue() ||
                menu.getType() == Constants.MenuType.MENU.getValue()) {
            if (parentType != Constants.MenuType.CATALOG.getValue()) {
                throw new ValidationException("上级菜单只能是目录");
            }
            return;
        }

        //按钮
        if (menu.getType() == Constants.MenuType.BUTTON.getValue()) {
            if (parentType != Constants.MenuType.MENU.getValue()) {
                throw new ValidationException("上级菜单只能为菜单类型");
            }
            return;
        }
    }
}
