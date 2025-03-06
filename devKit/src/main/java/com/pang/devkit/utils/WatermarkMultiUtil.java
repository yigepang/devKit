package com.pang.devkit.utils;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * 由hoozy于2023/11/10 10:50进行创建
 * 描述：
 */
public class WatermarkMultiUtil {
    /**
     * 水印文本
     */
    private String mText;

    private List<String> labels;
    /**
     * 字体颜色，十六进制形式，例如：0xAEAEAEAE
     */
    private int mTextColor;
    /**
     * 字体大小，单位为sp
     */
    private float mTextSize;
    /**
     * 旋转角度
     */
    private float mRotation;
    private static WatermarkMultiUtil sInstance;

    private WatermarkMultiUtil() {
        mText = "";
        mTextColor = 0xAEAEAEAE;
        mTextSize = 18;
        mRotation = -25;
    }

    public static WatermarkMultiUtil getInstance() {
        if (sInstance == null) {
            synchronized (WatermarkMultiUtil.class) {
                sInstance = new WatermarkMultiUtil();
            }
        }
        return sInstance;
    }

    /**
     * 设置水印文本
     *
     * @param text 文本
     * @return Watermark实例
     */
    public WatermarkMultiUtil setText(String text) {
        mText = text;
        return sInstance;
    }

    /**
     * 设置多行水印文本
     *
     * @return WatermarkMultiUtil实例
     */
    public WatermarkMultiUtil setMultiLine(List<String> lab) {
        labels = lab;
        return sInstance;
    }

    /**
     * 设置字体颜色
     *
     * @param color 颜色，十六进制形式，例如：0xAEAEAEAE
     * @return WatermarkMultiUtil实例
     */
    public WatermarkMultiUtil setTextColor(int color) {
        mTextColor = color;
        return sInstance;
    }

    /**
     * 设置字体大小
     *
     * @param size 大小，单位为sp
     * @return WatermarkMultiUtil实例
     */
    public WatermarkMultiUtil setTextSize(float size) {
        mTextSize = size;
        return sInstance;
    }

    /**
     * 设置旋转角度
     *
     * @param degrees 度数
     * @return WatermarkMultiUtil实例
     */
    public WatermarkMultiUtil setRotation(float degrees) {
        mRotation = degrees;
        return sInstance;
    }

    /**
     * 显示水印，铺满整个页面
     *
     * @param activity 活动
     */
    public void show(Activity activity) {
        show(activity, mText);
    }

    /**
     * 显示水印，铺满整个页面
     *
     * @param activity 活动
     * @param text     水印
     */
    public void show(Activity activity, String text) {
        WatermarkDrawable drawable = new WatermarkDrawable();
        drawable.mText = text;
        drawable.mTextColor = mTextColor;
        drawable.mTextSize = mTextSize;
        drawable.mRotation = mRotation;

        ViewGroup rootView = activity.findViewById(android.R.id.content);
        FrameLayout layout = new FrameLayout(activity);
        layout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setBackground(drawable);
        layout.setAlpha(0.3f);
        rootView.addView(layout);
    }

    private class WatermarkDrawable extends Drawable {
        private Paint mPaint;
        /**
         * 水印文本
         */
        private String mText;

        /**
         * 字体颜色，十六进制形式，例如：0xAEAEAEAE
         */
        private int mTextColor;
        /**
         * 字体大小，单位为sp
         */
        private float mTextSize;
        /**
         * 旋转角度
         */
        private float mRotation;

        private WatermarkDrawable() {
            mPaint = new Paint();
        }

        @Override
        public void draw(@NonNull Canvas canvas) {

            if (labels != null && labels.size() > 0) {
                //多行文本
                int width = getBounds().right;
                int height = getBounds().bottom;

                canvas.drawColor(0x00000000);
                mPaint.setColor(mTextColor);

                mPaint.setAntiAlias(true);
                mPaint.setTextSize(ConvertUtils.spToPx(mTextSize));
                canvas.save();
                canvas.rotate(mRotation);
                //计算集合中最长的宽度
                float textWidth = 0;
                for (int i = 0; i < labels.size(); i++) {
                    if (textWidth < mPaint.measureText(labels.get(i))) {
                        textWidth = mPaint.measureText(labels.get(i));
                    }
                }
                int index = 0;
                for (int positionY = height / 10; positionY <= height; positionY += height / 10 + 80) {
                    float fromX = -width + (index++ % 2) * textWidth;
                    for (float positionX = fromX; positionX < width; positionX += textWidth * 2) {
                        int spacing = 0;//间距
                        for (String label : labels) {
                            canvas.drawText(label, positionX, positionY + spacing, mPaint);
                            spacing = spacing + 50;
                        }

                    }
                }
                canvas.restore();
            } else if (!TextUtils.isEmpty(mText)) {
                //单行文本
                int width = getBounds().right;
                int height = getBounds().bottom;
                int diagonal = (int) Math.sqrt(width * width + height * height); // 对角线的长度

                mPaint.setColor(mTextColor);
                mPaint.setTextSize(ConvertUtils.spToPx(mTextSize)); // ConvertUtils.spToPx()这个方法是将sp转换成px，ConvertUtils这个工具类在我提供的demo里面有
                mPaint.setAntiAlias(true);
                float textWidth = mPaint.measureText(mText);

                canvas.drawColor(0x00000000);
                canvas.rotate(mRotation);

                int index = 0;
                float fromX;
                // 以对角线的长度来做高度，这样可以保证竖屏和横屏整个屏幕都能布满水印
                for (int positionY = diagonal / 10; positionY <= diagonal; positionY += diagonal / 10) {
                    fromX = -width + (index++ % 2) * textWidth; // 上下两行的X轴起始点不一样，错开显示
                    for (float positionX = fromX; positionX < width; positionX += textWidth * 2) {
                        canvas.drawText(mText, positionX, positionY, mPaint);
                    }
                }

                canvas.save();
                canvas.restore();
            }

        }

        @Override
        public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
        }

        @Override
        public void setColorFilter(@Nullable ColorFilter colorFilter) {
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }

    }
}
