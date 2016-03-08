package com.kyee.framework.demo.result.controller;

import com.kyee.framework.core.exception.AbstractKyeeRuntimeException;
import com.kyee.framework.core.exception.KyeeRuntimeException;
import com.kyee.framework.core.web.result.Result;
import com.kyee.framework.core.web.result.ResultHelper;
import com.kyee.framework.demo.result.exception.DemoException;
import com.kyee.framework.demo.result.state.DemoState;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 梁志艳
 * 创建时间：2015-09-07 下午2:02
 * 任务号：MOBILEDEVELOP-10295
 * 创建原因：使用框架做返回结果的示例Controller
 */
@RestController
@RequestMapping("result")
public class DemoController {

    //一、成功返回部分。必须value，可选，message和state

    /**
     * 只返回value
     * @return 固定的返回对象
     */
    @RequestMapping("s1")
    public Result s1(){
        return ResultHelper.successResult("s1").build();
    }
    /**
     * 返回value和message
     * @return 固定的返回对象
     */
    @RequestMapping("s2")
    public Result s2(){
        return ResultHelper.successResult("s2").message("f2 message").build();
    }
    /**
     * 返回value和state
     * @return 固定的返回对象
     */
    @RequestMapping("s3")
    public Result s3(){
        return ResultHelper.successResult("s3").state(DemoState.S1).build();
    }
    /**
     * 返回value、message和state
     * @return 固定的返回对象
     */
    @RequestMapping("s4")
    public Result s4(){
        return ResultHelper.successResult("s4").message("s4 message").state(DemoState.S1).build();
    }

    //二、错误返回部分。必须mesage和state，value不可设置为null

    /**
     * 返回message和state
     * @return 固定的返回对象
     */
    @RequestMapping("f1")
    public Result f1(){
        return ResultHelper.failureResult("f1 message", DemoState.F1);
    }

    /**
     * 手动处理异常，返回message和state
     * @return 固定的返回对象
     */
    @RequestMapping("f2")
    public Result f2(){
        try {
            throw new KyeeRuntimeException("f2");
        }catch (KyeeRuntimeException e){
            return ResultHelper.failureResult("f2 message",e.getState());
        }
    }

    //三、异常情况返回。返回格式自动处理

    /**
     * 自动返回message和state，message是异常的message，state是异常{@link AbstractKyeeRuntimeException#getState()}的返回结果
     * @return 固定的返回对象
     */
    @RequestMapping("e1")
    public Result error1(){
        throw new DemoException("error1");
    }
    /**
     * 自动返回message和state，message是异常的message，state是5（代表未知异常）
     * @return 固定的返回对象
     */
    @RequestMapping("e2")
    public Result error2(){
        throw new RuntimeException("error2");
    }
}
