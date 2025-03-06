package com.pang.devkit.view.actualtime;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
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
 * Created by hoozy on 2022/5/31
 * Describe:
 */
public class BlueBgModuleView extends LinearLayout {

    private String topText;
    private String bottomText;
    private int topTextColor;
    private int bottomTextColor;
    private TextView mViewValue, mViewTitleAndUnit;

    public BlueBgModuleView(Context context) {
        this(context, null);
    }

    public BlueBgModuleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BlueBgModuleView);
        topText = array.getString(R.styleable.BlueBgModuleView_blueBgViewTopText);
        bottomText = array.getString(R.styleable.BlueBgModuleView_blueBgViewBottomText);

        topTextColor = array.getColor(R.styleable.BlueBgModuleView_blueBgViewTopTextColor, Color.parseColor("#333333"));
        bottomTextColor = array.getColor(R.styleable.BlueBgModuleView_blueBgViewBottomTextColor, Color.parseColor("#333333"));

        array.recycle();
        initView(context);
    }

    private void initView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_blue_bg_module, this, false);
        mViewValue = (TextView) inflate.findViewById(R.id.tv_value);
        mViewTitleAndUnit = (TextView) inflate.findViewById(R.id.tv_title_and_unit);

        setDefaultTextString(topText, mViewValue);
        setDefaultTextString(bottomText, mViewTitleAndUnit);

        setDefaultTextColor(topTextColor, mViewValue);
        setDefaultTextColor(bottomTextColor, mViewTitleAndUnit);

        addView(inflate);
    }

    /**
     * textView默认颜色
     */
    private void setDefaultTextColor(int co, TextView view) {
        if (Color.BLACK != co) {
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
        if (12 != size) {
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        }
    }
}
