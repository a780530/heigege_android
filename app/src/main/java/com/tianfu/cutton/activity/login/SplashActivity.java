package com.tianfu.cutton.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.tianfu.cutton.MainActivity;
import com.tianfu.cutton.R;
import com.tianfu.cutton.utils.PrefManager;
import com.zhy.autolayout.AutoFrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.fl)
    AutoFrameLayout fl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = View.inflate(this, R.layout.activity_splash, null);     //将启动界面的xml文件转换为view
        setContentView(view);
        ButterKnife.bind(this);
        fl.setSystemUiVisibility(View.INVISIBLE);
        AlphaAnimation aa = new AlphaAnimation(0.3f, 1f);           //设置动画效果
        aa.setDuration(2000);
        view.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {


            @Override
            public void onAnimationEnd(Animation animation) {
// TODO 自动生成的方法存根
                Intent intent = new Intent();
                PrefManager prefManager = new PrefManager(SplashActivity.this);
                if (prefManager.isFirstTimeLaunch()) {
                    intent.setClass(SplashActivity.this, WelcomeActivity.class);
                }else{
                    intent.setClass(SplashActivity.this, MainActivity.class);
                }
                startActivity(intent);
                finish();
            }


            @Override
            public void onAnimationStart(Animation animation) {
// TODO 自动生成的方法存根

            }


            @Override
            public void onAnimationRepeat(Animation animation) {
// TODO 自动生成的方法存根

            }
        });

    }
}

