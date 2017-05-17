package code.warehouse.api.service.rest;

import org.springframework.stereotype.Component;

import code.warehouse.api.service.DemoService;

/**
 * ${DESCRIPTION}
 * package code.warehouse.api.service.rest
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-16 17:51
 **/
@Component
public class DemoServiceImpl implements DemoService {
    @Override
    public String message() {
        return "hello world";
    }
}
