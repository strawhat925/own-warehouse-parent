package code.warehouse.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解.
 * package code.warehouse.common.annotation
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-16 11:54
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD})
@Inherited
@Documented
public @interface Transparent {

    String value() default "";
}
