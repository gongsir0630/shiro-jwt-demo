package com.github.gongsir0630.shirodemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.github.gongsir0630.shirodemo.controller.res.CodeMsg;
import com.github.gongsir0630.shirodemo.controller.res.Result;
import com.github.gongsir0630.shirodemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 码之泪殇 GitHub: https://github.com/gongsir0630
 * @date 2021/3/23 11:12
 * 你的指尖,拥有改变世界的力量
 * 描述: 用户信息接口类,包含小程序登录注册
 */
@RestController
@Slf4j
@RequestMapping("user")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 从认证信息中获取用户Id: userId == openId
     * @return userId
     */
    private String getUserId() {
        return SecurityUtils.getSubject().getPrincipal().toString();
    }

    /**
     * 小程序用户登录接口: 通过js_code换取openId, 判断用户是否已经注册
     * @param code wx.login() 得到的code凭证
     * @return token
     */
    @PostMapping("/login")
    public ResponseEntity<Result<JSONObject>> login(String code) {
        if (StringUtils.isBlank(code)) {
            return new ResponseEntity<>(Result.fail(new CodeMsg(401,"code is empty"), null), HttpStatus.OK);
        }
        log.info("--->接收到来自小程序端的code:[{}]",code);
        // todo: 使用 code -> wxAccountService.login() -> openId,session_key
        Map<String, String> loginMap = userService.login(code);
        boolean canLogin = Boolean.parseBoolean(loginMap.get("canLogin"));
        String token = loginMap.get("token");
        JSONObject data = new JSONObject();
        data.put("token",token);
        data.put("canLogin",canLogin);
        log.info("--->>>返回认证信息:[{}]", data.toString());
        if (!canLogin) {
            // todo: 用户不存在,提示用户注册
            return new ResponseEntity<>(Result.fail(CodeMsg.NO_USER,data),HttpStatus.OK);
        }
        return new ResponseEntity<>(Result.success(data),HttpStatus.OK);
    }

    /**
     * 使用 RequiresAuthentication 注解, 需要验证才能访问
     * @return userId
     */
    @GetMapping("/hello")
    @RequiresAuthentication
    public ResponseEntity<Result<JSONObject>> requireAuth() {
        JSONObject data = new JSONObject();
        data.put("hello",getUserId());
        return new ResponseEntity<>(Result.success(data),HttpStatus.OK);
    }
}
