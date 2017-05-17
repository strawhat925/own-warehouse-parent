package code.warehouse.configuration.druid;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * @link http://localhost:8080/druid/index
 *
 * druid servlet
 * package code.warehouse.weichat.druid
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-04-12 9:46
 **/
@WebServlet(urlPatterns = "/druid/*", initParams = { @WebInitParam(name = "allow", value = "192.168.17.28,127.0.0.1"), // IP白名单 (没有配置或者为空，则允许所有访问)
        @WebInitParam(name = "deny", value = "192.168.17.22"), // IP黑名单 (存在共同时，deny优先于allow)
        //由权限管理系统控制访问权限
        //@WebInitParam(name = "loginUsername", value = "root"), // 用户名
        //@WebInitParam(name = "loginPassword", value = "123456"), // 密码
        @WebInitParam(name = "resetEnable", value = "false") // 禁用HTML页面上的“Reset All”功能
})
public class DruidStatViewServlet extends StatViewServlet {


}
