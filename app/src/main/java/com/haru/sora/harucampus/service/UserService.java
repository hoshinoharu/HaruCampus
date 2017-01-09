package com.haru.sora.harucampus.service;

import com.haru.sora.harucampus.HaruBase;
import com.haru.sora.harucampus.R;
import com.haru.sora.harucampus.vo.User;
import com.haru.sora.harucampus.vo.UserInfo;
import com.haru.tools.HLog;
import com.haru.tools.OKHttpTool;

import java.util.ArrayList;
import java.util.List;
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
        OKHttpTool.sendOkHttpRequest(queryUserInfoAction, Headers.of("Cookie", "JSESSIONID=C204D1A3EAAF9651FC3A4D0D1F5F16C0"), callback);
    }

    public void fillInfoFromHtml(User user, String html){
        List<UserInfo> userInfos = new ArrayList<>() ;
        Pattern pattern = Pattern.compile("<label.*?>(.*?)</label>") ;
        Matcher ma = pattern.matcher(html) ;
        while(ma.find()){
            String pro = ma.group(1).trim() ;
            String val = null ;
            if(ma.find() ){
                val = ma.group(1).trim() ;
                userInfos.add(new UserInfo(pro, val.split("&nbsp")[0]));
            }
        }
        user.setUserInfos(userInfos);
    }



}
