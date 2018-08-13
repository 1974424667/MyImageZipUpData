package com.yl.myimageupdata.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.yl.myimageupdata.R;
import com.yl.myimageupdata.utils.PrefUtils;
import com.yl.myimageupdata.utils.Utils;

public class LoginActivity extends AppCompatActivity {

    private VideoView mVideoView;
    private ImageView mIvVideo;
    private Button mImage;
    private Button mloginBtLogin;
    private EditText mloginEtUsername;
    private EditText mloginEtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mVideoView = (VideoView) findViewById(R.id.login_video_view);
        mloginEtUsername = (EditText) findViewById(R.id.login_et_username);
        mloginEtPassword = (EditText) findViewById(R.id.login_et_password);
        mImage = (Button) findViewById(R.id.image);
        mIvVideo = (ImageView) findViewById(R.id.login_iv_video);
        mloginBtLogin = (Button) findViewById(R.id.login_bt_login);
        mloginBtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = mloginEtUsername.getText().toString().trim();
                String password = mloginEtPassword.getText().toString().trim();
                if (!TextUtils.isEmpty(password)) {
                    sendLogin(userName, password);
                } else {
                    Toast.makeText(getApplicationContext(), "密不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginBtnActivity.class));
            }
        });
       /* mVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/raw/video_login"));
        //循环播放
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
                mediaPlayer.setLooping(true);
            }
        });*/


       /* //防止照片隐藏黑屏
        mVideoView.start();
        mVideoView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mIvVideo.setVisibility(View.GONE);
            }
        }, 1000);*/
    }

    private void sendLogin(String userName, String pas) {
        String passWord = PrefUtils.getString(Utils.getContext(), "PassWord", "");
        if (passWord.equals(pas)) {
            Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        } else {
            Toast.makeText(getApplicationContext(), "登录失败，密码错误", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
