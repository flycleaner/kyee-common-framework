package com.kyee.framework.core.web.security.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author 程峰
 * 创建时间：15/8/14 下午17:18
 * 任务号：MOBILEDEVELOP-10034
 * 创建说明：实现UserDetails接口
 */
public class User implements UserDetails {

    /**
     * 用户id,用于session控制
     */
    private int id;

    /**
     * 用户所属的域，例如医院等
     */
    private String domain;
    /**
     * 用户名
     * */
    private String username;
    /**
     * 密码，序列化时忽略
     * */
    @JsonIgnore
    private String password;
    /**
     * 权限
     * */
    private Collection<UserAuthority> authorities;
    /**
     * 账号没有过期
     * */
    private boolean accountNonExpired = true;
    /**
     * 账号没有被锁定
     * */
    private boolean accountNonLocked =true;
    /**
     * 凭证没有过期
     * */
    private boolean credentialsNonExpired = true;
    /**
     * 用户激活状态
     * */
    private boolean enabled = true;

    /**
     * 日期，用于判断是否过期的凭证
     * */
    private Date date;


    public User(){

    }

    public User(String username, String password, Collection<? extends GrantedAuthority> authorities, Date date){
        this.username = username;
        this.password = password;
        this.authorities = authorities.stream().map(authority -> new UserAuthority(authority.getAuthority())).collect(Collectors.toList());
        this.date = date;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(Collection<UserAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
