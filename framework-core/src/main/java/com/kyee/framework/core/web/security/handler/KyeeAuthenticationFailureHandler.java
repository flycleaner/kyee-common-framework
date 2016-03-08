package com.kyee.framework.core.web.security.handler;


import com.kyee.framework.core.web.security.utils.ResponseHelper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 程峰
 * 创建时间：15/8/17 上午11:01
 * 任务号：MOBILEDEVELOP-10034
 * 创建说明：认证失败处理handler
 */

public class KyeeAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        ResponseHelper.createFailResponse(response, "用户名或密码错误");
    }
}
