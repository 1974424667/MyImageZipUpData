package com.yl.myimageupdata.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * Description: 提示语的工具类
 * Copyright  : Copyright (c) 2017
 * Company    : 大麦科技
 * Author     : yl
 * Date       : 2017/9/30
 */
public class ToastUtils {
    private static Toast TOAST;

    //可以在主线程中toast
    public static void showToast(Context ctx, String text) {
        if (TOAST == null) {
            TOAST = Toast.makeText(ctx, text, Toast.LENGTH_SHORT);
        } else {
            TOAST.setText(text);
            TOAST.setDuration(Toast.LENGTH_SHORT);
        }
        TOAST.show();
    }

    //可以在子线程中toast
    public static void showToastOnUIThread(final Activity activity,
                                           final String text) {
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
