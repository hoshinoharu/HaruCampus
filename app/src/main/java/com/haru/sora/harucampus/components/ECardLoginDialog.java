package com.haru.sora.harucampus.components;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.haru.sora.harucampus.R;
import com.haru.tools.GlideTool;
import com.haru.tools.MathTool;
import com.haru.tools.OKHttpTool;
import com.haru.tools.Res;
import com.haru.tools.ViewTool;
import com.haru.widget.HTextWatcher;

import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Response;

/**
 * Created by 星野悠 on 2017/1/5.
 */

public class ECardLoginDialog extends AlertDialog implements View.OnClickListener, View.OnFocusChangeListener, DialogInterface.OnShowListener {

    private View mainView;
    private HIndeterminateProgressButton mrPhBtn_login ;
    private ImageButton imgBtn_verifyCode ;


    //cookies
    private String cookies ;


    //验证码
    private String verifyCode ;


    private Activity ownerActivy ;

    private EditText edText_verifyCode ;

    private EditText edTxt_userName ;

    private EditText edTxt_password ;

    private RadioGroup rdoGrp_loginMoedl ;


    //错误图片
    private Drawable error ;

    private Animator apperanceAnimator ;

    private View rootView ;

    private View contentView ;


    public ECardLoginDialog(Activity ownerActivy){
        super(ownerActivy, R.style.NoBackGroundDialog);
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
        this.error = new BitmapDrawable(BitmapFactory.decodeResource(this.ownerActivy.getResources(), R.drawable.frame_error)) ;
        this.mainView = LayoutInflater.from(this.getContext()).inflate(R.layout.layout_ecard_login, null) ;
        this.setView(this.mainView);
        this.contentView = this.mainView.findViewById(R.id.contentView) ;
        this.mrPhBtn_login = (HIndeterminateProgressButton) this.mainView.findViewById(R.id.mrPhBtn_login);
        this.imgBtn_verifyCode = (ImageButton) this.mainView.findViewById(R.id.imgBtn_verifyCode);
        this.imgBtn_verifyCode.setOnClickListener(this);
        this.edText_verifyCode = (EditText) this.mainView.findViewById(R.id.edText_verifyCode);
        this.mrPhBtn_login.setOnClickListener(this);

        this.edTxt_userName = (EditText) this.mainView.findViewById(R.id.edTxt_userName);
        this.edTxt_password = (EditText) this.mainView.findViewById(R.id.edTxt_password);
        this.rdoGrp_loginMoedl = (RadioGroup) this.mainView.findViewById(R.id.rdoGrp_loginModel);

        this.edTxt_password.setOnFocusChangeListener(this);
        this.edTxt_userName.setOnFocusChangeListener(this);
        this.edText_verifyCode.setOnFocusChangeListener(this);

        this.edText_verifyCode.addTextChangedListener(new HTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                edText_verifyCode.setBackgroundColor(Res.color(ownerActivy, android.R.color.transparent));
            }
        });

        this.edTxt_password.addTextChangedListener(new HTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                edTxt_password.setBackgroundColor(Res.color(ownerActivy, android.R.color.transparent));
            }
        });

        this.edTxt_userName.addTextChangedListener(new HTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                edTxt_userName.setBackgroundColor(Res.color(ownerActivy, android.R.color.transparent));
            }
        });


        this.rdoGrp_loginMoedl.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                rdoGrp_loginMoedl.setBackgroundColor(Res.color(ownerActivy, android.R.color.transparent));
                mrPhBtn_login.morphToSquare();
            }
        });

        this.setOnShowListener(this);
    }

    @Override
    public void show() {
        super.show();
        if(loginAnimeHandler == null){
            loginAnimeHandler = new Handler(Looper.getMainLooper()) ;
        }
        this.queryVerifyCode();
    }

    //请求验证码
    public void queryVerifyCode(){
        mrPhBtn_login.morphToSquare();
        if(this.cookies == null){
            Toast.makeText(getContext(), R.string.server_fail, Toast.LENGTH_SHORT).show();
        }else {
            this.ownerActivy.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Glide.with(ownerActivy).load(GlideTool.getUrlWithCookies(ownerActivy.getString(R.string.verifyCode_url)+ MathTool.randomDouble(), cookies)).into(imgBtn_verifyCode);
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
                Snackbar.make(mainView, R.string.server_fail, Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                cookies = response.header("Set-Cookie", null) ;
                Looper.prepare();
                queryVerifyCode();
            }
        });
    }


    private boolean isCheckSuccess = false;
    private Handler loginAnimeHandler ;
    long startTimes ;
    //校验验证码
    public void checkVerifyCode(){
        if(!isCheckSuccess){
            startTimes = System.currentTimeMillis() ;
            this.mrPhBtn_login.simulateProgress();
            OKHttpTool.sendOkHttpRequest(ownerActivy.getString(R.string.check_verify_code_url) + this.verifyCode, Headers.of("Cookie", cookies),  new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    boolean flag = Boolean.parseBoolean(response.body().string()) ;
                        if(flag){
                            verifyCodeSuccess();
                        }else{
                            verifyCodeFail();
                        }
                }
            });
        }
    }

    public void verifyCodeSuccess() {
        this.checkUser();
    }

    public void login(){
        this.verifyCode = this.edText_verifyCode.getText().toString() ;
        if(!ViewTool.checkInput(this.verifyCode)){
            this.verifyCodeFail();
            return ;
        }
        this.checkVerifyCode();
    }

    public void checkUser(){
        String userName = this.edTxt_userName.getText().toString() ;
        String password = this.edTxt_password.getText().toString() ;
        String loginUrl = getLoginUrl() ;
        if(loginUrl == null){
            this.loginModelError();
            return ;
        }
        OKHttpTool.sendOkHttpRequest(loginUrl+"&username="+userName+"&password="+password+"&checkCode="+verifyCode, Headers.of("Cookie", cookies), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String html = response.body().string() ;
                long offset = System.currentTimeMillis() - startTimes ;

                if(offset > 750){
                    offset = 750 ;
                }
                offset = 750 - offset ;
                if(Pattern.compile(ownerActivy.getString(R.string.login_resp_regex)).matcher(html).find()){
                    //登录成功
                    loginAnimeHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            checkUserSuccess(html);
                        }
                    }, offset) ;
                }else{
                    //登录失败
                    loginAnimeHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            checkUserFail();
                        }
                    }, offset) ;
                }
            }
        });
    }

    private void checkUserSuccess(String html) {
        Snackbar.make(this.mainView, R.string.login_success, Snackbar.LENGTH_LONG).show();
        mrPhBtn_login.morphToSuccess();
    }

    public void checkUserFail(){
        Snackbar.make(this.mainView, R.string.user_error_info, Snackbar.LENGTH_LONG).show();
        mrPhBtn_login.morphToFailure();
    }

    public void loginModelError(){
        Snackbar.make(this.mainView, R.string.login_model_error, Snackbar.LENGTH_LONG).show();
        mrPhBtn_login.morphToFailure();
    }

    public String getLoginUrl(){
        switch (this.rdoGrp_loginMoedl.getCheckedRadioButtonId()){
            case R.id.rdoBtn_user:
                return ownerActivy.getString(R.string.express_login_url);
            case R.id.rdoBtn_trade:
                return ownerActivy.getString(R.string.trade_login_url);
            case R.id.rdoBtn_admin:
                return ownerActivy.getString(R.string.manager_login_url) ;
        }
        return null ;
    }

    @Override
    public void onClick(View view) {
        if(view == this.mrPhBtn_login){
            if(mrPhBtn_login.isCanTouch()){
                this.login();
            }
        }else if(view == this.imgBtn_verifyCode){
            this.queryVerifyCode();
        }
    }

    public void verifyCodeFail(){
        this.ownerActivy.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                edText_verifyCode.setBackgroundResource(R.drawable.frame_error);
                mrPhBtn_login.morphToFailure();
            }
        });
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if(b){
            this.mrPhBtn_login.morphToSquare();
        }
    }

    @Override
    public void onShow(DialogInterface dialogInterface) {
        if(this.rootView == null){
            this.rootView = this.mainView.getRootView() ;
            this.rootView.setBackgroundResource(android.R.color.transparent);
        }
        //初始化动画
        if(this.apperanceAnimator == null) {
            this.apperanceAnimator = ObjectAnimator.ofFloat(this.contentView, "rotationX", 90, 0);
            this.apperanceAnimator.setDuration(500);
        }
        this.apperanceAnimator.start();
    }
}
