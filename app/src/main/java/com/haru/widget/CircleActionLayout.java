package com.haru.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.haru.sora.harucampus.R;
import com.haru.tools.HLog;
import com.haru.tools.MathTool;
import com.haru.tools.Res;
import com.haru.tools.ViewTool;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 星野悠 on 2017/1/4.
 */

public class CircleActionLayout extends FrameLayout {

    //中心视图
    private View centerView;

    //其他视图分布的范围
    private float range = 0;

    //所有actions视图
    private List<CircleAction> actions = new ArrayList<>();

    //动作控制器
    private CircleActionsController controller;

    private long animaDuration;

    private GestureDetector gestureDetector;

    private HGestureListener hGestureListener;

    public CircleActionLayout(Context context) {
        super(context);
        this.init();
    }

    public CircleActionLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public CircleActionLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CircleActionLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.init();
    }

    //触摸事件开始的位置
    private float[] startLoc;

    private double startDegree;

    //所有构造器都会调用初始化方法
    private void init() {
        //初始隐藏
        this.setVisibility(INVISIBLE);
        this.range = ViewTool.dip2px(this.getContext(), 60) ;

        this.hGestureListener = new HGestureListener();
        this.gestureDetector = new GestureDetector(this.getContext(), this.hGestureListener);

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetector.onTouchEvent(motionEvent);
                float[] originLoc = ViewTool.getViewCenter(centerView);
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (startLoc == null) {
                            startLoc = new float[2];
                        }
                        startLoc[0] = motionEvent.getRawX();
                        startLoc[1] = motionEvent.getRawY();
                        startDegree = MathTool.getAngle(originLoc[0], originLoc[1], startLoc[0], startLoc[1]);
                        for (CircleAction action : actions) {
                            action.stopAutoAnimator();
                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        double curDegree = MathTool.getAngle(originLoc[0], originLoc[1], motionEvent.getRawX(), motionEvent.getRawY());
                        for (CircleAction action : actions) {
                            action.roateByCenter((float) (startDegree - curDegree));
                        }
                        startDegree = curDegree;
                        return true;
                    case MotionEvent.ACTION_UP:
//                    double curUpDegree = MathTool.getAngle(originLoc[0], originLoc[1], motionEvent.getRawX(), motionEvent.getRawY()) ;
//                    HLog.e("TAG", "upDegree", curUpDegree, "preDegree", startDegree) ;
                        float mx = motionEvent.getRawX() ;
                        float my = motionEvent.getRawY() ;
                        float v ;
                        float vx = hGestureListener.getVx();
                        float vy = hGestureListener.getVy();
                        if (Math.abs(vx) > Math.abs(vy)) {
                            v = vx;
                            if(my < originLoc[1]){
                                v = -v ;
                            }
                        } else {
                            v = vy;
                            if(mx > originLoc[0]){
                                v = -v ;
                            }
                        }
                        if (v != 0) {
                            for (CircleAction action : actions) {
                                action.roateByCenterWithStartVel(v);
                            }
                        }

                        return true;
                }
                return true;
            }
        });

    }

    private View converView ;
    //展示
    public boolean show() {
        if(!canExecuteAnime){
            return false;
        }
        this.setVisibility(VISIBLE);
        if(converView == null){
            converView = this.findViewById(R.id.coverView) ;
            if(converView == null){
                throw  new RuntimeException("maybe you forget to set a view by id coverView");
            }
        }

        converView.setVisibility(VISIBLE);
        ObjectAnimator coverAnime = ObjectAnimator.ofFloat(converView, "alpha", 0f, 0.6f) ;

        if(actions != null){
            for (CircleAction action : actions) {
                action.getAction().setVisibility(INVISIBLE);
            }
        }
        if (this.controller == null) {
            this.controller = new CircleActionsController();
        }
        Animator animator = this.controller.getApperanceAnimator(this.centerView, this.actions);
        animator.setDuration(animaDuration);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                canExecuteAnime = true ;
            }
        });
        canExecuteAnime = false ;
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator).with(coverAnime);
        animatorSet.start();
        return true ;
    }

    private boolean canExecuteAnime = true ;

    //消失时动画
    public boolean dismiss(){
        if(!canExecuteAnime){
            return false;
        }
        ObjectAnimator coverAnime = ObjectAnimator.ofFloat(converView, "alpha", 0.6f, 0f) ;
        coverAnime.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                converView.setVisibility(GONE);
            }
        });


        Animator animator= this.controller.getDismissAnimator(this.centerView, this.actions);
        animator.setDuration(animaDuration) ;
        animator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                //动画结束隐藏布局
                CircleActionLayout.this.setVisibility(INVISIBLE);
                canExecuteAnime = true ;
            }
        });
        canExecuteAnime = false ;

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator).with(coverAnime);
        animatorSet.start();
        return true ;
    }

    //增加一个动作
    public void addAction(CircleAction action) {
        if (this.actions == null) {
            this.actions = new ArrayList<>();
        }
        if (action != null) {
            this.actions.add(action);
        }
    }

    //增加一个视图，从视图转为action对象
    public void addViewAsAction(View view) {
        if (view != null) {
            HLog.e("TAG", this.range);
            this.addAction(new CircleAction(view, this.centerView, this.range));
        }
    }

    public void removeView(View view) {
        for (int index = 0; index < actions.size(); index++) {
            CircleAction action = actions.get(index);
            if (action.getAction() == view) {
                this.removeAction(index, action);
                break;
            }
        }
    }

    public void removeAction(int index, CircleAction action) {
        if (this.actions != null) {
            this.actions.remove(index);
        }
        if (this.controller != null) {
            this.controller.removeAnimator(action);
        }
    }

    public void removeAction(CircleAction action) {
        if (this.actions != null) {
            this.actions.remove(action);
        }

        if (this.controller != null) {
            this.controller.removeAnimator(action);
        }
    }

    public void setCenterView(View centerView) {
        this.centerView = centerView;
    }

    public long getAnimaDuration() {
        return animaDuration;
    }

    public void setAnimaDuration(long animaDuration) {
        this.animaDuration = animaDuration;
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }
}
