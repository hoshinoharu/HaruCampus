package com.haru.sora.harucampus.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.haru.sora.harucampus.R;
import com.haru.sora.harucampus.components.fragment.UserInfoFragment;
import com.haru.sora.harucampus.vo.User;

/**
 * Created by 星野悠 on 2017/1/8.
 */

public class ECardActivity extends AppCompatActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.layout_ecard);
        ActionBar actionBar = getSupportActionBar() ;
        if(actionBar != null){
            actionBar.hide();
        }
    }

    public static void start(Activity origin){
        Intent intent = new Intent(origin, ECardActivity.class) ;
        origin.startActivity(intent);
    }

    public void showUserInfo(User user){
        FragmentManager manager = this.getSupportFragmentManager() ;
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.frag_ecardContent, new UserInfoFragment());
        fragmentTransaction.addToBackStack(null) ;
        fragmentTransaction.commit() ;
    }
}
