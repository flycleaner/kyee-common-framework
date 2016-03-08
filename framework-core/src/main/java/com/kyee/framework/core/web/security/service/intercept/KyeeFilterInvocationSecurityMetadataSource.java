//package com.kyee.framework.core.web.security.service.intercept;
//
//import org.springframework.security.access.ConfigAttribute;
//import org.springframework.security.web.FilterInvocation;
//import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.util.PathMatcher;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author 程峰
// *         创建时间:15/9/7 上午10:50
// *         任务号:
// *         创建说明:
// */
//public class KyeeFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource{
//
//    private Map<String ,List<ConfigAttribute>> url2ConfigAttributesMap;
//    private static final PathMatcher pathMatcher = new AntPathMatcher();
//
//    @Override
//    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
//
//        String requestUrl = ((FilterInvocation) object).getRequestUrl();
//
//        int firstQuestionMarkIndex = requestUrl.indexOf("?");
//        if (firstQuestionMarkIndex != -1) {
//            requestUrl  = requestUrl.substring(0, firstQuestionMarkIndex);
//        }
//
//        for (String resURL : url2ConfigAttributesMap.keySet()) {
//            if (pathMatcher.match(resURL, requestUrl)) {
//                return url2ConfigAttributesMap.get(resURL);
//            }
//        }
//        return null;
//    }
//
//    public void loadRoleFromDb(){
//
//    }
//
//    @Override
//    public Collection<ConfigAttribute> getAllConfigAttributes() {
//        return null;
//    }
//
//    @Override
//    public boolean supports(Class<?> clazz) {
//        return true;
//    }
//
//    public Map<String, List<ConfigAttribute>> getUrl2ConfigAttributesMap() {
//        return url2ConfigAttributesMap;
//    }
//
//    public void setUrl2ConfigAttributesMap(Map<String, List<ConfigAttribute>> url2ConfigAttributesMap) {
//        this.url2ConfigAttributesMap = url2ConfigAttributesMap;
//    }
//
//}
