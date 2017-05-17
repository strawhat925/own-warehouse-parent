package code.warehouse.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import code.warehouse.common.utils.Code;
import code.warehouse.common.utils.Result;
import code.warehouse.service.GeneratorService;

/**
 * 代码生成器控制层.
 * package code.warehouse.generator.controller
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-12 11:02
 **/
@RestController
@RequestMapping("/sys/generator")
public class GeneratorController {
    private static final Logger logger = LoggerFactory.getLogger(GeneratorController.class);

    @Qualifier("GeneratorService")
    @Autowired
    private GeneratorService generateService;


    /**
     * 列表
     *
     * @param params
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/list")
    //@RequiresPermissions("sys:generator:list")
    public String list(@RequestParam Map<String, Object> params) {

        System.out.println("------------------------------------------------");
        //分页参数
        //当前页码
        int pageNum = Integer.parseInt(params.get("page").toString());
        //每页条数
        int pageSize = Integer.parseInt(params.get("limit").toString());
        Page<List<Map<String, Object>>> page = PageHelper.startPage(pageNum, pageSize);

        generateService.queryList(params);
        //PageIno 属性详解:{@Link http://blog.csdn.net/u012728960/article/details/50791343}
        return Result.newResult(Code.SUCCESS, new PageInfo(page)).toJson();
    }

    @RequestMapping("/code")
    //@RequiresPermissions("sys:generator:code")
    public void code(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] tableNames = new String[]{};
        //获取表名，不进行xss过滤
        String tables = request.getParameter("tables");
        tableNames = JSON.parseArray(tables).toArray(tableNames);

        logger.debug("-------------------->");
        logger.debug("                  自动生成代码[tables={}]", tableNames);
        byte[] data = generateService.generatorCode(tableNames);

        //下载zip
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"warehouse.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(data, response.getOutputStream());

    }
}
