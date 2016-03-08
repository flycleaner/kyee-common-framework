package com.kyee.framework.demo.security;

import com.kyee.framework.core.web.security.config.AbstractSecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * @author 程峰
 * 创建时间:15/9/7 下午4:00
 * 任务号:MOBILEDEVELOP-10293
 * 创建说明:security配置示例
 */
@Configuration
@EnableScheduling
public class SecurityConfig extends AbstractSecurityConfig {

    @Override
    protected void configureHttpProxy(HttpSecurity http) throws Exception {
        http.
                authorizeRequests()
                .antMatchers("/test").permitAll()
                .anyRequest().authenticated()
        ;
    }

    /**
     * 这里配置了获取用户服务则会覆盖默认配置
     * @return UserDetailsService
     */
    //@Bean
//    public UserDetailsService userDetailsService(){
//        return new UserDetailsService() {
//            @Override
//            public User loadUserByUsername(String username) throws UsernameNotFoundException {
//                ArrayList<UserAuthority> userAuthorities = new ArrayList<>();
//                userAuthorities.add(new UserAuthority("ROLE_ADMIN"));
//                return new User("kyee","kyeepass",userAuthorities,new Date());
//            }
//        };
//    }
}
