package code.warehouse.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 返回结果构建类.
 * package code.warehouse.common.utils
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-10 14:41
 **/
public final class Result<T> implements Serializable {

    private static final long serialVersionUID = -1833165782243950333L;

    public final static String SUCCESS = Result.newResult(Code.SUCCESS).toJson();
    public final static String FAIL = Result.newResult(Code.FAIL).toJson();

    /**
     * 编码
     */
    @JSONField(serialize = true)
    private Integer code;

    /**
     * 信息
     */
    @JSONField(serialize = true)
    private String msg;

    /**
     * 数据
     */
    private T data;


    public Result() {

    }


    private Result(Builder<T> builder) {
        this.code = builder.code;
        this.msg = builder.msg;
        this.data = builder.data;
    }


    public static <T> Builder<T> newBuilder() {
        return new Builder<T>();
    }


    public static <T> Result<T> newResult(Code resultCode, T data) {
        Result<T> result = new Result<T>();
        result.setCode(resultCode.code());
        result.setMsg(resultCode.msg());
        result.setData(data);
        return result;
    }

    public static <T> Result<T> newResult(Code resultCode) {
        return newResult(resultCode, null);
    }


    public final static class Builder<T> {
        private Integer code;
        private String msg;
        private T data;


        public Builder<T> result(Code resultCode, T data) {
            this.code = resultCode.code();
            this.msg = resultCode.msg();
            this.data = data;
            return this;
        }

        public Builder<T> code(Integer code) {
            this.code = code;
            return this;
        }

        public Builder<T> msg(String msg) {
            this.msg = msg;
            return this;
        }

        public Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public Result<T> build() {
            return new Result<T>(this);
        }


        public String string() {
            return new Result<T>(this).toString();
        }

        public String json() {
            return new Result<T>(this).toJson();
        }
    }



    public String toJson() {
        return JSON.toJSONString(this, false);
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

   /* public static void main(String[] args) {
        String json  = Result.newResult(Code.SUCCESS, "xxxx").toJson();
        System.out.println(json);

    }*/


}
