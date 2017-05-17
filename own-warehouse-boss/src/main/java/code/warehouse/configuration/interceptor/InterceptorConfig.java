package code.warehouse.configuration.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 拦截器配置.
 * package code.warehouse.boss.configuration.interceptor
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-12 10:47
 **/
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter{


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CommonInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
