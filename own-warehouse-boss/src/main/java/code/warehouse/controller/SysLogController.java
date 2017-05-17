package code.warehouse.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import code.warehouse.common.utils.Code;
import code.warehouse.common.utils.Result;
import code.warehouse.service.SysLogService;

/**
 * 系统日志.
 * package code.warehouse.controller
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-16 13:34
 **/
@SuppressWarnings("ALL")
@RestController
@RequestMapping("/sys/log")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    @RequestMapping("/list")
    @RequiresPermissions("sys:log:list")
    public String list(@RequestParam Map<String, Object> params) {
        //分页参数
        //当前页码
        int pageNum = Integer.parseInt(params.get("page").toString());
        //每页条数
        int pageSize = Integer.parseInt(params.get("limit").toString());
        Page<List<Map<String, Object>>> page = PageHelper.startPage(pageNum, pageSize);
        sysLogService.queryList(params);
        return Result.newResult(Code.SUCCESS, new PageInfo(page)).toJson();
    }
}
