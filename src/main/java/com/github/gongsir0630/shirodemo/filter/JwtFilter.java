package com.github.gongsir0630.shirodemo.filter;

import com.github.gongsir0630.shirodemo.wx.vo.JwtToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 码之泪殇 GitHub: https://github.com/gongsir0630
 * @date 2021/3/23 10:58
 * 你的指尖,拥有改变世界的力量
 * 描述: JWT核心过滤器配置
 * 所有的请求都会先经过Filter，继承官方的BasicHttpAuthenticationFilter，并且重写鉴权的方法
 * 执行流程 preHandle->isAccessAllowed->isLoginAttempt->executeLogin
 */
@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {
    /**
     * 跨域支持
     * @param request 请求
     * @param response 相应
     * @return bool
     * @throws Exception 异常
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        // 判断request是否包含 Authorization 字段
        String auth = getAuthzHeader(request);
        return auth != null && !"".equals(auth);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request,response)) {
            // executeLogin 进入登录逻辑
            // 从request请求头获取 Authorization 字段
            String token = getAuthzHeader(request);
            log.info("--->>>JwtFilter::isAccessAllowed拦截到认证token信息:[{}]",token);
            // 这里会提交给刚刚我们自定义的realm处理
            getSubject(request,response).login(new JwtToken(token));
        }
        // 这里返回true表示所有验证结果都能通过, 在controller中可以使用shiro注解限制是否需要登录权限
        // 设置true即允许游客访问
        // 设置false则必须携带token进行验证
        return true;
    }
}
