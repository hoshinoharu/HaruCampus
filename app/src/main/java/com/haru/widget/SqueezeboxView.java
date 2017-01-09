package com.haru.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;

/**
 * Created by 星野悠 on 2017/1/9.
 */
enum State{
    SHOW,HIDE
}

/***
 * 搭配SqueezeboxViewGroup使用
 * 第一个子View是内容
 * 第二个子View才是标题，最开始显示的部分
 */
public class SqueezeboxView extends FrameLayout implements View.OnClickListener {

    public interface OnContentItemClickListener{
        void onContentItemClick(View view);
    }
    private View title ;
    private View content ;

    private SqueezeboxListener squeezeboxListener ;

    private OnContentItemClickListener onContentItemClickListener ;

    void setItemOnClickListener(SqueezeboxGroup.ItemOnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }

    private SqueezeboxGroup.ItemOnClickListener itemOnClickListener ;

    //上一个节点
    private SqueezeboxView nextView ;

    //下一个节点
    private SqueezeboxView preView ;

    private int index ;

    private State curState = State.HIDE ;


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public SqueezeboxView getNextView() {
        return nextView;
    }

    public void setNextView(SqueezeboxView nextView) {
        this.nextView = nextView;
    }

    public SqueezeboxView getPreView() {
        return preView;
    }

    public void setPreView(SqueezeboxView preView) {
        this.preView = preView;
    }

    private boolean showContent = false ;

    public SqueezeboxView(Context context) {
        super(context);
    }

    public SqueezeboxView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SqueezeboxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SqueezeboxView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void init(){
        if(this.title == null){
            this.title = this.getChildAt(1) ;
            this.content = this.getChildAt(0) ;
            this.title.setOnClickListener(this);
            this.layoutParams = this.getLayoutParams();
            this.originContentHeight = this.content.getHeight() ;
            this.content.setY(this.title.getY()+this.title.getHeight());
            setClickListener(this.content);
            this.show();
        }
    }


    //给所有子视图设置监听
    private void setClickListener(View view){
        if(view instanceof  ViewGroup){
            ViewGroup viewGroup = (ViewGroup) view;
            for(int index = 0; index < viewGroup.getChildCount(); index ++){
                setClickListener(viewGroup.getChildAt(index));
            }
        }else{
            view.setOnClickListener(this);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed){
           this.init();
        }
    }

    private Animator showAnime ;
    private Animator hideAnime ;
    public void showContent(){
        this.showContent = true ;
        if(this.curState == State.SHOW){
            return ;
        }
        this.curState = State.SHOW ;
        if(squeezeboxListener != null){
            squeezeboxListener.onContentShow(SqueezeboxView.this);
        }
        if(this.showAnime == null){
            final ObjectAnimator animator = ObjectAnimator.ofFloat(this.content, "haru", 0, this.originContentHeight);
            animator.setDuration(500) ;
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float height = (float) valueAnimator.getAnimatedValue();
                    layoutParams.height = (int) (getTitle().getHeight() + height);
                    SqueezeboxView.this.setLayoutParams(layoutParams);
                }
            });
            animator.setInterpolator(new OvershootInterpolator());
            this.showAnime = animator ;
        }
        if(this.hideAnime != null){
            if(this.hideAnime.isRunning()){
                this.hideAnime.cancel();
            }
        }
        this.showAnime.start();
    }

    public void  hideContent(){
        this.showContent = false ;
        if(this.curState == State.HIDE){
            return ;
        }
        this.curState = State.HIDE ;
        if(squeezeboxListener != null){
            squeezeboxListener.onContentHide(SqueezeboxView.this);
        }
        if(this.hideAnime == null){
            ObjectAnimator animator = ObjectAnimator.ofFloat(this.content, "haru", this.originContentHeight, 0);
            animator.setDuration(500) ;
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float height = (float) valueAnimator.getAnimatedValue();
                    layoutParams.height = (int) (getTitle().getHeight() + height);
                    SqueezeboxView.this.setLayoutParams(layoutParams);
                }
            });
            animator.setInterpolator(new DecelerateInterpolator());
            this.hideAnime = animator ;
        }
        if(this.showAnime != null){
            if(this.showAnime.isRunning()){
                this.showAnime.cancel();
            }
        }
        this.hideAnime.start();
    }

    @Override
    public void onClick(View view) {
        if(view == this.title){
            if(!this.showContent){
                this.showContent();
            }else{
                this.hideContent();
            }

            if(this.itemOnClickListener != null){
                this.itemOnClickListener.onItemTitleClick(view, showContent);
            }
        }else{
            if(this.itemOnClickListener != null){
                this.itemOnClickListener.onItemContentClick(view);
            }
            if(this.onContentItemClickListener != null){
                this.onContentItemClickListener.onContentItemClick(view);
            }
        }
    }

    public SqueezeboxListener getSqueezeboxListener() {
        return squeezeboxListener;
    }

    public void setSqueezeboxListener(SqueezeboxListener squeezeboxListener) {
        this.squeezeboxListener = squeezeboxListener;
    }

    public View getTitle() {
        return title;
    }


    public View getContent() {
        return content;
    }


    private int originContentHeight ;

    private ViewGroup.LayoutParams layoutParams ;

    private Animator appAnime ;
    public void show(){
        //强制调整大小
        if(appAnime == null){
            ValueAnimator animator = ValueAnimator.ofFloat(0, this.title.getHeight()) ;
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    layoutParams.height = (int)(float) valueAnimator.getAnimatedValue();
                    setLayoutParams(layoutParams);
                }
            });
            animator.setInterpolator(new OvershootInterpolator());
            appAnime = animator ;
        }
        this.appAnime.start();
    }
}
