package code.warehouse.api.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * ${DESCRIPTION}
 * package code.warehouse.api.service
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-16 17:50
 **/
@Path("/demo")
public interface DemoService {

    @GET
    @Path("/message")
    public String message();
}
