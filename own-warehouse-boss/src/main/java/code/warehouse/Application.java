package code.warehouse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * spring boot start
 */
@SpringBootApplication
@MapperScan(basePackages = "code.warehouse.dao")
@ServletComponentScan
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);


        //NettyServer nettyServer = (NettyServer) SpringUtils.getBean("nettyServer");
        //System.out.println(nettyServer.getPort());

    }
}
