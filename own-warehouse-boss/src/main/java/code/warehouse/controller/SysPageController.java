package code.warehouse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面视图.
 * package code.warehouse.boss.controller
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-11 17:34
 **/
@Controller
public class SysPageController {


    @RequestMapping("/sys/{url}")
    public String page(@PathVariable("url") String url) {
        System.out.println("---------------------------------------" + url);
        return "/sys/" + url;
    }

    @RequestMapping("/")
    public String home(){
        System.out.println("--------------redirect----------------------------");
        return "redirect:/login";
    }

    @RequestMapping("/login")
    public String login(){
        System.out.println("----------------------login-------------------------------");
        return "login";
    }
}
