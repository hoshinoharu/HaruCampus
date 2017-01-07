package com.haru.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.haru.tools.MathTool;
import com.haru.tools.ViewTool;

/**
 * Created by 星野悠 on 2017/1/4.
 */

public class CircleAction {

    private View centerView;

    private float[] center;

    private View action;

    private float range = 100;

    private float degree;

    private Animator autoAnimator;

    public CircleAction(View action, View centerView, float range) {
        this.action = action;
        this.centerView = centerView;
        this.range = range;
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public float getDegree() {
        return degree;
    }

    public void setDegree(float degree) {
        this.degree = degree;
    }

    public View getAction() {
        return action;
    }

    public void setAction(View action) {
        this.action = action;
    }

    public float[] getCenter() {
        return ViewTool.getViewCenter(centerView);
    }

    public void setCenter(float[] center) {
        this.center = center;
    }

    public View getCenterView() {
        return centerView;
    }

    public void setCenterView(View centerView) {
        this.centerView = centerView;
    }

    public void roateByCenter(float degree) {
        this.center = ViewTool.getViewCenter(this.centerView);
        float[] tloc = ViewTool.getViewCenter(this.action);
        float[] loc = MathTool.roate(tloc[0], tloc[1], center[0], center[1], degree);
        ViewTool.fitViewByCenter(this.action, loc);
    }

    private ValueAnimator.AnimatorUpdateListener animaListener;
    private float startDegree = 0;


    public void roateByCenterWithStartVel(float vx) {
        startDegree = 0;
        if (this.animaListener == null) {
            this.animaListener = new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float curDegree = (float) valueAnimator.getAnimatedValue();
                    roateByCenter(curDegree-startDegree);
                    startDegree = curDegree;
                }
            } ;
        }
        //获取中心点
        float[] ps = this.getCenter();

        //获取action的中心点
        float[] ms = ViewTool.getViewCenter(this.action);

        //获取当前角度
        float curAngle = (float) MathTool.getAngle(ps[0], ps[1], ms[0], ms[1]);

        //从当前角度旋转到目标角度
        ObjectAnimator animator = ObjectAnimator.ofFloat(this.action, "haru", 0, vx / 20).setDuration(1000);
        animator.addUpdateListener(this.animaListener);
        autoAnimator = animator;

        if (this.autoAnimator != null) {
            this.autoAnimator.setInterpolator(new DecelerateInterpolator());
            this.autoAnimator.start();
        }
    }

    //停止动画
    public void stopAutoAnimator() {
        if (this.autoAnimator != null) {
            if (this.autoAnimator.isRunning()) {
                this.autoAnimator.cancel();
                this.startDegree = 0;
            }
        }
    }


}
