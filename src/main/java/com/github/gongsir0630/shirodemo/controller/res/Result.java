package com.github.gongsir0630.shirodemo.controller.res;

/**
 * @author 码之泪殇 GitHub: https://github.com/gongsir0630
 * @date 2021/3/20 18:45
 * 你的指尖,拥有改变世界的力量
 * 描述:
 * 输出结果的封装
 * 只要get不要set,进行更好的封装
 * @param <T> data泛型
 */
public class Result<T> {

    private int code;
    private String msg;
    private T data;


    private Result(T data){
        this.code=0;
        this.msg="success";
        this.data=data;
    }

    private Result(CodeMsg mg, T data) {
        if (mg==null){
            return;
        }
        this.code=mg.getCode();
        this.msg=mg.getMsg();
        this.data=data;
    }


    /**
     * 成功时
     * @param <T> data泛型
     * @return Result
     */
    public static <T>  Result<T>  success(T data){
        return new Result<T>(data);
    }

    /**
     * 失败
     * @param <T> data泛型
     * @return Result
     */
    public static <T>  Result<T>  fail(CodeMsg mg, T data){
        return new Result<T>(mg,data);
    }

    public int getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }


    public T getData() {
        return data;
    }


}
