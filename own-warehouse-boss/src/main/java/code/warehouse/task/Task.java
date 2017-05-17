package code.warehouse.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * ${DESCRIPTION}
 * package code.warehouse.task
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-17 13:44
 **/
@Component("task")
public class Task {
    private static final Logger logger = LoggerFactory.getLogger(Task.class);

    public void test(String params){
        logger.info("我是带参数的test方法，正在被执行，参数为：" + params);

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.info("==============================================================");
    }

}
