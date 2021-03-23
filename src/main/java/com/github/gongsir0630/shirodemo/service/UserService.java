package com.github.gongsir0630.shirodemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.gongsir0630.shirodemo.model.User;

import java.util.Map;

/**
 * @author 码之泪殇 GitHub: https://github.com/gongsir0630
 * @date 2021/3/22 19:49
 * 你的指尖,拥有改变世界的力量
 * 描述: 用户接口
 */
public interface UserService extends IService<User> {
    /**
     * 登录
     * @param jsCode 小程序code
     * @return 登录信息: 包含token
     */
    Map<String, String> login(String jsCode);
}
