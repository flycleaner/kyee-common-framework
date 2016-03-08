package com.kyee.framework.core.web.security.handler;

import com.kyee.framework.core.web.security.utils.ResponseHelper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 程峰
 * 创建时间:15/9/8 上午11:26
 * 任务号:MOBILEDEVELOP-10293
 * 创建说明:token认证失败调用入口
 */
public class KyeeAuthenticationEntryPoint implements AuthenticationEntryPoint{

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) {
        ResponseHelper.createFailResponse(response, "token 无效");
    }
}
