package com.kyee.framework.demo.mutidb.jta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 程峰
 *         创建时间:15/8/18 下午3:24
 *         任务号:
 *         创建说明:
 */
@RestController
public class Controller {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/search")
    public Map test(){
        return userService.getUser();
    }

    @RequestMapping(value = "/insert")
    public Boolean insert(@RequestParam("hasException") Boolean hasException) throws Exception {
        return  userService.insert(hasException);
    }
}
