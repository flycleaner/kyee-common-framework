package com.kyee.framework.demo.mutidb.repository.source2;

import com.kyee.framework.demo.mutidb.domain.UserHospistal;

/**
 * @author 程峰
 *         创建时间:15/8/18 下午3:01
 *         任务号:
 *         创建说明:
 */
public interface IUserInfoDao2 {

    UserHospistal getUserByid(String id);

    Integer insert(UserHospistal userHospistal);

}
