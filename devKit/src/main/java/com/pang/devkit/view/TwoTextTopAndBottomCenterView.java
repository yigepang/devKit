package com.pang.devkit.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.pang.devkit.R;

/**
 * Created by hoozy on 2022/6/15
 * Describe:
 */
public class TwoTextTopAndBottomCenterView extends LinearLayout {
    private String mTopTextString;
    private String mBottomString;
    private int mBottomTextColor;
    private int mTopTextColor;
    private int mTopTextSize;
    private int mBottomTextSize;
    private TextView mTopTextView;
    private TextView mBottomTextView;
    private boolean topUseBold;
    private boolean bottomUseBold;

    public TextView getmTopTextView() {
        return mTopTextView;
    }

    public void setmTopTextView(TextView mTopTextView) {
        this.mTopTextView = mTopTextView;
    }

    public TextView getmBottomTextView() {
        return mBottomTextView;
    }

    public void setmBottomTextView(TextView mBottomTextView) {
        this.mBottomTextView = mBottomTextView;
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

    public TwoTextTopAndBottomCenterView(Context context) {
        this(context, null);
    }

    @SuppressLint("NewApi")
    public TwoTextTopAndBottomCenterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TwoTextTopAndBottomCenterView);

        mTopTextString = a.getString(R.styleable.TwoTextTopAndBottomCenterView_topTextStringCenterView);
        mBottomString = a.getString(R.styleable.TwoTextTopAndBottomCenterView_bottomTextStringCenterView);

        mBottomTextColor = a.getColor(R.styleable.TwoTextTopAndBottomCenterView_bottomTextColorCenterView, context.getColor(R.color.text_black));
        mTopTextColor = a.getColor(R.styleable.TwoTextTopAndBottomCenterView_topTextColorCenterView, context.getColor(R.color.color_999999));

        mBottomTextSize = a.getInteger(R.styleable.TwoTextTopAndBottomCenterView_bottomTextSizeCenterView, 0);
        mTopTextSize = a.getInteger(R.styleable.TwoTextTopAndBottomCenterView_topTextSizeCenterView, 0);

        topUseBold = a.getBoolean(R.styleable.TwoTextTopAndBottomCenterView_topUseBold, false);
        bottomUseBold = a.getBoolean(R.styleable.TwoTextTopAndBottomCenterView_BottomUseBold, false);

        a.recycle();
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_two_text_center, this, false);
        mTopTextView = (TextView) view.findViewById(R.id.tv_top_text_two_text);
        mBottomTextView = (TextView) view.findViewById(R.id.tv_bottom_text_two_text);

        setDefaultSize(mBottomTextSize, mBottomTextView);
        setDefaultSize(mTopTextSize, mTopTextView);

        setDefaultTextColor(mTopTextColor, mTopTextView);
        setDefaultTextColor(mBottomTextColor, mBottomTextView);

        setDefaultTextString(mTopTextString, mTopTextView);
        setDefaultTextString(mBottomString, mBottomTextView);
        if (topUseBold) {
            mTopTextView.setTypeface(null, android.graphics.Typeface.BOLD);
        }
        if (bottomUseBold) {
            mBottomTextView.setTypeface(null, android.graphics.Typeface.BOLD);

        }
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
