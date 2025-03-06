package com.pang.devkit.base;

import android.content.Context;

import androidx.annotation.NonNull;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.util.ArrayList;
import java.util.List;

public class PermissionTool {
    public static final int STORAGE = 1;
    public static final int LOCATION = 2;
    public static final int INTERNET = 3;
    public static final int CALL = 4;
    public static final int ALL = 5;
    private static volatile PermissionTool instance;

    private PermissionTool() {

    }

    public static PermissionTool getInstance() {
        if (instance == null) {
            synchronized (PermissionTool.class) {
                if (instance == null) {
                    instance = new PermissionTool();
                }
            }
        }
        return instance;
    }

    public void requestPermission(Context context, int type, PermissionListener permissionListener) {
        List<String> permissions = new ArrayList<>();
        switch (type) {
            case STORAGE:
                permissions.add(Permission.WRITE_EXTERNAL_STORAGE);
                permissions.add(Permission.READ_EXTERNAL_STORAGE);
                break;
            case CALL:
                permissions.add(Permission.CALL_PHONE);
                break;
            case LOCATION:
                permissions.add(Permission.ACCESS_FINE_LOCATION);
                permissions.add(Permission.ACCESS_COARSE_LOCATION);
                break;

            case ALL:
                permissions.add(Permission.WRITE_EXTERNAL_STORAGE);
                permissions.add(Permission.READ_EXTERNAL_STORAGE);
                permissions.add(Permission.ACCESS_FINE_LOCATION);
                permissions.add(Permission.ACCESS_COARSE_LOCATION);
                permissions.add(Permission.CALL_PHONE);
                break;

            case INTERNET:

                break;
            default:
                break;
        }
        XXPermissions.with(context).permission(permissions).request(new OnPermissionCallback() {

            @Override
            public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                permissionListener.onGranted(permissions, allGranted);

            }

            @Override
            public void onDenied(@NonNull List<String> permissions, boolean doNotAskAgain) {
                permissionListener.onDenied(permissions, doNotAskAgain);
            }
        });
    }

    public void jumpSettings(Context context) {
        XXPermissions.startPermissionActivity(context);
    }

    public void jumpSettings(Context context, @NonNull List<String> permissions) {
        XXPermissions.startPermissionActivity(context, permissions);
    }

    public interface PermissionListener {
        public void onGranted(@NonNull List<String> permissions, boolean allGranted);

        default void onDenied(@NonNull List<String> permissions, boolean doNotAskAgain) {
        }
    }
}
