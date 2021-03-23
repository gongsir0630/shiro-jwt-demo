package com.github.gongsir0630.shirodemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.gongsir0630.shirodemo.mapper.UserMapper;
import com.github.gongsir0630.shirodemo.model.User;
import com.github.gongsir0630.shirodemo.service.UserService;
import com.github.gongsir0630.shirodemo.wx.model.WxAccount;
import com.github.gongsir0630.shirodemo.wx.service.WxAccountService;
import com.github.gongsir0630.shirodemo.wx.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 码之泪殇 GitHub: https://github.com/gongsir0630
 * @date 2021/3/23 11:19
 * 你的指尖,拥有改变世界的力量
 * 描述:
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private WxAccountService wxAccountService;

    @Override
    public Map<String, String> login(String jsCode) {
        Map<String, String> res = new HashMap<>();
        WxAccount wxAccount = wxAccountService.login(jsCode);
        log.info("--->>>wxAccount信息:[{}]",wxAccount);
        User user = userMapper.selectById(wxAccount.getOpenId());
        if (user == null) {
            // todo: 用户不存在, 提醒用户提交注册信息
            res.put("canLogin",Boolean.FALSE.toString());
        } else {
            res.put("canLogin",Boolean.TRUE.toString());
        }
        res.put("token", jwtUtil.sign(wxAccount));
        return res;
    }
}
