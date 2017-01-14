package com.haru.sora.harucampus.service;

import com.haru.sora.harucampus.HaruBase;
import com.haru.sora.harucampus.R;
import com.haru.sora.harucampus.vo.User;
import com.haru.sora.harucampus.vo.UserInfo;
import com.haru.tools.OKHttpTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        OKHttpTool.sendOkHttpRequest(queryUserInfoAction, Headers.of("Cookie", "JSESSIONID=07A51C3556C93D0F97C6A742C5ABA265"), callback);
    }

}
