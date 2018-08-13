package com.yl.myimageupdata.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yl.myimageupdata.R;
import com.yl.myimageupdata.utils.PrefUtils;
import com.yl.myimageupdata.utils.Utils;


/**
 * Description:修改密码界面
 * Date       : 2017/10/20 10:54
 */
public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = "ChangePasswordActivity";
    private EditText mOldPassword, mNewPassWord, mAgainNewPassWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aiter_pass_word);
        initView();//初始化控件
    }

    //初始化控件
    private void initView() {
        Button amendButton = (Button) findViewById(R.id.amendButton);
        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        mOldPassword = (EditText) findViewById(R.id.oldPassword);
        mNewPassWord = (EditText) findViewById(R.id.newPassWord);
        mAgainNewPassWord = (EditText) findViewById(R.id.againNewPassWord);
        //确认修改键
        amendButton.setOnClickListener(this);
        //取消键
        cancelButton.setOnClickListener(this);
        ImageView backspace = (ImageView) findViewById(R.id.backspace);
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText("修改密码");
        backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //点击事件
    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                //修改密码
                case R.id.amendButton:
                    //旧密码
                    String oldStr = mOldPassword.getText().toString().trim();
                    //新密码
                    String newStr = mNewPassWord.getText().toString().trim();
                    //再次输入新密码
                    String againStr = mAgainNewPassWord.getText().toString().trim();
                    //如果旧密码和新密码不一致就提示
                    if (!newStr.equals(againStr)) {
                        Toast.makeText(getApplicationContext(), "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                    } else {
                        //发送修改密码请求
                        send(oldStr, againStr);
                    }
                    break;
                //取消修改密码//取消,返回
                case R.id.cancelButton:
                    onBackPressed();
                    break;
            }
        } catch (Exception e) {
            //捕获异常
            e.printStackTrace();
        }
    }

    //修改密码请求
    private void send(String oldStr, String againStr) {
        String passWord = PrefUtils.getString(Utils.getContext(), "PassWord", "");
        if (oldStr.equals(passWord)) {
            PrefUtils.putString(Utils.getContext(), "PassWord", againStr);
            Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        } else {
            Toast.makeText(getApplicationContext(), "旧密码输入错误", Toast.LENGTH_SHORT).show();
        }

    }


    //返回
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}