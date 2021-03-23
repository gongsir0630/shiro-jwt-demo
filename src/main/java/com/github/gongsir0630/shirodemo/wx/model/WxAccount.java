package com.github.gongsir0630.shirodemo.wx.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author 码之泪殇 GitHub: https://github.com/gongsir0630
 * @date 2021/3/22 19:58
 * 你的指尖,拥有改变世界的力量
 * 描述: 微信认证信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WxAccount {
    private String openId;
    private String sessionKey;
}
