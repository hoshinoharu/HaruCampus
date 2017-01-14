package com.haru.widget;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import com.haru.tools.HLog;

/**
 * Created by 星野悠 on 2017/1/11.
 */

public class HScrollView extends ScrollView {
    public interface BeforeTouchListener{
        void beforeTouchListener(MotionEvent ev) ;
    }

    private BeforeTouchListener beforeTouchListener  ;

    public HScrollView(Context context) {
        super(context);
    }

    public HScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public BeforeTouchListener getBeforeTouchListener() {
        return beforeTouchListener;
    }

    public void setBeforeTouchListener(BeforeTouchListener beforeTouchListener) {
        this.beforeTouchListener = beforeTouchListener;
    }

    private boolean canSrcoll = true;


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(beforeTouchListener != null){
            beforeTouchListener.beforeTouchListener(ev);
        }
        if(canSrcoll) {
             return super.onTouchEvent(ev);
        }
        return false ;
    }

    public boolean isCanSrcoll() {
        return canSrcoll;
    }

    public void setCanSrcoll(boolean canSrcoll) {
        this.canSrcoll = canSrcoll;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
