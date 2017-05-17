package code.warehouse.configuration.freemarker;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import code.warehouse.common.freemarker.ShiroTags;

/**
 * shiro tag configure
 * package code.warehouse.configuration.freemarker
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-15 18:14
 **/
@Component
public class ShiroFreeMarkerConfigurer implements InitializingBean {

    @Autowired
    private Configuration configuration;

    @Override
    public void afterPropertiesSet() throws IOException, TemplateException {
        configuration.setSharedVariable("shiro", new ShiroTags());
    }
}
