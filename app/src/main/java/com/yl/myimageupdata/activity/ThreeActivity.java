package com.yl.myimageupdata.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yl.myimageupdata.R;

public class ThreeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        TextView tv = (TextView) findViewById(R.id.tv);
        ImageView backspace = (ImageView) findViewById(R.id.backspace);
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tv.setText("模块三敬请期待");
        tvTitle.setText("模块三");
        backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
