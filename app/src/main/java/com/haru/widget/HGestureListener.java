package com.haru.widget;

import android.view.MotionEvent;

/**
 * Created by 星野悠 on 2017/1/5.
 */

public class HGestureListener implements android.view.GestureDetector.OnGestureListener {

    private float vx ;

    private float vy ;

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        vx = 0 ;
        vy = 0 ;
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        this.vx = v ;
        this.vy = v1 ;
        return false;
    }


    public float getVx() {
        return vx;
    }

    public float getVy() {
        return vy;
    }
}

