package com.haru.sora.harucampus.components.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 星野悠 on 2017/1/9.
 */

public class HFragment extends Fragment {
    protected View contentView ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.contentView = super.onCreateView(inflater, container, savedInstanceState);
        return this.contentView ;
    }

    public View findViewById(int resId){
        return this.contentView.findViewById(resId) ;
    }
}
