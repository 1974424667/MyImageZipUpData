package com.yl.myimageupdata.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;

import com.yl.myimageupdata.activity.MainActivity;
import com.yl.myimageupdata.R;


/**
 * Description:  定时轮询后台服务
 * Copyright  : Copyright (c) 2016
 * Company    :
 * Author     : yl
 * Date       : 2017/10/8 13:42
 */
public class LoopsService extends Service {
    //绑定服务
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //开启通知栏，将后台服务设置为前台服务
        Intent i = new Intent(this, MainActivity.class);
        PendingIntent pint = PendingIntent.getActivity(this, 0, i, 0);
        Notification notification = new NotificationCompat.Builder(this).setContentTitle("拍照小能手")
                .setContentText("请勿关闭此通知").setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pint).build();
        startForeground(1, notification);
    }

    //开启服务
    @Override
    public int onStartCommand(Intent intent, final int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    //注销
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
