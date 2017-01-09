package com.haru.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haru.tools.HLog;
import com.haru.tools.Res;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 星野悠 on 2017/1/9.
 */

public class SqueezeboxGroup extends LinearLayout implements SqueezeboxListener{

    public interface ItemOnClickListener{
        void onItemTitleClick(View view, boolean showContent) ;
        void onItemContentClick(View view) ;
    }
    private List<SqueezeboxView> childs  ;

    private ItemOnClickListener itemOnClickListener ;

    public SqueezeboxGroup(Context context) {
        super(context);
    }

    public SqueezeboxGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SqueezeboxGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SqueezeboxGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed){
            this.init();
        }
    }

    public void  init(){
        if(childs == null){
            childs = new ArrayList<>() ;
            View child = null ;
            int index = 0 ;
            SqueezeboxView preView = null;
            SqueezeboxView curView ;

            //添加子View
            while((child = this.getChildAt(index)) != null){
                if(child instanceof SqueezeboxView){
                    curView = (SqueezeboxView) child;
                    curView.setIndex(index);
                    curView.setSqueezeboxListener(this);
                    childs.add(curView) ;
                    if(childs.size() > 1){
                        preView = childs.get(index-1) ;
                        preView.setNextView(curView);
                    }
                    curView.setPreView(preView);
                }
                index ++ ;
            }
            this.setItemOnClickListener(this.itemOnClickListener);
        }

    }
    private int originHeight  ;
    private ViewGroup.LayoutParams layoutParams ;
    private SqueezeboxView curShowView = null ;
    public void onContentShow(SqueezeboxView child){
        if(curShowView != null){
            if(curShowView != child){
                curShowView.hideContent();
            }
        }
        curShowView = child ;
    }

    public void onContentHide(SqueezeboxView child){

    }

    public void setItemOnClickListener(ItemOnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
        if(childs != null) {
            for (SqueezeboxView squeezeboxView : childs) {
                squeezeboxView.setItemOnClickListener(this.itemOnClickListener);
            }
        }
    }

}
