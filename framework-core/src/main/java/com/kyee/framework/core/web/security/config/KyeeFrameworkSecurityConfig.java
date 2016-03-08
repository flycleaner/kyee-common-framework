package com.kyee.framework.core.web.security.config;


import com.kyee.framework.core.web.security.filter.AuthenticationFilter;
import com.kyee.framework.core.web.security.filter.LoginFilter;
import com.kyee.framework.core.web.security.token.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * @author 程峰
 * 创建时间：15/8/13 上午9:18
 * 任务号：MOBILEDEVELOP-10006
 * 创建说明：配置spring security
 * 提供功能:默认token认证方式,基础的security http请求拦截
 */
@Configuration
public class KyeeFrameworkSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired(required = false)
    private AbstractSecurityConfig securityConfig;

    /**
     * 配置http
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .anonymous().and()
                .servletApi().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .headers().cacheControl().and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                .antMatchers(HttpMethod.GET, "/authToken").permitAll()
                .and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and()
                //发送get 请求, url?username=xxx&password=xxx，返回token
                .addFilterBefore(new LoginFilter("/authToken", tokenAuthenticationService, authenticationManager(), authenticationFailureHandler), UsernamePasswordAuthenticationFilter.class)
                //其它请求通过token认证
                .addFilterBefore(new AuthenticationFilter(tokenAuthenticationService), UsernamePasswordAuthenticationFilter.class)
        ;
        if(securityConfig !=null) {
            securityConfig.configureHttpProxy(http);
        }
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return userDetailsService;
    }

}
