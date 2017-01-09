package com.haru.sora.harucampus.components.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.haru.sora.harucampus.R;
import com.haru.sora.harucampus.service.UserService;
import com.haru.sora.harucampus.vo.User;
import com.haru.sora.harucampus.vo.UserManager;
import com.haru.tools.HLog;
import com.haru.tools.OKHttpTool;
import com.haru.tools.Res;
import com.haru.widget.SqueezeboxGroup;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 星野悠 on 2017/1/8.
 */

public class ECardOptionFragment extends HFragment implements SqueezeboxGroup.ItemOnClickListener {


    private TextView txtVw_queryUserInfo;

    private SqueezeboxGroup container;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_eacrd_options, container);
        this.contentView = view;
        this.container = (SqueezeboxGroup) this.findViewById(R.id.container);
        this.txtVw_queryUserInfo = (TextView) this.findViewById(R.id.txtVw_queryUserInfo);
        this.init();
        return view;
    }

    public void init() {
        this.container.setItemOnClickListener(this);
    }

    private TextView curTitel;
    private TextView curChoosedOption;
    private Animator titleChoosedAnime;
    private Animator titleUnChoosedAnime;

    private Animator contentItemChoosedAnime;
    private Animator contentItemUnChoosedAnime;

    @Override
    public void onItemTitleClick(View view, boolean showContent) {
        if (view instanceof TextView) {
            if (curTitel != null) {
                curTitel.setTextColor(Res.color(android.R.color.white));
                if (titleUnChoosedAnime == null) {
                    final ObjectAnimator animator = ObjectAnimator.ofFloat(curTitel, "scaleY", 1.2f, 1);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            View view = (View) animator.getTarget();
                            view.setScaleX((Float) animation.getAnimatedValue());
                        }
                    });
                    animator.setInterpolator(new OvershootInterpolator());
                    this.titleUnChoosedAnime = animator;
                }
                curTitel.setPivotX(curTitel.getX());
                titleUnChoosedAnime.setTarget(curTitel);
                titleUnChoosedAnime.start();
            }
          if(curTitel != view || showContent){
              curTitel = (TextView) view;
              curTitel.setTextColor(Res.color(android.R.color.background_dark));
              if (titleChoosedAnime == null) {
                  final ObjectAnimator animator = ObjectAnimator.ofFloat(curTitel, "scaleY", 1, 1.2f);
                  animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                      @Override
                      public void onAnimationUpdate(ValueAnimator animation) {
                          View view = (View) animator.getTarget();
                          view.setScaleX((Float) animation.getAnimatedValue());
                      }
                  });
                  animator.setInterpolator(new OvershootInterpolator());
                  this.titleChoosedAnime = animator;
              }
              curTitel.setPivotX(curTitel.getX());
              titleChoosedAnime.setTarget(curTitel);
              titleChoosedAnime.start();
          }

        }
    }

    @Override
    public void onItemContentClick(View view) {
        if (view == txtVw_queryUserInfo) {
            this.queryUserInfo();
        }
        if (view instanceof TextView) {
            if (curChoosedOption != null) {
                curChoosedOption.setTextColor(Res.color(android.R.color.white));
                if (contentItemChoosedAnime == null) {
                    final ObjectAnimator animator = ObjectAnimator.ofFloat(curTitel, "scaleY", 1.2f, 1);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            View view = (View) animator.getTarget();
                            view.setScaleX((Float) animation.getAnimatedValue());
                        }
                    });
                    animator.setInterpolator(new OvershootInterpolator());
                    this.contentItemChoosedAnime = animator;
                }
                curChoosedOption.setPivotX(curTitel.getX());
                contentItemChoosedAnime.setTarget(curChoosedOption);
                contentItemChoosedAnime.start();
            }
            curChoosedOption = (TextView) view;
            curChoosedOption.setTextColor(Res.color(android.R.color.background_dark));
            if (contentItemUnChoosedAnime == null) {
                final ObjectAnimator animator = ObjectAnimator.ofFloat(curTitel, "scaleY", 1, 1.2f);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        View view = (View) animator.getTarget();
                        view.setScaleX((Float) animation.getAnimatedValue());
                    }
                });
                animator.setInterpolator(new OvershootInterpolator());
                this.contentItemUnChoosedAnime = animator;
            }
            curChoosedOption.setPivotX(curTitel.getX());
            contentItemUnChoosedAnime.setTarget(curChoosedOption);
            contentItemUnChoosedAnime.start();
        }

    }

    public void queryUserInfo() {
        User user = UserManager.getUser();
        UserService service = UserService.getUserService();
        service.queryInfo(user, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String html = OKHttpTool.getHtml(response.body().byteStream()) ;

            }
        });
    }
}
