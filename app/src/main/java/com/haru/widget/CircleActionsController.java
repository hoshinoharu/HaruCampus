package com.haru.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;

import com.haru.tools.MathTool;
import com.haru.tools.ViewTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 星野悠 on 2017/1/4.
 */

public class CircleActionsController {
    //动画映射器
    private Map<View, Animator> appAnimeMapper;

    private Map<View, Animator> dismissAnimeMapper ;

    //获取显示时候的动画
    public Animator getApperanceAnimator(View centerView, List<CircleAction> actions) {
        centerView.setVisibility(View.VISIBLE);
        AnimatorSet animator = new AnimatorSet();
        if (actions != null) {
            if (appAnimeMapper == null) {
                appAnimeMapper = new HashMap<>();
            }
            //获取centerView 显示动画
            Animator centerAnimator = getCenterAnimator(centerView);

            //获取centerView 伴随action出现的动画
            Animator centerAnimator_x = ObjectAnimator.ofFloat(centerView, "scaleX", 1f, 0.5f, 1f);

            //所有action的动画
            List<Animator> actionAnimasList = new ArrayList<>();
            float degree = 135;
            float offset = (float) (360.0 / actions.size());
            for (int index = 0; index < actions.size(); index++) {
                CircleAction action = actions.get(index);
                degree += offset;
                action.setDegree(degree);
                Animator actionAnimator = this.getActionApperanceAnimator(action);
                actionAnimasList.add(actionAnimator);
            }
            AnimatorSet actionAnimas = new AnimatorSet();
            actionAnimas.playTogether(actionAnimasList);
            centerAnimator_x.setInterpolator(new AnticipateOvershootInterpolator());
            animator.play(centerAnimator_x).after(centerAnimator);
            animator.play(centerAnimator_x).with(actionAnimas);
        }
        return animator;
    }

