package code.warehouse.configuration.mybatis;

import com.github.pagehelper.PageHelper;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import java.util.Properties;

import javax.sql.DataSource;

/**
 * mybatis configuration
 * package code.warehouse.boss.configuration.mybatis
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-04-14 9:48
 **/
@Configuration
@EnableTransactionManagement
public class MybatisConfig implements TransactionManagementConfigurer {

    @SuppressWarnings("ALL")
    @Autowired
    DataSource dataSource;


    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //bean.setTypeAliasesPackage("");

        //分页插件
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);

        //添加插件
        bean.setPlugins(new Interceptor[]{ pageHelper });

        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            /**
             * 配置1：使用该配置有一定的局限性，只能将接口和mapper文件放到同一个包下，并且同名
             */
            //设置骆驼峰映射
            //注意
            bean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
            //// 扫描该包下的接口和mapper文件，这里使用spring boot启动类加载的@MapperScan注解
            //bean.getObject().getConfiguration().addMappers("code.warehouse.dao");

            /**
             * 配置2：扫描mapper的xml，不需要关注接口和mapper是否同一包下
             */
            //bean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
            return bean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
