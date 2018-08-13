package com.yl.myimageupdata.baseappliction;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;

import com.yl.myimageupdata.utils.LogUtil;
import com.yl.myimageupdata.utils.PrefUtils;
import com.yl.myimageupdata.utils.Utils;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Description:全局语音
 * Copyright  : Copyright (c) 2017
 * Company    : 大麦科技
 * Author     : yl
 * Date       : 2017/1/25 15:08141
 */
public class BaseApplication extends Application  {

    public Context context;
    public static BaseApplication instance;
    public Handler handler;
    public int mainThreadId;
    private ArrayList<Activity> activities = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext(); //获取全局Context对象
        handler = new Handler();
        mainThreadId = android.os.Process.myTid(); //获取主线程的线程id
        instance = this;//Application对象的创建是有系统来完成，不能自己new出Application的实例对象
        PrefUtils.putString(Utils.getContext(), "PassWord", "123");
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());//捕获全局异常

    }

    //添加单个Activity
    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    //移除单个Activity
    public void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    //移除所有Activity
    public void exit() {
        for (Activity activity : activities) {
            activity.finish();
        }
    }


    class ExceptionHandler implements Thread.UncaughtExceptionHandler {
        //一旦出现未捕获异常,就会走到此方法中
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            LogUtil.i("发现一个未处理的异常", "");
            ex.printStackTrace();
            //收集错误日志, 自动将错误日志文件上传到服务器
            PrintWriter writer;
            try {
                writer = new PrintWriter(new File(Environment
                        .getExternalStorageDirectory().getAbsolutePath()
                        + "/err.txt"));
                ex.printStackTrace(writer);
                writer.flush();
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //结束当前进程, 闪退
            //System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}