package com.yl.myimageupdata.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.yl.myimageupdata.R;


/**
 * Description: 闪屏界面
 * Date       : 2017/10/27 11:41
 */
public class SplashActivity extends AppCompatActivity {
    private ImageView mIvRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //渐变动画
        mIvRoot = (ImageView) findViewById(R.id.re_root);
        openAnimation();
        //   initPermission();
    }

    //开机动画
    private void openAnimation() {
        //版本名
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.3f, 1);
        alphaAnimation.setDuration(1000);
        mIvRoot.startAnimation(alphaAnimation);
        //动画监听
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            //动画结束开启登录界面
            @Override
            public void onAnimationEnd(Animation animation) {
                startLoginActivity();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }


    //登录界面
    private void startLoginActivity() {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        //取消默认界面切换动画
        overridePendingTransition(0, 0);
        finish();
    }

    //回退按钮禁用
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
  /* private void initPermission() {
        //检查权限
        String[] permissions = CheckPermissionUtils.checkPermission(this);
        if (permissions.length == 0) {
            //权限都申请了
            //是否登录
        } else {
            //申请权限
            ActivityCompat.requestPermissions(this, permissions, 100);
        }
    }*/
}
