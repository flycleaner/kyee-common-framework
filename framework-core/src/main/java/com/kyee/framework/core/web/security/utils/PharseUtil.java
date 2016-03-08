package com.kyee.framework.core.web.security.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 程峰
 * 创建时间:15/9/21 下午4:33
 * 任务号:MOBILEDEVELOP-10399
 * 创建说明:解析认证接口传来的credentials， 格式：username/password@domain
 */
public class PharseUtil {

    /**
     * credentials格式: username/password@domain
     */
    private static final Pattern rawUsernameParamPattern=java.util.regex.Pattern.compile("^(.+)/(.+)@(.*$)");

    /**
     * 解析用户名，密码，域
     * @param credentials 获取token的凭证 格式:username/password@domain
     * @return 数组，分别是用户名，密码，域。如果格式不正确返回null
     */
    public static String[] pharseUserInfo(String credentials){
        if(credentials==null||"".equals(credentials)){
            return null;
        }
        Matcher matcher = rawUsernameParamPattern.matcher(credentials);
        if(matcher.matches() && 3 == matcher.groupCount()){
            return new String[]{matcher.group(1),matcher.group(2),matcher.group(3)};
        }else{
            return null;
        }
    }

    /**
     * 返回带有域的用户名
     * @param credentials 获取token的凭证 格式:username/password@domain
     * @return 返回带有域的用户名: username@domain。 SessionManager{@link com.kyee.framework.core.web.security.session.SessionManager}中username为带域的用户名
     */
    public static String pharseUsernameWithDomain(String credentials){
        String [] userInfos = pharseUserInfo(credentials);
        if(userInfos == null){
            return null;
        }else {
            return userInfos[0] + "@" + userInfos[2];
        }
    }

    /**
     * 返回带有域的用户名
     * @param username 用户名
     * @param domain 域
     * @return 返回带有域的用户名: username@domain。 SessionManager{@link com.kyee.framework.core.web.security.session.SessionManager}中username为带域的用户名
     */
    public static String pharseUsernameWithDomain(String username,String domain){
        return username+"@"+domain;
    }

    /**
     * 获取域
     * @param credentials 获取token的凭证 格式:username/password@domain
     * @return 域
     */
    public static String pharseDomain(String credentials){
        String [] userInfos = pharseUserInfo(credentials);
        if(userInfos == null){
            return null;
        }else {
            return userInfos[2];
        }
    }

}
