package com.haru.sora.harucampus.components;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.haru.sora.harucampus.R;
import com.haru.tools.GlideTool;
import com.haru.tools.HLog;
import com.haru.tools.OKHttpTool;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 星野悠 on 2017/1/5.
 */

public class ECardLoginDialog extends AlertDialog {

    private View contentView ;
    private HIndeterminateProgressButton mrPhBtn_login ;
    private ImageButton imgBtn_verifyCode ;

    private String cookies ;

    private String verifyCode ;

    private Activity ownerActivy ;

    public ECardLoginDialog(Activity ownerActivy){
        super(ownerActivy);
        this.ownerActivy =ownerActivy ;
        this.init();
    }

    protected ECardLoginDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.init();
    }

    protected ECardLoginDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.init();
    }

    private void init(){
        this.queryCookies();
        this.contentView = LayoutInflater.from(this.getContext()).inflate(R.layout.layout_ecard_login, null) ;
        this.setView(this.contentView);
        this.mrPhBtn_login = (HIndeterminateProgressButton) this.contentView.findViewById(R.id.mrPhBtn_login);
        this.contentView.getRootView().setBackgroundColor(this.getContext().getResources().getColor(android.R.color.transparent));
        this.imgBtn_verifyCode = (ImageButton) this.contentView.findViewById(R.id.imgBtn_verifyCode);
    }

    @Override
    public void show() {
        super.show();
        this.mrPhBtn_login.morphToSquare();
    }

    //请求验证码
    public void queryVerifyCode(){
        if(this.cookies == null){
            Toast.makeText(getContext(), R.string.server_fail, Toast.LENGTH_SHORT).show();
        }else {
            this.ownerActivy.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Glide.with(ownerActivy).load(GlideTool.getUrlWithCookies(ownerActivy.getString(R.string.verifyCode_url), cookies)).into(imgBtn_verifyCode);
                }
            });
        }
    }

    //获取Cookie
    public void queryCookies(){
        OKHttpTool.sendOkHttpRequest(this.getContext().getString(R.string.ecard_home_url), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(getContext(), R.string.server_fail, Toast.LENGTH_SHORT).show();
                HLog.ex("TAG", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                cookies = response.header("Set-Cookie", null) ;
                HLog.e("TAG", cookies);
                Looper.prepare();
                Toast.makeText(getContext(), ""+cookies, Toast.LENGTH_SHORT).show();
                queryVerifyCode();
            }
        });
    }
}
