package code.warehouse.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import code.warehouse.utils.ShiroUtils;
import code.warehouse.common.utils.CipherUtils;
import code.warehouse.common.utils.Code;
import code.warehouse.common.utils.Result;


/**
 * 登录.
 * package code.warehouse.boss.controller
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-09 16:57
 **/
@RestController
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private Producer producer;

    /**
     * 登录
     *
     * @param username
     *         用户名
     * @param password
     *         密码
     * @param captcha
     *         验证码
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/sys/login", method = RequestMethod.POST)
    public String login(String username, String password, String captcha) {
        String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
        if (!StringUtils.equalsIgnoreCase(captcha, kaptcha)) {
            return Result.newResult(Code.FAIL, "验证码不正确").toJson();
        }
        try {
            logger.debug("--------> login");

            Subject subject = SecurityUtils.getSubject();

            // 生成盐
            //用SecureRandom会有性能问题，需配置启动参数-Djava.security.egd=file:/dev/./urandom
            //byte[] bytes = SecureRandom.getSeed(32);
            //String salt = Base64.encodeBuffer(bytes);

            String cipherPassword = CipherUtils.SHA256(password);
            UsernamePasswordToken token = new UsernamePasswordToken(username, cipherPassword);
            subject.login(token);
        } catch (UnknownAccountException e) {
            return Result.newResult(Code.FAIL, e.getMessage()).toJson();
        } catch (IncorrectCredentialsException e) {
            return Result.newResult(Code.FAIL, e.getMessage()).toJson();
        } catch (LockedAccountException e) {
            return Result.newResult(Code.FAIL, e.getMessage()).toJson();
        } catch (AuthenticationException e) {
            return Result.newResult(Code.FAIL, "账号验证失败").toJson();
        }

        return Result.SUCCESS;
    }

    /**
     * 使用组件生成验证码
     *
     * @param response
     *
     * @throws IOException
     */
    @RequestMapping("/captcha.jpg")
    public void captcha(HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);

        ShiroUtils.setAttribute(Constants.KAPTCHA_SESSION_KEY, text);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }

}
