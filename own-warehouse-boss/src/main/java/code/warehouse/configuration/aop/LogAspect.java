package code.warehouse.configuration.aop;

import com.alibaba.fastjson.JSON;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import code.warehouse.common.annotation.Transparent;
import code.warehouse.common.entity.SysLogEntity;
import code.warehouse.common.utils.IPUtils;
import code.warehouse.service.SysLogService;
import code.warehouse.utils.ShiroUtils;

/**
 * 日志切面.
 * package code.warehouse.configuration.aop
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-16 11:49
 **/
@Aspect
@Component
public class LogAspect {

    @Autowired
    private SysLogService sysLogService;


    @Pointcut("@annotation(code.warehouse.common.annotation.Transparent)")
    public void logPointCut(){

    }

    /**
     * 保存日志到db
     *
     * @param joinPoint
     */
    @Before("logPointCut()")
    public void saveLog(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SysLogEntity sysLogEntity = new SysLogEntity();
        Transparent transparent = method.getAnnotation(Transparent.class);
        if(transparent != null){
            //注解的描述
            sysLogEntity.setOperation(transparent.value());
        }

        //请求方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLogEntity.setMethod(className + "." + methodName + "()");

        //请求参数
        Object[] args = joinPoint.getArgs();
        String params = JSON.toJSONString(args[0]);
        sysLogEntity.setParams(params);

        //获取request
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();

        sysLogEntity.setIp(IPUtils.getIpAddr(request));

        //用户名
        String username = ShiroUtils.getSysUserInfo().getUsername();
        sysLogEntity.setUsername(username);
        sysLogEntity.setCreateDate(new Date());

        //保存日志
        sysLogService.save(sysLogEntity);
    }


}
