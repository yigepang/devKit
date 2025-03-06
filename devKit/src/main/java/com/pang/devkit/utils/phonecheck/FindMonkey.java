package com.pang.devkit.utils.phonecheck;

import android.app.ActivityManager;

/**
 * 由hoozy于2023/7/4 10:14进行创建
 * 描述：
 */
public class FindMonkey {
    /**
     * Check if the normal method of "isUserAMonkey"
     * returns a quick win of who the user is.
     *
     * @return {@code true} if the user is a monkey
     * or {@code false} if not.
     */
    public static boolean isUserAMonkey() {
        return ActivityManager.isUserAMonkey();
    }
}
