package com.kyee.framework.core.web.security.user;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author 程峰
 * 创建时间：15/8/14 下午5:23
 * 任务号：MOBILEDEVELOP-10034
 * 创建说明：实现GrantedAuthority接口
 */
public class UserAuthority implements GrantedAuthority {

    /**
     * 权限
     * */
    private String authority;

    public UserAuthority(){

    }

    public UserAuthority(String authority){
        this.authority =authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority){
        this.authority =authority;
    }
}
