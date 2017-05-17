package code.warehouse.configuration.environment;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * 主要是@Configuration，实现接口：EnvironmentAware就能获取到系统环境信息
 * package code.warehouse.configuration.environment
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-16 11:02
 **/
@Configuration
public class SystemEnvironmentAware implements EnvironmentAware {


    /**
     * 注意重写的方法 setEnvironment 是在系统启动的时候被执行
     * @param environment
     */
    @Override
    public void setEnvironment(Environment environment) {
        System.out.println("JAVA_HOME================:" + environment.getProperty("JAVA_HOME"));
        System.out.println("spring.datasource.url:" + environment.getProperty("spring.datasource.url"));
    }
}
