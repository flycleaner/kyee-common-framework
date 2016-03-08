package com.kyee.framework.core.annotation;

import com.kyee.framework.core.web.cors.CORSFilter;
import com.kyee.framework.core.web.cors.CORSProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author 程峰
 * 创建时间:15/9/9 下午3:02
 * 任务号:MOBILEDEVELOP-10293
 * 创建说明:开启cors功能
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({CORSFilter.class, CORSProperties.class})
public @interface EnableCORS {
}
