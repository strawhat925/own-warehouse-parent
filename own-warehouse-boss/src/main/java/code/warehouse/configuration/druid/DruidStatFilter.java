package code.warehouse.configuration.druid;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * druid filter
 * package code.warehouse.weichat.druid
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-04-12 9:56
 **/
@WebFilter(filterName = "druidStatFilter", urlPatterns = "/*",
        initParams = { @WebInitParam(name = "exclusions", value = "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*") // 忽略资源
        })
public class DruidStatFilter extends WebStatFilter {


}
