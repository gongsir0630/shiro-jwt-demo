package com.github.gongsir0630.shirodemo.controller.res;

/**
 * @author 码之泪殇 GitHub: https://github.com/gongsir0630
 * @date 2021/3/22 20:17
 * 你的指尖,拥有改变世界的力量
 * 描述: code和msg封装
 */
public class CodeMsg {
    private final int code;
    private final String msg;

    public static CodeMsg SUCCESS=new CodeMsg(0,"success");

    public static CodeMsg LOGIN_FAIL = new CodeMsg(-1,"code2session failure, please try aging");

    public static CodeMsg NO_USER = new CodeMsg(1000,"user not found");
    public static CodeMsg SESSION_KEY_ERROR = new CodeMsg(1001,"sessionKey is invalid");
    public static CodeMsg TOKEN_ERROR = new CodeMsg(1002,"token is invalid");
    public static CodeMsg SHIRO_ERROR = new CodeMsg(1003,"token is invalid");

    public CodeMsg(int code, String msg) {
        this.code=code;
        this.msg=msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "CodeMsg{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }

}
