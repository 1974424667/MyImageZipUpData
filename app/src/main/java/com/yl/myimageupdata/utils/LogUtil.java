package com.yl.myimageupdata.utils;

import android.util.Log;


/**
 * Description: 自定义日志工具
 * Copyright  : Copyright (c) 2017
 * Company    : 大麦科技
 * Author     : yl
 * Date       : 2017/9/30
 */
public class LogUtil {
    /*public static final int VERBOSE = 1;
    public static final int DEBUG = 2;
    public static final int INFO = 3;
    public static final int WARM = 4;
    public static final int ERROR = 5;
    public static final int LEVEL = VERBOSE;*/
    //控制日志是否打印  true为打印日志,false   为不打印日志
    public static final boolean LEVEL = true;

    /* public static void v(String tag, String msg) {
         if (LEVEL <= VERBOSE) {
             Log.v(tag, msg);
         }
     }

     public static void d(String tag, String msg) {
         if (LEVEL <= DEBUG) {
             Log.v(tag, msg);
         }
     }
 /*
     public static void w(String tag, String msg) {
         if (LEVEL <= WARM) {
             Log.v(tag, msg);
         }
     }

     public static void e(String tag, String msg) {
         if (LEVEL <= ERROR) {
             Log.v(tag, msg);
         }
     }*/
    public static void i(String tag, String msg) {
        if (LEVEL) {
            Log.v(tag, msg);
        }
    }
}
