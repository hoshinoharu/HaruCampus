package com.haru.sora.harucampus.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.haru.sora.harucampus.R;

/**
 * Created by 星野悠 on 2017/1/8.
 */

public class ECardActivity extends AppCompatActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.layout_ecard);
    }

    public static void start(Activity origin){
        Intent intent = new Intent(origin, ECardActivity.class) ;
        origin.startActivity(intent);
    }
}