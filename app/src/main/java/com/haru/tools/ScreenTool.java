package com.haru.tools;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by 星野悠 on 2017/1/4.
 */

public class ScreenTool {
    private static DisplayMetrics metrics = null ;
    public static DisplayMetrics getSreenSize(Context context){
        if(metrics == null) {
            metrics = new DisplayMetrics() ;
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(metrics);
        }
        return metrics ;
    }
}
