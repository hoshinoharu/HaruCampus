package com.haru.sora.harucampus;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.haru.sora.harucampus.components.ECardLoginDialog;
import com.haru.tools.ViewTool;
import com.haru.widget.CircleActionLayout;

public class HomPage extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton fab ;

    private CircleActionLayout circleActionLayout;

    private LinearLayout lnrLot_ecardLogin ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hom_page);
        this.init();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(circleActionLayout == null){
                    View centerView = HomPage.this.findViewById(R.id.centerView) ;
                    circleActionLayout = (CircleActionLayout) HomPage.this.findViewById(R.id.container);
                    circleActionLayout.setRange(ViewTool.dip2px(HomPage.this, 120));
                    circleActionLayout.setCenterView(centerView);
                    circleActionLayout.addViewAsAction(lnrLot_ecardLogin);
                    circleActionLayout.addViewAsAction(HomPage.this.findViewById(R.id.test1));
                    circleActionLayout.addViewAsAction(HomPage.this.findViewById(R.id.test2));
                    circleActionLayout.addViewAsAction(HomPage.this.findViewById(R.id.test3));
                    circleActionLayout.addViewAsAction(HomPage.this.findViewById(R.id.test4));
                    circleActionLayout.addViewAsAction(HomPage.this.findViewById(R.id.test5));
                    circleActionLayout.addViewAsAction(HomPage.this.findViewById(R.id.test6));
                    centerView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(circleActionLayout.dismiss()){
                                fab.setVisibility(View.VISIBLE);
                                ObjectAnimator.ofFloat(fab, "scaleX", 0, 1).setDuration(300).start();
                                ObjectAnimator objectAnime = ObjectAnimator.ofFloat(fab, "scaleY", 0, 1).setDuration(300);
                                objectAnime.start();
                            }
                        }
                    });
                }
                circleActionLayout.setAnimaDuration(300);
                if( circleActionLayout.show()){
                    ObjectAnimator.ofFloat(fab, "scaleX", 1f, 0).setDuration(300).start();
                    ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(fab, "scaleY", 1f, 0).setDuration(300);
                    objectAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            fab.setVisibility(View.INVISIBLE);
                        }
                    }) ;
                    objectAnimator.start();
                }
            }
        });


    }

    private void init(){
        this.lnrLot_ecardLogin = (LinearLayout) this.findViewById(R.id.lnrLot_ecardLogin);
        this.lnrLot_ecardLogin.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_hom_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if(view == this.lnrLot_ecardLogin){
            new ECardLoginDialog(this).show();
        }
    }
}
