package com.haru.tools;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by 星野悠 on 2017/1/6.
 */

public class OKHttpTool {
    public static void sendOkHttpRequest(String url, Callback callback){
        OkHttpClient client = new OkHttpClient() ;
        Request request = new Request.Builder().url(url).build() ;
        client.newCall(request).enqueue(callback);
    }
}
