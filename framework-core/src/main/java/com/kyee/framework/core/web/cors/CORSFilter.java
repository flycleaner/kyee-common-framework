package com.kyee.framework.core.web.cors;

import com.kyee.framework.core.exception.KyeeRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author 程峰
 * 创建时间:15/9/7 下午4:28
 * 任务号:MOBILEDEVELOP-10293
 * 创建说明:cors过滤器
 */
@Component
public class CORSFilter extends OncePerRequestFilter {

    private static final String ORIGIN = "Origin";

    private static final Logger LOGGER = LoggerFactory.getLogger(CORSFilter.class);

    @Autowired
    private CORSProperties corsProperties;

    private PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getMethod().equals("OPTIONS")) {
            if ( request.getHeader(ORIGIN)!=null) {
                String origin = request.getHeader(ORIGIN);
                if(isOriginAllowed(origin)) {
                    response.setHeader("Access-Control-Allow-Origin", origin);
                    response.setHeader("Access-Control-Allow-Credentials", corsProperties.getAccessControlAllowCredentials());
                    response.setHeader("Access-Control-Allow-Methods", corsProperties.getAccessControlAllowMethods());
                    response.setHeader("Access-Control-Allow-Headers",
                            corsProperties.getAccessControlAllowHeaders());
                }
            }

            try {
                response.getWriter().print("OK");
                response.getWriter().flush();
            } catch (IOException e) {
                LOGGER.error("写入resonse输入流出错",e);
                throw new KyeeRuntimeException("写入response输入流出错",e);
            }
        }else{
            filterChain.doFilter(request, response);
        }
    }

    private boolean isOriginAllowed(String origin){
        ArrayList<String > allowedOrigins = corsProperties.getAccessControlAllowOrigin();
        if(allowedOrigins.size()==0){
            return true;
        }else{
            for(String alowedOrigin: allowedOrigins){
                if("*".equals(alowedOrigin) || pathMatcher.match(alowedOrigin,origin)){
                    return true;
                }
            }
            return false;
        }
    }
}
