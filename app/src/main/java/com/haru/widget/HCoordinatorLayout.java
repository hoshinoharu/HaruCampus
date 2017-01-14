package com.haru.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;

import com.haru.tools.HLog;
import com.haru.tools.ViewTool;

/**
 * Created by 星野悠 on 2017/1/11.
 */

public class HCoordinatorLayout extends CoordinatorLayout {
    private HAppBarLayout hAppBarLayout;
    private HScrollView hScrollView;

    public HCoordinatorLayout(Context context) {
        super(context);
    }

    public HCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (this.hAppBarLayout == null) {
            this.hAppBarLayout = (HAppBarLayout) this.getChildAt(0);
            this.hScrollView = (HScrollView) this.getChildAt(1);
            ViewTreeObserver vto = hAppBarLayout.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    hAppBarLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    hScrollView.setY(hAppBarLayout.getHeight()+hAppBarLayout.getY());

                }
            });
            this.hAppBarLayout.addOnLayoutChangeListener(new OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    hScrollView.setY(hAppBarLayout.getHeight()+hAppBarLayout.getY());
                }
            });
            this.hScrollView.setBeforeTouchListener(new HScrollView.BeforeTouchListener() {
                @Override
                public void beforeTouchListener(MotionEvent event) {
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            startY = event.getRawY() ;
                            hScrollView.setCanSrcoll(true);
                            break;
                        case MotionEvent.ACTION_MOVE:
                            curY = event.getRawY() ;
                            if(curY < startY){
                                if(hAppBarLayout.getY() >= -hAppBarLayout.getHeight()){
                                    hScrollView.setCanSrcoll(false);
                                    hAppBarLayout.setY(hAppBarLayout.getY()+curY-startY);
                                    hScrollView.setY(hAppBarLayout.getY()+hAppBarLayout.getHeight()+1);
                                }else{
                                    hScrollView.setCanSrcoll(true);
                                }
                            }
                            startY = curY ;
                            break ;

                    }
                }

                private float startY ;
                private float curY ;

            });
        }

    }

    private float startY;
    private float originY;
    private int originHeight = -1;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(originHeight == -1){
            originHeight = hAppBarLayout.getHeight() ;
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:

                this.startY = ev.getRawY();
                this.originY = this.startY;
                break;
            case MotionEvent.ACTION_MOVE:
                float curY = ev.getRawY();
                float offset = curY - startY ;
                if (startY < curY) {
                    this.adjustLocation(ev, offset);
                }else{
                    if(this.hAppBarLayout.getHeight() > originHeight && offset < 0){
                        ViewGroup.LayoutParams layoutParams = this.hAppBarLayout.getLayoutParams();
                        layoutParams.height  += offset ;
                        this.hAppBarLayout.setLayoutParams(layoutParams);
                    }
                }
                startY = ev.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                this.rollback();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
    private float maxOffset = ViewTool.dip2px(120) ;
    private boolean canRollback = false;


    public void adjustLocation(MotionEvent ev, float offset) {
            ViewGroup.LayoutParams layoutParams = this.hAppBarLayout.getLayoutParams();
        if (this.hAppBarLayout.getY() >= 0 && this.hScrollView.getScrollY() == 0) {
            canRollback = true ;
            this.hAppBarLayout.setY(0);
                layoutParams.height += (offset * ((this.originHeight + maxOffset) / this.hAppBarLayout.getHeight() / 3));
                if (layoutParams.height > this.originHeight + maxOffset) {
                    layoutParams.height = (int) (this.originHeight + maxOffset);
                }
                this.hAppBarLayout.setLayoutParams(layoutParams);
        }else if(this.hAppBarLayout.getY() < 0){
                offset = this.hAppBarLayout.getY() + offset ;
                offset = offset>=0?0:offset;
                this.hAppBarLayout.setY(offset);
                hScrollView.setY(hAppBarLayout.getY()+hAppBarLayout.getHeight()+1);
        }
    }

    private ValueAnimator rollbackAnime;

    public void rollback() {
        if(!canRollback || this.hAppBarLayout.getHeight() <= this.originHeight){
            return;
        }
        canRollback = false ;
        this.hScrollView.setCanSrcoll(false);
        final ViewGroup.LayoutParams layoutParams = hAppBarLayout.getLayoutParams();
        if (rollbackAnime == null) {
            this.rollbackAnime = ValueAnimator.ofInt(this.hAppBarLayout.getHeight(), this.originHeight);
            this.rollbackAnime.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int height = (int) animation.getAnimatedValue() ;
                    layoutParams.height = height;
                    hAppBarLayout.setLayoutParams(layoutParams);
                }
            });
            this.rollbackAnime.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    hScrollView.setCanSrcoll(true);
                }
            });
            this.rollbackAnime.setInterpolator(new DecelerateInterpolator());
            this.rollbackAnime.setDuration(300);
        }
        this.rollbackAnime.cancel() ;
            this.rollbackAnime.setIntValues(this.hAppBarLayout.getHeight(), this.originHeight);
            this.rollbackAnime.start();
    }
}
