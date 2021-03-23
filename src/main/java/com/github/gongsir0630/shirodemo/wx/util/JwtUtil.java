package com.github.gongsir0630.shirodemo.wx.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.github.gongsir0630.shirodemo.wx.model.WxAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author 码之泪殇 GitHub: https://github.com/gongsir0630
 * @date 2021/3/23 10:26
 * 你的指尖,拥有改变世界的力量
 * 描述: jwt工具类: 生成token签名, token校验
 */
@Component
@SuppressWarnings("All")
public class JwtUtil {
    /**
     * 过期时间: 2小时
     */
    private static final long EXPIRE_TIME = 7200;
    /**
     * 使用 appid 签名
     */
    @Value("${wx.appid}")
    private String appsecret;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 根据微信用户登陆信息创建 token
     * 使用`uuid`随机生成一个jwt-id
     * 将用户的`openId`、`session_key`连同`jwt-id`一起，使用小程序的`appid`进行签名加密并设置过期时间，最终生成`token`
     * 将`"JWT-SESSION-"+jwt-id`和`token`以key-value的形式存入`redis`中，并设置相同的过期时间
     * 注 : 这里的token会被缓存到redis中,用作为二次验证
     * redis里面缓存的时间应该和jwt token的过期时间设置相同
     *
     * @param wxAccount 微信用户信息
     * @return 返回 jwt token
     */
    public String sign(WxAccount account) {
        //JWT 随机ID,做为redis验证的key
        String jwtId = UUID.randomUUID().toString();
        //1 . 加密算法进行签名得到token
        Algorithm algorithm = Algorithm.HMAC256(appsecret);
        String token = JWT.create()
                .withClaim("openId", account.getOpenId())
                .withClaim("sessionKey", account.getSessionKey())
                .withClaim("jwt-id",jwtId)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME * 1000))
                .sign(algorithm);
        //2 . Redis缓存JWT, 注 : 请和JWT过期时间一致
        redisTemplate.opsForValue().set("JWT-SESSION-"+jwtId, token, EXPIRE_TIME, TimeUnit.SECONDS);
        return token;
    }

    /**
     * token 检验
     * @param token
     * @return bool
     */
    public boolean verify(String token) {
        try {
            //1 . 根据token解密，解密出jwt-id , 先从redis中查找出redisToken，匹配是否相同
            String redisToken = redisTemplate.opsForValue().get("JWT-SESSION-" + getClaimsByToken(token).get("jwt-id").asString());
            if (!token.equals(redisToken)) {
                return Boolean.FALSE;
            }
            //2 . 得到算法相同的JWTVerifier
            Algorithm algorithm = Algorithm.HMAC256(appsecret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("openId", getClaimsByToken(redisToken).get("openId").asString())
                    .withClaim("sessionKey", getClaimsByToken(redisToken).get("sessionKey").asString())
                    .withClaim("jwt-id",getClaimsByToken(redisToken).get("jwt-id").asString())
                    .build();
            //3 . 验证token
            verifier.verify(token);
            //4 . Redis缓存JWT续期
            redisTemplate.opsForValue().set("JWT-SESSION-" + getClaimsByToken(token).get("jwt-id").asString(),
                    redisToken,
                    EXPIRE_TIME,
                    TimeUnit.SECONDS);
            return Boolean.TRUE;
        } catch (Exception e) {
            //捕捉到任何异常都视为校验失败
            return Boolean.FALSE;
        }
    }

    /**
     * 从token解密信息
     * @param token token
     * @return
     * @throws JWTDecodeException
     */
    public Map<String, Claim> getClaimsByToken(String token) throws JWTDecodeException {
        return JWT.decode(token).getClaims();
    }
}
