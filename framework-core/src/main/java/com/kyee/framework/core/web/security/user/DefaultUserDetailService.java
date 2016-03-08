package com.kyee.framework.core.web.security.user;

import com.kyee.framework.core.web.security.utils.PharseUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

/**
 * @author 程峰
 * 创建时间:15/9/22 下午1:10
 * 任务号:MOBILEDEVELOP-10399
 * 创建说明: 默认获取用户信息服务，如果用户自己实现并注册Bean，则使用用户的服务
 */
public class DefaultUserDetailService extends JdbcDaoImpl {

    /**
     * 根据credentials获取用户信息
     * @param credentials 获取token时请求中的credentials: username/password@domain
     * @return UserDetails
     * @throws UsernameNotFoundException 无此用户抛出此异常
     */
    @Override
    public UserDetails loadUserByUsername(String credentials) throws UsernameNotFoundException {
        String[] userInfo = PharseUtil.pharseUserInfo(credentials);
        String realUsername;
        if(userInfo==null){
            return super.loadUserByUsername(credentials);
        }else {
            realUsername = userInfo[0];
        }
        return super.loadUserByUsername(realUsername);
    }
}
