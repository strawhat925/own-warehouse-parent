package code.warehouse.common.exception;

/**
 * 验证异常.
 * package code.warehouse.common.exception
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-15 16:17
 **/
public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = 1999855191204199984L;
    private int code = 500;
    private String msg;


    public ValidationException(String msg){
        super(msg);
        this.msg = msg;
    }

    public ValidationException(String msg, Throwable e){
        super(msg, e);
        this.msg = msg;
    }


    public ValidationException(int code, String msg){
        super(msg);
        this.code = code;
        this.msg = msg;
    }


    public ValidationException(int code, String msg, Throwable e){
        super(msg, e);
        this.code = code;
        this.msg = msg;
    }


    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
