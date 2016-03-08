package com.kyee.framework.core.push;

import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author 程峰
 * 创建时间:15/9/14 下午1:59
 * 任务号:MOBILEDEVELOP-10343
 * 创建说明:用以初始化百度推送所需的类
 */
@Component
public class BaiduPushHelper {

    /**
     * 百度云推送apiKey
     */
    @Value("${baiduPush.apiKey}")
    private String apiKey;
    /**
     * 百度云推送secretKey
     */
    @Value("${baiduPush.secretKey}")
    private String secretKey;

    private BaiduPushClient client;

    @PostConstruct
    private void init(){
        PushKeyPair pair = new PushKeyPair(apiKey,secretKey);
        client = new BaiduPushClient(pair, BaiduPushConstants.CHANNEL_REST_URL);
    }

    /**
     * 获取推送服务client
     * @return 推送服务
     */
    public BaiduPushClient getClient() {
        return client;
    }

}
