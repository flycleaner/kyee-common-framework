//package com.kyee.framework.core.web.security.service.intercept;
//
//
//import org.springframework.security.access.SecurityMetadataSource;
//import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
//
//import javax.servlet.*;
//import java.io.IOException;
//
//
///**
// * @author 程峰
// *         创建时间:15/9/7 上午9:56
// *         任务号:
// *         创建说明:
// */
//public class KyeeHttpInterceptFilter extends AbstractSecurityInterceptor implements Filter {
//
//    private SecurityMetadataSource securityMetadataSource;
//    @Override
//    public Class<?> getSecureObjectClass() {
//        return null;
//    }
//
//    @Override
//    public SecurityMetadataSource obtainSecurityMetadataSource() {
//        return securityMetadataSource;
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}
