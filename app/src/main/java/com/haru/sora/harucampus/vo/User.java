package com.haru.sora.harucampus.vo;

import java.util.List;

/**
 * Created by 星野悠 on 2017/1/9.
 */

public class User {
    private String id ;

    private String password ;

    private String loginModel ;

    private String loginUrl ;

    private String cookies ;

    private List<UserInfo> userInfos ;

    User(){

    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginModel() {
        return loginModel;
    }

    public void setLoginModel(String loginModel) {
        this.loginModel = loginModel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserInfo> getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(List<UserInfo> userInfos) {
        this.userInfos = userInfos;
    }
}
