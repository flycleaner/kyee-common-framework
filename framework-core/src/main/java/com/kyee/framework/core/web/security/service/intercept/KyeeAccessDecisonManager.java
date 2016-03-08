//package com.kyee.framework.core.web.security.service.intercept;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.access.AccessDecisionManager;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.access.ConfigAttribute;
//import org.springframework.security.authentication.InsufficientAuthenticationException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//
//import java.util.Collection;
//
///**
// * @author 程峰
// *         创建时间:15/9/7 上午11:28
// *         任务号:
// *         创建说明:
// */
//
//public class KyeeAccessDecisonManager implements AccessDecisionManager {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(KyeeAccessDecisonManager.class);
//
//    @Override
//    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
//        if (configAttributes == null) {
//            return;
//        }
//
//        @SuppressWarnings("unchecked")
//        Collection<GrantedAuthority> as= (Collection<GrantedAuthority>)authentication.getAuthorities();
//
//        for (ConfigAttribute ca : configAttributes) {
//            String needRole =  ca.getAttribute();
//            LOGGER.info("URL:{}----needrole:{}",object,needRole);
//            // ga 为用户所被赋予的权限。 needRole 为访问相应的资源应该具有的权限。
//            for (GrantedAuthority ga : as) {
//                if (needRole.trim().equals(ga.getAuthority().trim())) {
//                    return;
//                }
//
//            }
//
//        }
//
//        throw new AccessDeniedException("没有权限");
//    }
//
//    @Override
//    public boolean supports(ConfigAttribute attribute) {
//        return true;
//    }
//
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return true;
//    }
//}
