package com.pang.devkit.utils;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.pang.devkit.R;


public class PopWindowUtil {
    public static void setPopupWindowCenter(final Activity activity, View view, final PopupWindow popWnd, float bgAlpha, int width, int height) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);

        WindowManager wm = activity.getWindowManager();//窗口管理者对象
        Display display = wm.getDefaultDisplay();//获取display
        popWnd.setContentView(view);//泡泡框加载布局
        popWnd.setWidth((int) (display.getWidth() * width / 10));//设置宽度
        popWnd.setHeight((int) (display.getHeight() * height / 10));//高度自适应
        popWnd.setBackgroundDrawable(new ColorDrawable(0));//泡泡框背景透明
        popWnd.setAnimationStyle(R.style.pop_animation_center);//泡泡框动画
        popWnd.setOutsideTouchable(false);//不可点击外面
        popWnd.setFocusable(true);//避免popwindow和其他控件点击冲突
//        popWnd.showAtLocation(view, Gravity.CENTER, 0, 0);//显示位置
        new Handler().postDelayed(new Runnable() {
            public void run() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!activity.isFinishing()) {
                            popWnd.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                        }
                    }
                });
            }
        }, 200L);

//        popWnd.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
//                lp.alpha = 1; //0.0-1.0
//                activity.getWindow().setAttributes(lp);
//            }
//        });


    }

    public static void setPopupWindowRBCenter(final Activity activity, View view, final PopupWindow popWnd, float bgAlpha, int width, double height) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);

        WindowManager wm = activity.getWindowManager();//窗口管理者对象
        Display display = wm.getDefaultDisplay();//获取display
        popWnd.setContentView(view);//泡泡框加载布局
        popWnd.setWidth((int) (display.getWidth() * width / 10));//设置宽度
        popWnd.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);//高度自适应//20220524修改   //old//display.getHeight() * height / 10
        popWnd.setBackgroundDrawable(new ColorDrawable(0));//泡泡框背景透明
        popWnd.setAnimationStyle(R.style.pop_animation_center);//泡泡框动画
        popWnd.setOutsideTouchable(false);//不可点击外面
        popWnd.setFocusable(true);//避免popwindow和其他控件点击冲突
//        popWnd.showAtLocation(view, Gravity.CENTER, 0, 0);//显示位置
        new Handler().postDelayed(new Runnable() {
            public void run() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!activity.isFinishing()) {
                            popWnd.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                        }
                    }
                });
            }
        }, 200L);

//        popWnd.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
//                lp.alpha = 1; //0.0-1.0
//                activity.getWindow().setAttributes(lp);
//            }
//        });


    }
}
