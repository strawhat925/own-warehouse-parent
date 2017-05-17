package code.warehouse.controller;

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
import code.warehouse.common.entity.SysConfigEntity;
import code.warehouse.common.utils.Code;
import code.warehouse.common.utils.Result;
import code.warehouse.common.validator.ValidatorUtils;
import code.warehouse.service.SysConfigService;

/**
 * 系统配置.
 * package code.warehouse.controller
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-16 14:07
 **/
@SuppressWarnings("ALL")
@RestController
@RequestMapping("/sys/config")
public class SysConfigController extends AbstractController {

    @Autowired
    private SysConfigService sysConfigService;


    /**
     * 列表
     *
     * @param params
     *
     * @return
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:config:list")
    public String list(@RequestParam Map<String, Object> params) {
        //分页参数
        //当前页码
        int pageNum = Integer.parseInt(params.get("page").toString());
        //每页条数
        int pageSize = Integer.parseInt(params.get("limit").toString());
        Page<List<Map<String, Object>>> page = PageHelper.startPage(pageNum, pageSize);
        sysConfigService.queryList(params);
        return Result.newResult(Code.SUCCESS, new PageInfo(page)).toJson();
    }


    /**
     * 配置信息
     *
     * @param id
     *
     * @return
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:config:info")
    public String info(@PathVariable("id") Long id) {
        SysConfigEntity config = sysConfigService.queryObject(id);

        return Result.newResult(Code.SUCCESS, config).toJson();
    }


    /**
     * 新增配置
     *
     * @param config
     *
     * @return
     */
    @Transparent(value = "保存配置")
    @RequestMapping("/save")
    @RequiresPermissions("sys:config:save")
    public String save(@RequestBody SysConfigEntity config) {
        ValidatorUtils.validateEntity(config);

        sysConfigService.save(config);
        return Result.SUCCESS;
    }


    /**
     * 修改配置
     *
     * @param config
     *
     * @return
     */
    @Transparent(value = "修改配置")
    @RequestMapping("/update")
    @RequiresPermissions("sys:config:update")
    public String update(@RequestBody SysConfigEntity config) {
        ValidatorUtils.validateEntity(config);

        sysConfigService.update(config);
        return Result.SUCCESS;
    }


    /**
     * 删除配置
     *
     * @param ids
     *
     * @return
     */
    @Transparent(value = "删除配置")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:config:delete")
    public String delete(@RequestBody Long[] ids) {
        sysConfigService.deleteBatch(ids);
        return Result.SUCCESS;
    }
}
