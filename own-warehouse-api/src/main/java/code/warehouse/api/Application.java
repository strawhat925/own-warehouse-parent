package code.warehouse.api;

import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import org.jboss.resteasy.plugins.server.servlet.ResteasyBootstrap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import javax.servlet.ServletContextListener;

/**
 * spring boot start
 * package code.warehouse.api
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-16 17:49
 **/
@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Bean
    public ServletContextInitializer initializer() {
        return servletContext -> {
            // RestEasy configuration
            servletContext.setInitParameter("resteasy.scan", "true");
            servletContext.setInitParameter("resteasy.servlet.mapping.prefix", "/services");
        };
    }

    @Bean
    public ServletContextListener restEasyBootstrap() {
        return new ResteasyBootstrap();
    }

    @Bean
    public ServletRegistrationBean restEasyServlet() {
        final ServletRegistrationBean registrationBean = new ServletRegistrationBean();
        registrationBean.setServlet(new HttpServletDispatcher());
        registrationBean.setName("restEasy-servlet");
        registrationBean.addUrlMappings("/services/*");
        registrationBean.addInitParameter("javax.ws.rs.Application", "kjkrol.MyRESTApplication");
        return registrationBean;
    }
}