    //获取中心动画
    private Animator getCenterAnimator(final View centerView) {
        Animator centerAnimator = null ;
        if (centerAnimator == null) {
            centerAnimator = new AnimatorSet();
            //透明度动画
            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(centerView, "alpha", 0f, 1f);
            //缩放动画
            ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat(centerAnimator, "scale", 0f, 1f);
            scaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float scale = (float) valueAnimator.getAnimatedValue();
                    centerView.setScaleY(scale);
                    centerView.setScaleX(scale);
                }
            });
            scaleAnimator.setInterpolator(new AnticipateOvershootInterpolator());
            ((AnimatorSet) centerAnimator).play(scaleAnimator).with(alphaAnimator);
            appAnimeMapper.put(centerView, centerAnimator);
        }
        return centerAnimator;
    }

    //获取action出现时的动画
    private Animator getActionApperanceAnimator(CircleAction action) {
        final View view = action.getAction();
        View centerView = action.getCenterView();
        Animator actionAnimator = appAnimeMapper.get(view);
        if (actionAnimator == null) {
            actionAnimator = new AnimatorSet();
            actionAnimator.setInterpolator(new AnticipateOvershootInterpolator());
        }
        float[] origin = action.getCenter() ;

        //从centerView运动到目标位置
        float[] loc = MathTool.roate(origin[0], origin[1] - action.getRange(),  origin[0], origin[1], action.getDegree());

        //将视图中心固定到目标位置 并得到目标坐标
        float[] tarLoc = ViewTool.fitViewByCenter(view, loc);

        //让视图回到中心 并得到开始坐标
        float[] startLoc = ViewTool.fitViewByCenter(view, action.getCenter());

        //x坐标的移动
        ValueAnimator animatorX = ValueAnimator.ofFloat(startLoc[0], tarLoc[0]);
        animatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setX((Float) valueAnimator.getAnimatedValue());
            }
        });

        //y坐标的移动
        ValueAnimator animatorY = ValueAnimator.ofFloat(startLoc[1], tarLoc[1]);
        animatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setY((Float) valueAnimator.getAnimatedValue());
            }
        });

        //旋转动画
        ObjectAnimator animatorRotation = ObjectAnimator.ofFloat(view, "rotation", 0f, 60f, 0f);
        animatorRotation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setVisibility(View.VISIBLE);
            }
        });
        animatorRotation.setInterpolator(new AnticipateOvershootInterpolator());
        ((AnimatorSet) actionAnimator).play(animatorX).with(animatorY);
        ((AnimatorSet) actionAnimator).play(animatorRotation).with(animatorX) ;
        return actionAnimator;
    }

    public void removeAnimator(CircleAction action){
        if(appAnimeMapper != null){
            appAnimeMapper.remove(action.getAction()) ;
        }
    }

    //获取消失时的动画
    public Animator getDismissAnimator(View centerView, List<CircleAction> actions) {
        AnimatorSet animator = new AnimatorSet();
        if (actions != null) {
            if (dismissAnimeMapper == null) {
                dismissAnimeMapper = new HashMap<>();
            }
            //获取centerView 显示动画
            Animator centerAnimator = getCenterDismissAnimator(centerView);

            //获取centerView 伴随action出现的动画
            Animator centerAnimator_x = ObjectAnimator.ofFloat(centerView, "scaleX", 1f, 0.5f, 1f);

            //所有action的动画
            List<Animator> actionAnimasList = new ArrayList<>();
            for (int index = 0; index < actions.size(); index++) {
                CircleAction action = actions.get(index);
                Animator actionAnimator = this.getDismissActionAnimator(action);
                actionAnimasList.add(actionAnimator);
            }
            AnimatorSet actionAnimas = new AnimatorSet();
            actionAnimas.playTogether(actionAnimasList);
            centerAnimator_x.setInterpolator(new AnticipateOvershootInterpolator());
            animator.play(centerAnimator).after(centerAnimator_x);
            animator.play(centerAnimator_x).with(actionAnimas);
        }
        return animator;
    }


    //获取中心消失动画
    private Animator getCenterDismissAnimator(final View centerView) {
        Animator centerAnimator = dismissAnimeMapper.get(centerView);
        if (centerAnimator == null) {
            centerAnimator = new AnimatorSet();
            //透明度动画
            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(centerView, "alpha", 1f, 0f);
            //缩放动画
            ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat(centerAnimator, "scale", 1f, 0f);
            scaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float scale = (float) valueAnimator.getAnimatedValue();
                    centerView.setScaleY(scale);
                    centerView.setScaleX(scale);
                }
            });
            scaleAnimator.setInterpolator(new AnticipateOvershootInterpolator());
            scaleAnimator.addListener(new AnimatorListenerAdapter(){
                @Override
                public void onAnimationEnd(Animator animation) {
                    centerView.setScaleY(1);
                    centerView.setScaleX(1);
                    centerView.setAlpha(1);
                    centerView.setVisibility(View.INVISIBLE);
                }
            });
            ((AnimatorSet) centerAnimator).play(scaleAnimator).with(alphaAnimator);
            appAnimeMapper.put(centerView, centerAnimator);
        }
        return centerAnimator;
    }

    //获取action消失时的动画
    private Animator getDismissActionAnimator(CircleAction action){
        final View view = action.getAction();
        Animator actionAnimator = dismissAnimeMapper.get(view);
        if (actionAnimator == null) {
            actionAnimator = new AnimatorSet();
            actionAnimator.setInterpolator(new AnticipateOvershootInterpolator());
        }
        float[] origin = new float[]{view.getX(), view.getY()} ;
        float[] tagLoc = ViewTool.fitViewByCenter(view, action.getCenter()) ;


        //x坐标的移动
        ValueAnimator animatorX = ValueAnimator.ofFloat(origin[0], tagLoc[0] );
        animatorX.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setX((Float) valueAnimator.getAnimatedValue());
            }
        });

        //y坐标的移动
        ValueAnimator animatorY = ValueAnimator.ofFloat(origin[1], tagLoc[1]);
        animatorY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.setY((Float) valueAnimator.getAnimatedValue());
            }
        });

        //旋转动画
        ObjectAnimator animatorRotation = ObjectAnimator.ofFloat(view, "rotation", 0f, 60f, 0f);
        animatorRotation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.INVISIBLE);
            }
        });
        animatorRotation.setInterpolator(new AnticipateOvershootInterpolator());
        ((AnimatorSet) actionAnimator).play(animatorX).with(animatorY);
        ((AnimatorSet) actionAnimator).play(animatorRotation).with(animatorX) ;
        return actionAnimator;
    }
}
