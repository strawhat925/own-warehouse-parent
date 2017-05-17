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

import code.warehouse.common.utils.Code;
import code.warehouse.common.utils.Result;
import code.warehouse.common.validator.ValidatorUtils;
import code.warehouse.entity.ScheduleJobEntity;
import code.warehouse.service.ScheduleJobService;

/**
 * 定时任务.
 * package code.warehouse.controller
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-17 10:49
 **/
@SuppressWarnings("ALL")
@RestController
@RequestMapping("/sys/schedule")
public class ScheduleJobController {

    @Autowired
    private ScheduleJobService scheduleJobService;


    /**
     * 列表
     *
     * @param params
     *
     * @return
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:schedule:list")
    public String list(@RequestParam Map<String, Object> params) {
        //分页参数
        //当前页码
        int pageNum = Integer.parseInt(params.get("page").toString());
        //每页条数
        int pageSize = Integer.parseInt(params.get("limit").toString());
        Page<List<Map<String, Object>>> page = PageHelper.startPage(pageNum, pageSize);
        scheduleJobService.queryList(params);
        return Result.newResult(Code.SUCCESS, new PageInfo(page)).toJson();
    }


    /**
     * 定时任务信息
     *
     * @param jobId
     *
     * @return
     */
    @RequestMapping("/info/{jobId}")
    @RequiresPermissions("sys:schedule:info")
    public String info(@PathVariable("jobId") Long jobId) {
        ScheduleJobEntity scheduleJobEntity = scheduleJobService.queryObject(jobId);

        return Result.newResult(Code.SUCCESS, scheduleJobEntity).toJson();
    }


    /**
     * 保存定时任务
     *
     * @param scheduleJobEntity
     *
     * @return
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:schedule:save")
    public String save(@RequestBody ScheduleJobEntity scheduleJob) {
        ValidatorUtils.validateEntity(scheduleJob);

        scheduleJobService.save(scheduleJob);

        return Result.SUCCESS;
    }


    /**
     * 更新定时任务
     *
     * @param scheduleJobEntity
     *
     * @return
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:schedule:update")
    public String update(@RequestBody ScheduleJobEntity scheduleJob) {
        ValidatorUtils.validateEntity(scheduleJob);

        scheduleJobService.update(scheduleJob);
        return Result.SUCCESS;
    }


    /**
     * 删除定时任务
     *
     * @param jobIds
     *
     * @return
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:schedule:delete")
    public String delete(@RequestBody Long[] jobIds) {
        scheduleJobService.deleteBatch(jobIds);

        return Result.SUCCESS;
    }


    /**
     * 运行定时任务
     *
     * @param jobIds
     *
     * @return
     */
    @RequestMapping("/run")
    @RequiresPermissions("sys:schedule:run")
    public String run(@RequestBody Long[] jobIds) {
        scheduleJobService.run(jobIds);

        return Result.SUCCESS;
    }


    /**
     * 暂停定时任务
     *
     * @param jobIds
     *
     * @return
     */
    @RequestMapping("/pause")
    @RequiresPermissions("sys:schedule:pause")
    public String pause(@RequestBody Long[] jobIds) {
        scheduleJobService.pause(jobIds);

        return Result.SUCCESS;
    }


    /**
     * 恢复定时任务
     *
     * @param jobIds
     *
     * @return
     */
    @RequestMapping("/resume")
    @RequiresPermissions("sys:schedule:resume")
    public String resume(@RequestBody Long[] jobIds) {
        scheduleJobService.resume(jobIds);

        return Result.SUCCESS;
    }
}
