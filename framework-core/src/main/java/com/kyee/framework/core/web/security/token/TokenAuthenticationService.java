package com.kyee.framework.core.web.security.token;

import com.kyee.framework.core.web.result.Result;
import com.kyee.framework.core.web.result.ResultHelper;
import com.kyee.framework.core.web.security.utils.PharseUtil;
import com.kyee.framework.core.web.security.utils.ResponseHelper;
import com.kyee.framework.core.web.security.session.SessionUser;
import com.kyee.framework.core.web.security.user.User;
import com.kyee.framework.core.web.security.session.ISessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 程峰
 * 创建时间：15/8/14 下午4:20
 * 任务号：MOBILEDEVELOP-10006
 * 创建说明：授权服务
 */
@Service
public class TokenAuthenticationService {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private ISessionManager sessionManager;
    @Autowired
    private AbstractTokenService tokenService;

    private UserDetailsChecker userDetailsChecker = new AccountStatusUserDetailsChecker();

    private PathMatcher pathMatcher = new AntPathMatcher();

    /**
     * 请求头信息中的token
     * */
    private static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";

    /**
     * 过期时间
     */
    @Value("${kyee.security.expire:7200}")
    private long expire;

    /**
     * 登陆时登陆成功返回授权信息并在响应头中和body中输出token
     * @param request 请求
     * @param response 响应
     * @param authentication 授权信息
     * @return 授权信息
     * @throws IOException 获取response输出流失败
     */
    public Authentication loginAuthentication(HttpServletRequest request,HttpServletResponse response, Authentication authentication) throws IOException {

        String credentials = request.getParameter("credentials");
        String usernameWithDomain = PharseUtil.pharseUsernameWithDomain(credentials);
        String domain = PharseUtil.pharseDomain(credentials);
        SessionUser sessionUser;
        if((sessionUser = sessionManager.createSessionUser(usernameWithDomain,expire)) !=null){
        String token = tokenService.parseTokenFromUser(credentials,domain,sessionUser, response);
        response.addHeader(AUTH_HEADER_NAME, token);
        Result result = ResultHelper.successResult(token).build();
        ResponseHelper.createResponse(response, result);
        }else{
            ResponseHelper.createFailResponse(response,"已超过允许登录数");
            return null;
        }
        final UserDetails authenticatedUser = userDetailsService.loadUserByUsername(credentials);
        return this.createSuccessfulAuthentication(authenticatedUser);
    }

    /**
     * 通过token授权时获取授权信息
     * @param request 请求
     * @param response 响应
     * @return 授权信息
     * @throws IOException 读取response输出流失败
     */
    public Authentication getAuthentication(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String token = request.getParameter("token");   //url中获取token
        if(null == token){
            token = request.getHeader(AUTH_HEADER_NAME); //从request header中获取token
            if(null == token){ //没有获取到token
                return null;
            }
        }

        User user = (User)tokenService.parseUserFromToken(token,response);

        if(null == user){ //根据token 解析出的UserDetails为空
            return null;
        }

        this.userDetailsChecker.check(user);
        String sessionKey = PharseUtil.pharseUsernameWithDomain(user.getUsername(),user.getDomain());
        if(!sessionManager.isSessionUserValid(sessionKey,user.getId(),user.getDate())){
            return null;
        }

        if(pathMatcher.match("/authToken/logout",request.getRequestURI())){
            sessionManager.destroySessionUser(sessionKey,user.getId());
        }
        return this.createSuccessfulAuthentication(user);
    }

    /**
     * 创建成功授权信息
     * @param user 用户信息
     * @return 授权信息
     */
    protected Authentication createSuccessfulAuthentication(UserDetails user) {
        return new UsernamePasswordAuthenticationToken(user,null, user.getAuthorities());
    }

}
