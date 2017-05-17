package code.warehouse.common.validator;

import org.apache.commons.lang3.StringUtils;

import code.warehouse.common.exception.CommonException;

/**
 * 数据校验.
 * package code.warehouse.common.validator
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-15 15:38
 **/
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new CommonException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new CommonException(message);
        }
    }
}
