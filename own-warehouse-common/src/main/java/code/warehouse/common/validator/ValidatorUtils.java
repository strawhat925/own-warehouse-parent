package code.warehouse.common.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import code.warehouse.common.exception.CommonException;
import code.warehouse.common.exception.ValidationException;

/**
 * hibernate-validator校验工具类.
 * ${@Link http://docs.jboss.org/hibernate/validator/5.4/reference/en-US/html_single/}
 *
 * package code.warehouse.common.utils
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-15 15:08
 **/
public class ValidatorUtils {

    private static Validator validator;

    static {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    /**
     * 检验对象
     *
     * @param object
     *         待校验对象
     * @param groups
     *         待校验组
     *
     * @throws CommonException
     *         校验不通过，则抛出异常
     */
    public static void validateEntity(Object object, Class<?>... groups) throws CommonException {
        //校验一个对象的默认校验组为Default.class
        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(object, groups);
        if (!constraintViolationSet.isEmpty()) {
            ConstraintViolation<Object> constraintViolation = constraintViolationSet.iterator().next();
            throw new ValidationException(constraintViolation.getMessage());
        }
    }
}
