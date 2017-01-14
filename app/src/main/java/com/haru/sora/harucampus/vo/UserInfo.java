package com.haru.sora.harucampus.vo;

import java.util.Date;

/**
 * Created by 星野悠 on 2017/1/9.
 */

public class UserInfo {
    public String name ;
    public String sex ;
    public String status ;
    public String state ;
    public Double balance ;
    public Date accountOpenDate ;
    public String nation ;
    public Date expiryDate ;
    public String identity ;
    public String bacnCorsNum ;
    public String bankCardNum ;
    public String stdNum ;
    public String idNum ;
    public String department ;
    public String phoneNum ;

    @Override
    public String toString() {
        return "UserInfo{" +
                "accountOpenDate=" + accountOpenDate +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", status='" + status + '\'' +
                ", state='" + state + '\'' +
                ", balance=" + balance +
                ", nation='" + nation + '\'' +
                ", expiryDate=" + expiryDate +
                ", identity='" + identity + '\'' +
                ", bacnCorsNum='" + bacnCorsNum + '\'' +
                ", bankCardNum='" + bankCardNum + '\'' +
                ", stdNum='" + stdNum + '\'' +
                ", idNum='" + idNum + '\'' +
                ", department='" + department + '\'' +
                ", phoneNum='" + phoneNum + '\'' +
                '}';
    }
}
