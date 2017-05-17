package code.warehouse.configuration.exception;

import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import code.warehouse.common.exception.ValidationException;
import code.warehouse.common.utils.Code;
import code.warehouse.common.utils.Result;

/**
 * 全局异常处理.
 * 1、控制层rest返回json异常信息
 * 2、控制层返回异常页面
 * package code.warehouse.configuration.exception
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-15 16:14
 **/
@ControllerAdvice
@ResponseBody
public class GlobalDefaultExceptionHandler {


    private static final Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

    //@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public String handleValidationException(ValidationException e) {
        logger.error("参数验证失败");

        return Result.newResult(Code.FAIL, e.getMessage()).toJson();
    }


    public static final String DEFAULT_ERROR_VIEW = "error";

    @ExceptionHandler(value = UnauthorizedException.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, UnauthorizedException e) throws UnauthorizedException {
        logger.error("-----------------未认证权限------------------------");
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }
}
