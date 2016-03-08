package com.kyee.framework.demo.mutidb.domain;

/**
 * @author 夏之阳
 * 创建时间：2015-07-27 13:30
 * 任务号：MOBILEDEVELOP-9752
 * 创建说明：用户信息类（对应t_user表）
 */

public class UserInfo {
    /**
     * 医保卡号
     */
    private String MedicalCardNo;

    /**
     * 用户标识openId
     */
    private String openId;

    /**
     * 用状态值
     */
    private int status;

    public String getMedicalCardNo() {
        return MedicalCardNo;
    }

    public void setMedicalCardNo(String MedicalCardNo) {
        this.MedicalCardNo = MedicalCardNo;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}