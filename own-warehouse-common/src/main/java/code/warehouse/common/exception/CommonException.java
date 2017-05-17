package code.warehouse.common.exception;

/**
 * 代码生成器自定义异常.
 * package code.warehouse.common.exception
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-12 11:32
 **/
public class CommonException extends RuntimeException{


    private static final long serialVersionUID = 3393783812654877662L;

    private int code = 500;
    private String msg;


    public CommonException(String msg){
        super(msg);
        this.msg = msg;
    }

    public CommonException(String msg, Throwable e){
        super(msg, e);
        this.msg = msg;
    }


    public CommonException(int code, String msg){
        super(msg);
        this.code = code;
        this.msg = msg;
    }


    public CommonException(int code, String msg, Throwable e){
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
