package com.haru.sora.harucampus.components.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.haru.sora.harucampus.R;
import com.haru.sora.harucampus.vo.User;
import com.haru.sora.harucampus.vo.UserInfo;
import com.haru.tools.HLog;

/**
 * Created by 星野悠 on 2017/1/10.
 */

public class UserInfoFragment extends HFragment {

    private User user ;
    public UserInfoFragment(){

    }
    public UserInfoFragment(User user) {
        this.user = user ;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.contentView = inflater.inflate(R.layout.frag_user_info, container, false) ;
        return this.contentView ;
    }
}
