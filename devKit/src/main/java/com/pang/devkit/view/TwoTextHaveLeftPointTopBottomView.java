package com.pang.devkit.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.pang.devkit.R;

/**
 * Created by hoozy on 2022/5/21
 * Describe:
 */
public class TwoTextHaveLeftPointTopBottomView extends LinearLayout {
    private String mTopTextString;
    private String mBottomString;
    private int mBottomTextColor;
    private int mTopTextColor;
    private int mLeftPointColor;
    private int mTopTextSize;
    private int mBottomTextSize;
    private TextView mTopTextView;
    private TextView mBottomTextView;
    private View point;

    public void setPointColor(Context context, int pointColor) {
        GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.bg_color_point);
        drawable.setColor(pointColor);
        point.setBackground(drawable);
        this.mLeftPointColor = pointColor;
    }

    public String getTopTextString() {
        return mTopTextString;
    }

    public void setTopTextString(String mTopTextString) {
        mTopTextView.setText(mTopTextString);
        this.mBottomString = mTopTextString;
    }

    public String getBottomString() {
        return mBottomString;
    }

    public void setBottomString(String mBottomString) {
        mBottomTextView.setText(mBottomString);
        this.mBottomString = mBottomString;
    }


    public TwoTextHaveLeftPointTopBottomView(Context context) {
        this(context, null);
    }

    @SuppressLint("NewApi")
    public TwoTextHaveLeftPointTopBottomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TwoTextHaveLeftPointTopBottomView);
        mTopTextString = a.getString(R.styleable.TwoTextHaveLeftPointTopBottomView_topTvString);
        mBottomString = a.getString(R.styleable.TwoTextHaveLeftPointTopBottomView_bottomTvString);

        mBottomTextColor = a.getColor(R.styleable.TwoTextHaveLeftPointTopBottomView_bottomTvColor, context.getColor(R.color.text_black));
        mTopTextColor = a.getColor(R.styleable.TwoTextHaveLeftPointTopBottomView_topTvColor, context.getColor(R.color.color_999999));
        mLeftPointColor = a.getColor(R.styleable.TwoTextHaveLeftPointTopBottomView_leftPointColor, context.getColor(R.color.color_999999));

        mBottomTextSize = a.getInteger(R.styleable.TwoTextHaveLeftPointTopBottomView_bottomTvSize, 0);
        mTopTextSize = a.getInteger(R.styleable.TwoTextHaveLeftPointTopBottomView_topTvSize, 0);

        a.recycle();
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_contains_left_point_two_text, this, false);
        point = view.findViewById(R.id.v_point);
        mTopTextView = (TextView) view.findViewById(R.id.tv_top_text_two_text);
        mBottomTextView = (TextView) view.findViewById(R.id.tv_bottom_text_two_text);

        GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(context, R.drawable.bg_color_point);
        drawable.setColor(mLeftPointColor);
        point.setBackground(drawable);

        setDefaultSize(mBottomTextSize, mBottomTextView);
        setDefaultSize(mTopTextSize, mTopTextView);

        setDefaultTextColor(mTopTextColor, mTopTextView);
        setDefaultTextColor(mBottomTextColor, mBottomTextView);

        setDefaultTextString(mTopTextString, mTopTextView);
        setDefaultTextString(mBottomString, mBottomTextView);

        addView(view);
    }

    private void setDefaultTextColor(int co, TextView view) {
        if (0 != co) {
            view.setTextColor(co);
        }
    }

    /**
     * textView默认文本
     */
    private void setDefaultTextString(String s, TextView view) {
        if (!TextUtils.isEmpty(s)) {
            view.setText(s);
        }
    }

    /**
     * textView默认字体大小控制
     */
    private void setDefaultSize(float size, TextView view) {
        if (0 != size) {
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        }
    }
}
