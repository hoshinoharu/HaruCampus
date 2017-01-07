package com.haru.tools;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

/**
 * Created by 星野悠 on 2017/1/4.
 */

public class HLog  {
    private static final  boolean canShow = true ;
    public static void e(String tag, Object... object){
        if(canShow){
            Log.e(tag, Arrays.toString(object)) ;
        }
    }

    public static void d(String tag, Object... object){
        if(canShow){
        Log.d(tag, Arrays.toString(object)) ;
        }
    }

    public static void i(String tag, Object... object){
        if(canShow) {
            Log.i(tag, Arrays.toString(object));
        }
    }

    public static void w(String tag, Object... object){
        if(canShow) {
            Log.w(tag, Arrays.toString(object));
        }
    }

    public static void wtf(String tag, Object... object){
        if(canShow) {
            Log.wtf(tag, Arrays.toString(object));
        }
    }

    public static void ex(String tag, Throwable throwable){
        if(canShow){
            StringWriter sw = new StringWriter() ;
            throwable.printStackTrace(new PrintWriter(sw));
            Log.e(tag, sw.toString()) ;
        }
    }
}
