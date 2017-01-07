package com.haru.sora.harucampus.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.dd.morphingbutton.MorphingAnimation;
import com.dd.morphingbutton.MorphingButton;
import com.dd.morphingbutton.impl.IndeterminateProgressButton;
import com.haru.sora.harucampus.R;
import com.haru.tools.Res;

/**
 * Created by 星野悠 on 2017/1/6.
 */

 enum State{
    SUCCESS, FAIL,SQUARE,SIMUPROGRESS
        }
public class HIndeterminateProgressButton extends IndeterminateProgressButton implements View.OnClickListener {

    private int duration = 500;
    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
    private State state ;


    public HIndeterminateProgressButton(Context context) {
        super(context);
        this.init();
    }

    public HIndeterminateProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public HIndeterminateProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    public void init(){
        this.setOnClickListener(this);
    }

    private MorphingButton.Params squareParams ;
    public void morphToSquare() {
        if(this.state == State.SQUARE){
            return ;
        }
        this.canTouch = true ;
        this.state = State.SQUARE ;
        if(squareParams == null) {
            this.squareParams = MorphingButton.Params.create()
                    .duration(duration)
                    .cornerRadius(Res.dimen(this.getContext(), R.dimen.mb_corner_radius_2))
                    .width(Res.dimen(this.getContext(), R.dimen.mb_width_100))
                    .height(Res.dimen(this.getContext(), R.dimen.mb_height_56))
                    .color(Res.color(this.getContext(), R.color.mb_blue))
                    .colorPressed(Res.color(this.getContext(), R.color.mb_blue_dark))
                    .text(this.getContext().getString(R.string.login)).animationListener(new MorphingAnimation.Listener() {
                        @Override
                        public void onAnimationEnd() {
                            canSimulateProgress = true;
                        }
                    });
        }
        this.unblockTouch();
        this.morph(squareParams);
    }
    private int progressColor1 = Res.color(this.getContext(), R.color.holo_blue_bright);
    private int progressColor2 = Res.color(this.getContext(), R.color.holo_green_light);
    private int progressColor3 = Res.color(this.getContext(), R.color.holo_orange_light);
    private int progressColor4 = Res.color(this.getContext(), R.color.holo_red_light);
    private int color = Res.color(this.getContext(), R.color.mb_gray);
    private int progressCornerRadius = Res.dimen(this.getContext(), R.dimen.mb_corner_radius_4);
    private int width = Res.dimen(this.getContext(), R.dimen.mb_width_200);
    private int height = Res.dimen(this.getContext(), R.dimen.mb_height_8);
    private int progressDuration = Res.integer(this.getContext(), R.integer.mb_animation);

    public void simulateProgress() {
        if(this.state == State.SIMUPROGRESS){
            return ;
        }
        this.state = State.SIMUPROGRESS ;
        this.canTouch = false ;
        this.canSimulateProgress = false ;
        this.blockTouch();
        this.morphToProgress(color, progressCornerRadius, width, height, progressDuration, progressColor1, progressColor2,
                progressColor3, progressColor4);
    }

    private MorphingButton.Params successParams ;
    public void morphToSuccess() {
        if(this.state == State.SUCCESS){
            return ;
        }
        this.state = State.SUCCESS ;
        this.canTouch = false ;
        if(successParams == null) {
            successParams = MorphingButton.Params.create()
                    .duration(Res.integer(this.getContext(), R.integer.mb_animation))
                    .cornerRadius(Res.dimen(this.getContext(), R.dimen.mb_height_56))
                    .width(Res.dimen(this.getContext(), R.dimen.mb_height_56))
                    .height(Res.dimen(this.getContext(), R.dimen.mb_height_56))
                    .color(Res.color(this.getContext(), R.color.mb_green))
                    .colorPressed(Res.color(this.getContext(), R.color.mb_green_dark))
                    .icon(R.drawable.ic_done);
            successParams.animationListener(new MorphingAnimation.Listener() {
                @Override
                public void onAnimationEnd() {
                    unblockTouch();
                }
            }) ;
        }
        this.blockTouch();
        this.morph(successParams);
    }

    private MorphingButton.Params failureParams ;
    public void morphToFailure() {
        if(this.state == State.FAIL){
            return ;
        }
        this.state = State.FAIL ;
        this.canTouch = false ;
        if(failureParams == null) {
            failureParams = MorphingButton.Params.create()
                    .duration(duration)
                    .cornerRadius(Res.dimen(this.getContext(), R.dimen.mb_height_56))
                    .width(Res.dimen(this.getContext(), R.dimen.mb_height_56))
                    .height(Res.dimen(this.getContext(), R.dimen.mb_height_56))
                    .color(Res.color(this.getContext(), R.color.mb_red))
                    .colorPressed(Res.color(this.getContext(), R.color.mb_red_dark))
                    .icon(R.drawable.ic_lock);
            failureParams.animationListener(new MorphingAnimation.Listener() {
                @Override
                public void onAnimationEnd() {
                    unblockTouch();
                }
            }) ;
        }
        this.blockTouch();
        this.morph(failureParams);
    }
    private boolean canSimulateProgress = true ;
    private boolean canTouch = true ;
    @Override
    public void onClick(View view) {
        if(canTouch){
            if(canSimulateProgress){
                this.simulateProgress();
            }else{
                this.morphToSquare();
            }
        }
    }

    public boolean isCanTouch() {
        return canTouch;
    }

    public void setCanTouch(boolean canTouch) {
        this.canTouch = canTouch;
    }
}
