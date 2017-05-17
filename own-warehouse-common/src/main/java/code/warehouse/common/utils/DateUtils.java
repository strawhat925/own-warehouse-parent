package code.warehouse.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期处理工具类.
 * package code.warehouse.common.utils
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-12 12:02
 **/
public final class DateUtils {

    /**
     * 时间格式(yyyy-MM-dd)
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 时间格式(yyyy-MM-dd HH:mm:ss)
     */
    public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public final static String DATE_SECODE_PATTERN = "yyyy-MM-dd HH:mm";



    public static String format(Date date) {
        return format(date, DATE_PATTERN);
    }

    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }
}
