package com.github.gongsir0630.shirodemo.exception;

import com.github.gongsir0630.shirodemo.controller.res.CodeMsg;

/**
 * @author 码之泪殇 GitHub: https://github.com/gongsir0630
 * @date 2021/3/22 20:24
 * 你的指尖,拥有改变世界的力量
 * 描述: 自定义异常, 用于处理Api认证失败异常信息保存
 */
public class ApiAuthException extends RuntimeException {
    private CodeMsg codeMsg;

    public ApiAuthException() {
        super();
    }

    public ApiAuthException(CodeMsg codeMsg) {
        super(codeMsg.getMsg());
        this.codeMsg = codeMsg;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }

    public void setCodeMsg(CodeMsg codeMsg) {
        this.codeMsg = codeMsg;
    }
}
