package com.kyee.framework.demo.mutidb;

import com.kyee.framework.demo.mutidb.domain.UserHospistal;
import com.kyee.framework.demo.mutidb.domain.UserInfo;
import com.kyee.framework.demo.mutidb.repository.source1.IUserInfoDao;
import com.kyee.framework.demo.mutidb.repository.source2.IUserInfoDao2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 程峰
 *         创建时间:15/8/18 下午3:25
 *         任务号:
 *         创建说明:
 */
@Service
public class UserService {
    @Autowired
    IUserInfoDao userInfoDao;
    @Autowired
    IUserInfoDao2 userInfoDao2;

    @Transactional(readOnly = true)
    public Map getUser(){
        HashMap<String ,Object> result = new HashMap<>();
        result.put("db1",userInfoDao.getOpenIdByCardNo("123"));
        result.put("db2",userInfoDao2.getUserByid("2"));
        return result;
    }
    @Transactional(value = "localManager" )
    public Boolean insert(boolean hasException) {
        int result = 0;
//        UserInfo userInfo = new UserInfo();
//        userInfo.setMedicalCardNo("medicalTest");
//        userInfo.setOpenId("openIdTest");
//        result = result + userInfoDao.insert(userInfo);
        UserHospistal userHospistal = new UserHospistal();
        userHospistal.setOpenId("medicalTest");
        userHospistal.setHospitalId(100);
        result = result + userInfoDao2.insert(userHospistal);
        if(hasException){
            throw new RuntimeException();
        }
        return result==1;
    }
}

