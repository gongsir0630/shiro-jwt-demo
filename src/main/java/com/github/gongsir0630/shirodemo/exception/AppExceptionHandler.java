package com.github.gongsir0630.shirodemo.exception;

import com.alibaba.fastjson.JSONObject;
import com.github.gongsir0630.shirodemo.controller.res.CodeMsg;
import com.github.gongsir0630.shirodemo.controller.res.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 码之泪殇 GitHub: https://github.com/gongsir0630
 * @date 2021/3/20 15:49
 * 你的指尖,拥有改变世界的力量
 * 描述: 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class AppExceptionHandler {

    /**
     * 处理 Shiro 异常
     * @param e 异常信息
     * @return json
     */
    @ExceptionHandler({ShiroException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Result<JSONObject>> handShiroException(ShiroException e) {
        log.error("--->>> 捕捉到 [ApiAuthException] 异常: {}", e.getMessage());
        return new ResponseEntity<>(Result.fail(CodeMsg.SHIRO_ERROR,null), HttpStatus.UNAUTHORIZED);
    }

    /**
     * 处理 自定义ApiAuthException异常
     * @param e 异常信息
     * @return json
     */
    @ExceptionHandler({ApiAuthException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Result<JSONObject>> handApiAuthException(ApiAuthException e) {
        log.error("--->>> 捕捉到 [ApiAuthException] 异常: {},{}",e.getCodeMsg().getCode(),e.getCodeMsg().getMsg() );
        return new ResponseEntity<>(Result.fail(e.getCodeMsg(),null), HttpStatus.UNAUTHORIZED);
    }
}
