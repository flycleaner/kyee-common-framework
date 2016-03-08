package com.kyee.framework.core.web.security.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author 程峰
 * 创建时间:15/9/8 上午11:17
 * 任务号:MOBILEDEVELOP-10293
 * 创建说明:session管理策略
 */
@Component
public class SessionPolicy {
    private static final int DEFAULT_SESSION_COUNT = 1;
    private static final SessionControl DEFAULT_SESSION_CONTROL = SessionControl.LatterPrior;

    @Autowired
    private Environment environment;

    /**
     * 同一个账号最多允许同时登陆数
     */
    @Value("${kyee.security.session.maxNum:1}")
    private int sessionCount=DEFAULT_SESSION_COUNT;
    /**
     * 超过最大登陆数解决策略: 默认挤掉最前面的
     */
    private SessionControl sessionControl = DEFAULT_SESSION_CONTROL;

    public int getSessionCount() {
        return sessionCount;
    }

    public void setSessionCount(int sessionCount) {
        this.sessionCount = sessionCount;
    }

    public SessionControl getSessionControl() {
        return sessionControl;
    }

    public void setSessionControl(SessionControl sessionControl) {
        this.sessionControl = sessionControl;
    }


    @PostConstruct
    public void init(){
        Integer prior = environment.getProperty("kyee.security.session.prior",Integer.class);
        if(prior==null){
            sessionControl = SessionControl.LatterPrior;
            return;
        }
        switch (prior){
            case 0:
                sessionControl = SessionControl.FormerPrior;
                break;
            case 1:
                sessionControl = SessionControl.LatterPrior;
                break;
        }
    }

}
