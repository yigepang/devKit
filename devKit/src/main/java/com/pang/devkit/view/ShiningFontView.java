package com.pang.devkit.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * 由hoozy于2023/8/11 18:10进行创建
 * 描述： 文字闪动效果
 */
public class ShiningFontView extends AppCompatTextView {
    private Paint mPaint;
    private LinearGradient mLinearGradient;
    private Matrix mGradientMatrix;
    private int mViewWidth;
    private int mTranslate;

    public ShiningFontView(Context context) {
        this(context, null);
    }

    public ShiningFontView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShiningFontView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewWidth == 0) {
            mViewWidth = w;
            if (mViewWidth > 0) {
                mPaint = getPaint();
                /***
                 * LinearGradient构造方法中的参数int[] color：
                 * 第一个元素：发光字体闪过后所显示的字体颜色，这里给定与第三个元素一样
                 * 第二个元素：字体发光的颜色 0xffffffff
                 * 第三个元素：原字体显示的颜色
                 * mViewWidth：设置发光的宽度
                 **/
                mLinearGradient = new LinearGradient(0, 0, mViewWidth, 0, new int[]{Color.parseColor("#333333"), Color.parseColor("#FFFFFF"), Color.parseColor("#333333")}, null, Shader.TileMode.CLAMP);
                mPaint.setShader(mLinearGradient);
                mGradientMatrix = new Matrix();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mGradientMatrix != null) {
            mTranslate += mViewWidth / 5;
            if (mTranslate > mViewWidth * 2) {
                mTranslate = -mViewWidth;
            }
            mGradientMatrix.setTranslate(mTranslate, 0);
            mLinearGradient.setLocalMatrix(mGradientMatrix);
            //控制闪过的时间
            postInvalidateDelayed(150);
        }
    }
}

// 3个文字的宽度
// int gradientSize = (int) (textWith / text.length() * 3);
// 从左边-gradientSize开始，即左边距离文字gradientSize开始渐变
// mLinearGradient = new LinearGradient(-gradientSize, 0, 0, 0, new int[]{0x22ffffff, 0xffffffff, 0x22ffffff}, null, Shader.TileMode.CLAMP);
// mPaint.setShader(mLinearGradient);}
// @Override
// protected void onDraw(Canvas canvas) {
// super.onDraw(canvas);
// mTranslate += DELTAX;
// float textWidth = getPaint().measureText(getText().toString());
// 到底部进行返回
// if (mTranslate > textWidth + 1 || mTranslate < 1) {
// DELTAX = -DELTAX;
// }
// mMatrix = new Matrix();
// mMatrix.setTranslate(mTranslate, 0);
// mLinearGradient.setLocalMatrix(mMatrix);
// postInvalidateDelayed(50);
// }
//}


