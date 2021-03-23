package com.github.gongsir0630.shirodemo.wx.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.gongsir0630.shirodemo.controller.res.CodeMsg;
import com.github.gongsir0630.shirodemo.exception.ApiAuthException;
import com.github.gongsir0630.shirodemo.wx.model.WxAccount;
import com.github.gongsir0630.shirodemo.wx.service.WxAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author 码之泪殇 GitHub: https://github.com/gongsir0630
 * @date 2021/3/22 21:16
 * 你的指尖,拥有改变世界的力量
 * 描述: 微信接口实现: 用 restTemplate 调用 [wx-java-miniapp] 应用的接口
 */
@Service
@Slf4j
public class WxAccountServiceImpl implements WxAccountService {
    @Value("${wx.appid}")
    private String appid;
    @Value("${wx.url}")
    private String url;

    @Resource
    private RestTemplate restTemplate;

    @Override
    public WxAccount login(String code) {
        // todo: 微信登录: code + appid -> openId + session_key
        // appid: 从配置文件读取
        MultiValueMap<String, Object> request = new LinkedMultiValueMap<>();
        // 参数封装, 微信登录需要以下参数
        request.add("code", code);
        // eg: http://localhost:8081/wx/user/{appid}/login
        String path = url+"/user/"+appid+"/login";
        // 发起请求
        JSONObject dto = restTemplate.postForObject(path, request, JSONObject.class);
        log.info("--->>>来自[{}]的返回 = [{}]",path,dto);
        int errCode = -1;
        if (dto != null ) {
            errCode = Integer.parseInt(dto.get("code").toString());
        } else {
            throw new ApiAuthException(CodeMsg.LOGIN_FAIL);
        }
        if (0 != errCode) {
            throw new ApiAuthException(new CodeMsg(Integer.parseInt(dto.get("code").toString()),
                    dto.get("msg").toString()));
        }
        // code2session success
        JSONObject data = dto.getJSONObject("data");
        return JSON.toJavaObject(data, WxAccount.class);
    }
}
