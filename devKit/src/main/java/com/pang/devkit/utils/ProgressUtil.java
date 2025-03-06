package com.pang.devkit.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;

/**
 * <p>
 * Description：加载框工具类
 * </p>
 *
 * @author tangzhijie
 */
public class ProgressUtil {

    private ProgressDialog progressDialog;

    public ProgressUtil(Context context) {
        progressDialog = new ProgressDialog(context);
    }

    public void showProgress() {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    public boolean isShow() {
        return this.progressDialog.isShowing();
    }


    public void showProgress(String message) {
        if (!TextUtils.isEmpty(message)) {
            progressDialog.setMessage(message);
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.show();
            }
        }
    }

    /**
     * 加载框
     *
     * @param msg
     * @param cancelAble 是否可以取消加载框
     */
    public void showProgress(String msg, boolean cancelAble) {
        if (!TextUtils.isEmpty(msg)) {
            progressDialog.setMessage(msg);
            if (progressDialog != null && !progressDialog.isShowing()) {
                progressDialog.setCancelable(cancelAble);
                progressDialog.show();
            }
        }
    }

    public void closeProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


}
