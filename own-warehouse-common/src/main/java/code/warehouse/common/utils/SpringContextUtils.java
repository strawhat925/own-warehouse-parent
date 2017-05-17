package code.warehouse.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContextAware;

/**
 * spring 上下文工具类.
 * package code.warehouse.common.utils
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-17 11:10
 **/
public class SpringContextUtils implements ApplicationContextAware {

    public static org.springframework.context.ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(org.springframework.context.ApplicationContext applicationContext)
            throws BeansException {
        SpringContextUtils.applicationContext = applicationContext;
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return applicationContext.getBean(name, requiredType);
    }

    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

    public static boolean isSingleton(String name) {
        return applicationContext.isSingleton(name);
    }

    public static Class<? extends Object> getType(String name) {
        return applicationContext.getType(name);
    }
}
