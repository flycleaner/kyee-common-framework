package com.kyee.framework.core.web.security.filter;

import com.kyee.framework.core.web.security.token.TokenAuthenticationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 程峰
 * 创建时间：15/8/14 下午3:41
 * 任务号：MOBILEDEVELOP-10006
 * 创建说明：通过token认证的filter
 */
public class AuthenticationFilter extends GenericFilterBean{
    private final TokenAuthenticationService tokenAuthenticationService;

    /**
     * 构造函数
     * @param taService token授权服务
     * */
    public AuthenticationFilter(TokenAuthenticationService taService) {
        this.tokenAuthenticationService = taService;
    }

    /**
     * 过滤
     * @param req servlet请求
     * @param res servlet响应
     * @param chain 过滤器链
     * */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException,
            ServletException {
        Authentication auth = tokenAuthenticationService.getAuthentication((HttpServletRequest) req, (HttpServletResponse) res);
            SecurityContextHolder.getContext().setAuthentication(
                    auth);
            chain.doFilter(req, res);
    }
}
