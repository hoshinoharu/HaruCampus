package com.haru.tools;

import java.io.InputStream;
import java.util.Scanner;

import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by 星野悠 on 2017/1/6.
 */

public class OKHttpTool {

    public static Headers emptyHeaders = new Headers.Builder().build() ;

    public static void sendOkHttpRequest(String url, Headers headers, Callback callback){
        OkHttpClient client = new OkHttpClient() ;
        Request.Builder builder = new Request.Builder().url(url) ;
        if(headers != null){
            builder.headers(headers) ;
        }
        client.newCall(builder.build()).enqueue(callback);
    }
    public static void sendOkHttpRequest(String url, Callback callback){
        sendOkHttpRequest(url, emptyHeaders, callback);
    }

    public static String getHtml(InputStream inputStream){
        Scanner scanner = new Scanner(inputStream) ;
        StringBuffer buffer = new StringBuffer();
        while(scanner.hasNextLine()){
            buffer.append(scanner.nextLine()) ;
        }
        scanner.close();
        return buffer.toString();
    }
}
