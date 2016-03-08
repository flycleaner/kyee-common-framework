package com.kyee.framework.demo.baidupush;

import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;
import com.baidu.yun.push.model.PushMsgToSingleDeviceResponse;
import com.kyee.framework.core.annotation.EnableBaiduPush;
import com.kyee.framework.core.annotation.KyeeApplication;
import com.kyee.framework.core.push.BaiduPushHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;


/**
 * @author 程峰
 * 创建时间:15/9/11 下午1:09
 * 任务号:MOBILEDEVELOP-10343
 * 创建说明:测试百度云推送, apk安装包在resources目录下
 */

@KyeeApplication
@EnableBaiduPush
public class BaiduPushDemoApplication implements CommandLineRunner{

    @Autowired
    private BaiduPushHelper helper;

    public static void main(String[] args) {
        SpringApplication.run(BaiduPushDemoApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        PushMsgToSingleDeviceRequest request = new PushMsgToSingleDeviceRequest();
        request.addMessageType(1)
                .addChannelId("3787585171950995168")
                .addMessage("{\"title\":\"TEST\",\"description\":\"Hello Baidu push!\"}")
                .addDeviceType(3)
        ;
        PushMsgToSingleDeviceResponse response = helper.getClient().pushMsgToSingleDevice(request);
        System.out.println("消息id:"+response.getMsgId()+" 发送时间"+response.getSendTime());
    }
}
