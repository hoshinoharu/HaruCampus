package com.haru.sora.harucampus.components;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by 星野悠 on 2017/1/8.
 */

public class OptionLayout extends LinearLayout {
    public OptionLayout(Context context) {
        super(context);
        this.init();
    }

    public OptionLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public OptionLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public OptionLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void init(){

    }
}
