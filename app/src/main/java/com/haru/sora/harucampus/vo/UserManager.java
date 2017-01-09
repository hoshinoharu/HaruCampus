package com.haru.sora.harucampus.vo;

/**
 * Created by 星野悠 on 2017/1/9.
 */

public class UserManager {
    private static  User user = null ;
    public static User getUser(){
        synchronized (UserManager.class){
            if(user == null){
                user = new User() ;
            }
        }
        return user ;
    }

    public static void cleanUserInfo(){
        user = new User() ;
    }
}
