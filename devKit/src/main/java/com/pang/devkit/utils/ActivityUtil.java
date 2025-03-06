package com.pang.devkit.utils;

import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by 36978 on 2018/7/27.
 */

public class ActivityUtil {


    public static boolean isActDestroy(AppCompatActivity activity) {
        if (activity == null) return true;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return activity.isFinishing() || activity.getSupportFragmentManager() == null || activity.getSupportFragmentManager().isDestroyed();
        }
        return activity.isFinishing() || activity.isDestroyed();
    }


}
