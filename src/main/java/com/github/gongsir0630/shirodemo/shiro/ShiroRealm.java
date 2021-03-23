package com.github.gongsir0630.shirodemo.shiro;

import com.github.gongsir0630.shirodemo.controller.res.CodeMsg;
import com.github.gongsir0630.shirodemo.exception.ApiAuthException;
import com.github.gongsir0630.shirodemo.wx.util.JwtUtil;
import com.github.gongsir0630.shirodemo.wx.vo.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 码之泪殇 GitHub: https://github.com/gongsir0630
 * @date 2021/3/23 15:28
 * 你的指尖,拥有改变世界的力量
 * 描述: Realm 的一个配置管理类 allRealm()方法得到所有的realm
 */
@Component
@Slf4j
public class ShiroRealm {
    @Resource
    private JwtUtil jwtUtil;

    /**
     * 封装所有自定义的realm规则链 -> shiro配置中会将规则注入到shiro的securityManager
     * @return 所有自定义的realm规则
     */
    public List<Realm> allRealm() {
        List<Realm> realmList = new LinkedList<>();
        realmList.add(authorizingRealm());
        return Collections.unmodifiableList(realmList);
    }

    /**
     * 自定义 JWT的 Realm
     * 重写 Realm 的 supports() 方法是通过 JWT 进行登录判断的关键
     */
    private AuthorizingRealm authorizingRealm() {
        AuthorizingRealm realm = new AuthorizingRealm() {
            /**
             * 当需要检测 用户权限 时调用此方法，例如checkRole,checkPermission之类的
             * 根据业务需求自行编写验证逻辑
             * @param principalCollection == token
             */
            @Override
            protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
                String token = principalCollection.toString();
                log.info("--->>>PrincipalCollection: [{}]",token);
                // todo: 自定义权限验证, 比如role和permission验证
                return new SimpleAuthorizationInfo();
            }

            /**
             * 默认使用此方法进行用户名正确与否校验: 验证token逻辑
             */
            @Override
            protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
                String jwtToken = (String) authenticationToken.getCredentials();
                String openId = jwtUtil.getClaimsByToken(jwtToken).get("openId").asString();
                String sessionKey = jwtUtil.getClaimsByToken(jwtToken).get("sessionKey").asString();
                if (null == openId || "".equals(openId)) {
                    throw new ApiAuthException(CodeMsg.NO_USER);
                }
                if (null == sessionKey || "".equals(sessionKey)) {
                    throw new ApiAuthException(CodeMsg.SESSION_KEY_ERROR);
                }
                if (!jwtUtil.verify(jwtToken)) {
                    throw new ApiAuthException(CodeMsg.TOKEN_ERROR);
                }
                // 将 openId 和 sessionKey 装配到subject中
                // 在 Controller 中使用 SecurityUtils.getSubject().getPrincipal() 即可获取用户openId
                return new SimpleAuthenticationInfo(openId,sessionKey,this.getClass().getName());
            }

            /**
             * 注意坑点 : 必须重写此方法，不然Shiro会报错
             * 因为创建了 JWTToken 用于替换Shiro原生 token,所以必须在此方法中显式的进行替换，否则在进行判断时会一直失败
             */
            @Override
            public boolean supports(AuthenticationToken token) {
                return token instanceof JwtToken;
            }
        };
        realm.setCredentialsMatcher(credentialsMatcher());
        return realm;
    }

    /**
     * 注意 : 密码校验, 这里因为是JWT形式,就无需密码校验和加密,直接让其返回为true(如果不设置的话,该值默认为false,即始终验证不通过)
     */
    private CredentialsMatcher credentialsMatcher() {
        // 实现boolean doCredentialsMatch(AuthenticationToken var1, AuthenticationInfo var2);
        return (authenticationToken, authenticationInfo) -> true;
    }
}
