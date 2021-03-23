package com.github.gongsir0630.shirodemo.wx.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author 码之泪殇 GitHub: https://github.com/gongsir0630
 * @date 2021/3/23 10:48
 * 你的指尖,拥有改变世界的力量
 * 描述: 鉴权用的token，需要实现 AuthenticationToken
 */
@Data
@AllArgsConstructor
public class JwtToken implements AuthenticationToken {
    private String token;
    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
