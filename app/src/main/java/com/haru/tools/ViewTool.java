package com.haru.tools;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.haru.sora.harucampus.HaruBase;

/**
 * Created by 星野悠 on 2017/1/4.
 */

public class ViewTool {
    public static float[] getViewCenter(View view){
        float[] loc = new float[]{view.getX()+view.getWidth()/2, view.getY()+view.getHeight()/2};
        return loc ;
    }

    public static float[] fitViewByCenter(View view, float[] center){
        view.setX(center[0]-view.getWidth()/2);
        view.setY(center[1]-view.getHeight()/2);
        float[] newLoc = new float[]{view.getX(), view.getY()};
        return newLoc ;
    }

    public static boolean checkInput(TextView textView){
        if(textView != null){
            return checkInput(textView.getText()) ;
        }
        return false;
    }

    public static boolean checkInput(CharSequence input){
        if(input != null && ! "".equals(input)){
            return true ;
        }
        return false ;
    }
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public static int dip2px(float dpValue) {
        final float scale = HaruBase.context().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}
