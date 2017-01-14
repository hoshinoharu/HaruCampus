package com.haru.sora.harucampus.components.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haru.sora.harucampus.R;

/**
 * Created by 星野悠 on 2017/1/11.
 */

public class ECardHomePageFragment extends HFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.contentView = inflater.inflate(R.layout.frag_ecard_home_page, container) ;
        return  this.contentView;
    }
}
