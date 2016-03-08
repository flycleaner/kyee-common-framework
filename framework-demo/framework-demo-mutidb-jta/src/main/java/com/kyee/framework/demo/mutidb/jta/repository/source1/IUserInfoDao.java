package com.kyee.framework.demo.mutidb.jta.repository.source1;



import com.kyee.framework.demo.mutidb.jta.domain.UserInfo;

import java.util.List;

/**
 * @author 程峰
 *         创建时间:15/8/18 下午3:01
 *         任务号:
 *         创建说明:
 */
public interface IUserInfoDao {

    List<UserInfo> getOpenIdByCardNo(String medicalCardNumber);

    Integer insert(UserInfo userInfo);
}
