package com.haru.sora.harucampus.service;

import com.haru.sora.harucampus.HaruBase;
import com.haru.sora.harucampus.R;
import com.haru.sora.harucampus.vo.User;
import com.haru.tools.OKHttpTool;

import okhttp3.Callback;
import okhttp3.Headers;

/**
 * Created by 星野悠 on 2017/1/9.
 */

public class UserService {


    private static UserService service ;

    UserService(){}

    public static UserService getUserService(){
        synchronized (UserService.class){
            if(service == null){
                service = new UserService() ;
            }
        }
        return service ;
    }

    private String queryUserInfoAction = HaruBase.context().getString(R.string.query_userInfo_action) ;
    public void queryInfo(User user, Callback callback){
        OKHttpTool.sendOkHttpRequest(queryUserInfoAction, Headers.of("Cookie", "JSESSIONID=C204D1A3EAAF9651FC3A4D0D1F5F16C0"), callback);
    }



}
