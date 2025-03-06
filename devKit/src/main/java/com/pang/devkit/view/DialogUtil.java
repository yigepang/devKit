package com.pang.devkit.view;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.pang.devkit.R;


public class DialogUtil {

    private AlertDialog dialog;

    private View view;

    private TextView tvMsg;

    private Context context;

    public DialogUtil(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        view = LayoutInflater.from(context).inflate(R.layout.custom_progress_dialog, null);
        tvMsg = view.findViewById(R.id.tv_msg);
        if (dialog == null) {
            dialog = new AlertDialog.Builder(context, R.style.CustomProgressDialog)
                    .setCancelable(true)
                    .setView(view)
                    .create();
        }
    }

    public void showDialog(String msg) {
        if (!dialog.isShowing()) {
            tvMsg.setText(msg);
            dialog.show();
        }
    }

    public void showDialog(String msg, boolean cancel) {
        if (!dialog.isShowing()) {
            dialog.setCancelable(cancel);
            tvMsg.setText(msg);
            dialog.show();
        }
    }

    public void showDialogContainsMsg() {
        if (!dialog.isShowing()) {
            tvMsg.setText("加载中...");
            dialog.show();
        }
    }

    public void showDialogContainsMsg(boolean cancel) {
        if (!dialog.isShowing()) {
            dialog.setCancelable(cancel);
            tvMsg.setText("加载中...");
            dialog.show();
        }
    }

    public boolean isShow() {
        if (dialog != null) {
            return dialog.isShowing();
        }
        return false;
    }

    public void hideDialog() {
        if (null != dialog && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
