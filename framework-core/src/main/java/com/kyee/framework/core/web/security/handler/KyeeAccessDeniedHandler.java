package com.kyee.framework.core.web.security.handler;

import com.kyee.framework.core.web.security.utils.ResponseHelper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 程峰
 * 创建时间:15/9/7 下午2:54
 * 任务号:MOBILEDEVELOP-10293
 * 创建说明:访问拒绝handler
 */
public class KyeeAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ResponseHelper.createFailResponse(response, "token 无效");
    }
}
