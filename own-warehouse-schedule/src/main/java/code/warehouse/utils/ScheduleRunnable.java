package code.warehouse.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

import code.warehouse.common.exception.CommonException;
import code.warehouse.common.utils.SpringContextUtils;
import code.warehouse.entity.ScheduleJobEntity;

/**
 * 定时任务执行线程.
 * package code.warehouse.utils
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-17 11:08
 **/
public class ScheduleRunnable implements Runnable {
    private Object target;
    private Method method;
    private String params;

    public ScheduleRunnable(ScheduleJobEntity scheduleJob) throws NoSuchMethodException {
        this.target = SpringContextUtils.getBean(scheduleJob.getBeanName());
        this.params = scheduleJob.getParams();

        if (StringUtils.isNotBlank(scheduleJob.getParams())) {
            this.method = target.getClass().getDeclaredMethod(scheduleJob.getMethodName(), String.class);
        } else {
            this.method = target.getClass().getDeclaredMethod(scheduleJob.getMethodName());
        }

    }

    @Override
    public void run() {
        try {
            ReflectionUtils.makeAccessible(method);
            if (StringUtils.isNotBlank(params)) {
                method.invoke(target, params);
            } else {
                method.invoke(target);
            }
        } catch (Exception e) {
            throw new CommonException("执行定时任务失败", e);
        }

    }
}
