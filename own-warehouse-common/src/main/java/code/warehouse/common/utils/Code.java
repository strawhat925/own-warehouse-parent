package code.warehouse.common.utils;

/**
 * 错误码
 */
public enum Code {

    SUCCESS(200, "SUCCESS"),

    ER_PARAMS(300, "invalid params"),

    FAIL(500, "FAIL");

    private final int code;
    private final String msg;

    /**
     * default cont
     */
    Code(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * get code
     *
     * @return return code
     */
    public int code() {
        return code;
    }

    /**
     * get message
     *
     * @return return message desc
     */
    public String msg() {
        return msg;
    }

    /**
     * 校验状态代码是否是已知的代码
     *
     * @param code
     *         代码
     *
     * @return return true if code is right otherwise return false
     */
    public static boolean validate(Integer code) {
        Code[] codes = Code.values();
        for (Code temp : codes) {
            if (code == temp.code()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 转换 code
     *
     * @param code
     *         code 编码
     *
     * @return return MemberCode instance
     */
    public static Code parseCode(int code) {
        Code[] codes = Code.values();
        for (Code temp : codes) {
            if (code == temp.code()) {
                return temp;
            }
        }
        throw new IllegalArgumentException("无效错误码:" + code + " ,@see com.pyw.passport.Code");
    }

    /**
     * 批量判断 code
     *
     * @param code
     *         input code
     * @param codes
     *         except code
     *
     * @return is true
     */
    public static Boolean assertCode(int code, Code... codes) {
        try {
            if (codes != null && codes.length > 0) {
                Code input = Code.parseCode(code);
                for (Code temp : codes) {
                    if (input.equals(temp)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

}
