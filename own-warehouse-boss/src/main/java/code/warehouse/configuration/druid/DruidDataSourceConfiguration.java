package code.warehouse.configuration.druid;

import com.alibaba.druid.pool.DruidDataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * druid datasource
 * package code.warehouse.boss.configuration.druid
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-04-12 10:06
 **/
public class DruidDataSourceConfiguration {


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }
}
