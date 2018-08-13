package com.yl.myimageupdata.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.yl.myimageupdata.R;
import com.yl.myimageupdata.utils.PrefUtils;
import com.yl.myimageupdata.utils.Utils;

public class LoginBtnActivity extends AppCompatActivity {
    private Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        mBtn = (Button) findViewById(R.id.btn);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "已将密码改为 123 ", Toast.LENGTH_SHORT).show();
                PrefUtils.putString(Utils.getContext(), "PassWord", "123");
                finish();
            }
        });
    }
}
