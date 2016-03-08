package com.kyee.framework.demo.singledb.service;

import com.kyee.framework.demo.singledb.dao.mapper.IDemoMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 梁志艳
 * 创建时间：2015-09-08 下午2:25
 * 任务号：MOBILEDEVELOP-10307
 * 创建原因：Demo Service
 * 说明：请确保DemoService的logger输出DEBUG级别信息
 */
@Service
public class DemoService {
    private static final Log logger = LogFactory.getLog(DemoService.class);
    @Autowired
    private IDemoMapper demoMapper;

    /**
     * 一个测试的方法如果成功会输入1
     */
    public void doTest(){
        logger.debug(demoMapper.test());
    }
}
