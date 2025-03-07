package com.pang.devkit.utils.phonecheck;

import android.content.Context;
import android.content.pm.PackageManager;

import java.lang.reflect.Method;

/**
 * 由hoozy于2023/7/4 10:16进行创建
 * 描述：
 */
public class Utilities {
    /**
     * Method to reflectively invoke the SystemProperties.get command - which is the equivalent to the adb shell getProp
     * command.
     *
     * @param context  A {@link Context} object used to get the proper ClassLoader (just needs to be Application Context
     *                 object)
     * @param property A {@code String} object for the property to retrieve.
     * @return {@code String} value of the property requested.
     */
    public static String getProp(Context context, String property) {
        try {
            ClassLoader classLoader = context.getClassLoader();
            Class<?> systemProperties = classLoader.loadClass("android.os.SystemProperties");

            Method get = systemProperties.getMethod("get", String.class);

            Object[] params = new Object[1];
            params[0] = new String(property);

            return (String) get.invoke(systemProperties, params);
        } catch (IllegalArgumentException iAE) {
            iAE.printStackTrace();
            throw iAE;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw null;
        }
    }

    public static boolean hasPackageNameInstalled(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();

        // In theory, if the package installer does not throw an exception, package exists
        try {
            packageManager.getInstallerPackageName(packageName);
            return true;
        } catch (IllegalArgumentException exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
