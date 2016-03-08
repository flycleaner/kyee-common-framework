package com.kyee.framework.core.web.security.filter;


import com.kyee.framework.core.web.security.utils.PharseUtil;
import com.kyee.framework.core.web.security.utils.ResponseHelper;
import com.kyee.framework.core.web.security.token.TokenAuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author 程峰
 * 创建时间：15/8/14 下午3:38
 * 任务号：MOBILEDEVELOP-10006
 * 创建说明：登陆获取token的filter
 */
public class LoginFilter extends AbstractAuthenticationProcessingFilter{
    private final TokenAuthenticationService tokenAuthenticationService;

    /**
     * 构造函数
     * @param urlMapping 对urlMapping 进行过滤
     * @param tokenAuthenticationService 授权服务
     * @param authManager 授权管理器
     * */
    public LoginFilter(String urlMapping, TokenAuthenticationService tokenAuthenticationService,
                          AuthenticationManager authManager, AuthenticationFailureHandler authenticationFailureHandler) {
        super(new AntPathRequestMatcher(urlMapping));
        this.tokenAuthenticationService = tokenAuthenticationService;
        setAuthenticationManager(authManager);
        setAuthenticationFailureHandler(authenticationFailureHandler);
    }

    /**
     * 尝试获取授权
     * @param request servlet请求 请求中参数credentials应包含认证信息: username/password@domain
     * @param response servlet 响应
     * @return 返回授权
     * */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String credentials = request.getParameter("credentials");
        String[] userInfos = PharseUtil.pharseUserInfo(credentials);
        if(null == userInfos){
            ResponseHelper.createFailResponse(response, "用户名或密码错误");
            return null;
        }
        final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(
                credentials, userInfos[1]);
        return getAuthenticationManager().authenticate(loginToken);
    }

    /**
     * 授权成功后动作
     * @param request  servlet请求
     * @param response servlet响应
     * @param chain     过滤链
     * @param authentication  授权信息
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authentication) throws IOException {
        SecurityContextHolder.getContext().setAuthentication(tokenAuthenticationService.loginAuthentication(request,response,authentication));
    }
}
