package com.haru.sora.harucampus;

import android.app.Application;
import android.content.Context;

public  class HaruBase extends Application {
    private static Context context ;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext() ;
    }

    public static Context context(){
        return context ;
    }
}
