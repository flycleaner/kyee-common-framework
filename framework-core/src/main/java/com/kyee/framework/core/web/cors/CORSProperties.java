package com.kyee.framework.core.web.cors;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


/**
 * @author 程峰
 * 创建时间:15/9/9 下午1:44
 * 任务号:MOBILEDEVELOP-10293
 * 创建说明: cors配置属性
 */
@Component
@ConfigurationProperties(prefix = "kyee.cors")
public class CORSProperties {
    /**
     * Access-Control-Allow-Origin
     * 允许的请求来源
     */
    private ArrayList<String> AccessControlAllowOrigin = new ArrayList<>();
    /**
     * Access-Control-Allow-Credentials
     * 若设为true则会发送cookies
     */
    private String  AccessControlAllowCredentials = "true";
    /**
     * Access-Control-Allow-Methods
     * 允许的方法
     */
    private String AccessControlAllowMethods = "POST, GET, OPTIONS, DELETE, PUT";
    /**
     * Access-Control-Allow-Headers
     * 允许的请求头
     */
    private String AccessControlAllowHeaders = "*/*";

    public ArrayList<String> getAccessControlAllowOrigin() {
        return AccessControlAllowOrigin;
    }

    public void setAccessControlAllowOrigin(ArrayList<String> accessControlAllowOrigin) {
        AccessControlAllowOrigin = accessControlAllowOrigin;
    }

    public String getAccessControlAllowCredentials() {
        return AccessControlAllowCredentials;
    }

    public void setAccessControlAllowCredentials(String accessControlAllowCredentials) {
        AccessControlAllowCredentials = accessControlAllowCredentials;
    }

    public String getAccessControlAllowMethods() {
        return AccessControlAllowMethods;
    }

    public void setAccessControlAllowMethods(String accessControlAllowMethods) {
        AccessControlAllowMethods = accessControlAllowMethods;
    }

    public String getAccessControlAllowHeaders() {
        return AccessControlAllowHeaders;
    }

    public void setAccessControlAllowHeaders(String accessControlAllowHeaders) {
        AccessControlAllowHeaders = accessControlAllowHeaders;
    }
}
