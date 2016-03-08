package com.kyee.framework.core.annotation;

import com.kyee.framework.core.push.BaiduPushHelper;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author 程峰
 * 创建时间:15/9/14 下午2:44
 * 任务号:MOBILEDEVELOP-10343
 * 创建说明:启用百度推送
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(BaiduPushHelper.class)
public @interface EnableBaiduPush {
}
