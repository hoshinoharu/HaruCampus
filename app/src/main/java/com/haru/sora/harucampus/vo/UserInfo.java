package com.haru.sora.harucampus.vo;

/**
 * Created by 星野悠 on 2017/1/9.
 */

public class UserInfo {
    public String name ;
    public String value ;

    public UserInfo() {
    }
    public UserInfo(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
