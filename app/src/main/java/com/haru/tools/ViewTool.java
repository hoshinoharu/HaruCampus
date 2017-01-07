package com.haru.tools;

import android.view.View;
import android.widget.TextView;

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
        return true ;
    }

}
