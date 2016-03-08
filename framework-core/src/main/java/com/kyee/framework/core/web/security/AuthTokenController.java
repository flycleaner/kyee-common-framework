package com.kyee.framework.core.web.security;

import com.kyee.framework.core.web.result.Result;
import com.kyee.framework.core.web.result.ResultHelper;
import com.kyee.framework.core.web.security.config.KyeeFrameworkSecurityConfig;
import com.kyee.framework.core.web.security.filter.LoginFilter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 程峰
 * 创建时间:15/9/6 上午10:43
 * 任务号:MOBILEDEVELOP-10281
 * 创建说明: 获取中间件token
 */

/**
 * 修改人：程峰
 * 修改时间： 15/9/22 下午4：27
 * 修改原因：修改认证接口apidoc
 */

@RestController
public class AuthTokenController {

    /**
     * @api {get} /authToken 获取中间件token
     * @apiName authTokenController
     * @apiGroup authToken
     * @apiVersion 0.1.0
     *
     * @apiParam {String} credentials 凭证,格式:username/password@domain
     *
     * @apiSuccess (返回值) {Boolean} success 是否成功
     * @apiSuccess (返回值) {String} value 返回的token内容
     * @apiSuccess (返回值) {String} state 状态
     * @apiSuccess (返回值) {String} message 失败备注消息
     * @apiSuccessExample {json} 成功返回:
     *  {
     *      "success" : true,
     *      "value" : "ZXlKMWMyVnlibUZ0WlNJNkltdDVaV1VpTENKaGRYUm9iM0pwZEdsbGN5STZXM3NpWVhWM",
     *      "state" : 0,
     *      "message" : ""
     *  }
     * @apiErrorExample {json} 失败返回:
     *  {
     *      "success":false,
     *      "value":null,
     *      "state":1,
     *      "message":"用户名或密码错误"
     *  }
     */

    /**
     * 这个controller被security的 filter拦截
     * 配置filter
     * @see KyeeFrameworkSecurityConfig configure(HttpSecurity http)
     * filter实现
     * @see LoginFilter
     */
    @RequestMapping(value = "/authToken",method = RequestMethod.GET)
    public void  getAuthToken(String username,String password){
    }

    /**
     *
     * @api {get} /authToken/logout 登出
     * @apiName logoutController
     * @apiGroup authToken
     * @apiVersion 0.1.0
     *
     * @apiParam {String} token 访问token
     *
     * @apiSuccess (返回值) {Boolean} success 是否成功
     * @apiSuccess (返回值) {String} value 登出成功
     * @apiSuccessExample {json} 成功返回:
     *  {
     *      "success" : true,
     *      "value" : "登出成功"
     *  }
     * @apiErrorExample {json} 失败返回:
     *  {
     *      "success":false,
     *      "value":null,
     *      "state":4,
     *      "message":"token无效"
     *  }
     */
    @RequestMapping(value = "authToken/logout")
    public Result logout(){
        return ResultHelper.successResult("登出成功").build();
    }

}
