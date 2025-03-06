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
 * Created by hoozy on 2022/5/30
 * Describe:
 * 实时详情信息中 模块展示
 */
public class ActualModuleView extends LinearLayout {

    private String mTitle;
    private String mTitleValue;
    private String mBottomTitleUnit;
    private String mTitleUnit;
    private String mBottomTitle;
    private String mBottomTitleValue;
    private int mBottomValueTextColor;
    private int mBottomUnitTextColor;
    private TextView mTitleView;
    private TextView mTitleValueView;
    private TextView mTitleUnitView;
    private TextView mBottomTitleView;
    private TextView mBottomTitleValueView;
    private TextView mBottomTitleUnitView;
    private LinearLayout llBottomLayout;
    private Boolean showBottomLayout;

    public void setTitle(String mTitle) {
        mTitleView.setText(mTitle);
        this.mTitle = mTitle;
    }

    public void setTitleValue(String mTitleValue) {
        mTitleValueView.setText(mTitleValue);
        this.mTitleValue = mTitleValue;
    }

    public void setBottomTitleUnit(String mBottomTitleUnit) {
        mBottomTitleUnitView.setText(mBottomTitleUnit);
        this.mBottomTitleUnit = mBottomTitleUnit;
    }

    public void setTitleUnit(String mTitleUnit) {
        mTitleUnitView.setText(mTitleUnit);
        this.mTitleUnit = mTitleUnit;
    }

    public void setBottomTitle(String mBottomTitle) {
        mBottomTitleView.setText(mBottomTitle);
        this.mBottomTitle = mBottomTitle;
    }

    public void setBottomTitleValue(String mBottomTitleValue) {
        mBottomTitleValueView.setText(mBottomTitleValue);
        this.mBottomTitleValue = mBottomTitleValue;
    }

    public void setBottomValueTextColor(int mBottomValueTextColor) {
        mBottomTitleValueView.setTextColor(mBottomValueTextColor);
        this.mBottomValueTextColor = mBottomValueTextColor;
    }

    public void setBottomUnitTextColor(int mBottomUnitTextColor) {
        mBottomTitleUnitView.setTextColor(mBottomUnitTextColor);
        this.mBottomUnitTextColor = mBottomUnitTextColor;
    }

    public ActualModuleView(Context context) {
        this(context, null);
    }

    public ActualModuleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        /**
         *  <attr name="actualTitle" format="string" />
         *         <attr name="actualTitleValue" format="string" />
         *         <attr name="actualTitleValueUnit" format="string" />
         *         <attr name="actualBottomTitle" format="string" />
         *         <attr name="actualBottomTitleValue" format="string" />
         *         <attr name="actualBottomTitleValueTextColor" format="color" />
         *         <attr name="actualBottomTitleUnit" format="string" />
         *         <attr name="actualBottomTitleUnitTextColor" format="color" />
         */

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ActualModuleView);
        mTitle = array.getString(R.styleable.ActualModuleView_actualTitle);
        mTitleValue = array.getString(R.styleable.ActualModuleView_actualTitleValue);
        mTitleUnit = array.getString(R.styleable.ActualModuleView_actualTitleValueUnit);

        mBottomTitle = array.getString(R.styleable.ActualModuleView_actualBottomTitle);
        mBottomTitleValue = array.getString(R.styleable.ActualModuleView_actualBottomTitleValue);
        mBottomTitleUnit = array.getString(R.styleable.ActualModuleView_actualBottomTitleUnit);

        mBottomValueTextColor = array.getColor(R.styleable.ActualModuleView_actualBottomTitleValueTextColor, Color.parseColor("#E93A29"));
        mBottomUnitTextColor = array.getColor(R.styleable.ActualModuleView_actualBottomTitleUnitTextColor, Color.parseColor("#E93A29"));

        showBottomLayout = array.getBoolean(R.styleable.ActualModuleView_actualBottomTitlesShow, true);

        array.recycle();
        initView(context);
    }


    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_chart_modul_info, this, false);
        mTitleView = (TextView) view.findViewById(R.id.tv_title);
        mTitleValueView = (TextView) view.findViewById(R.id.tv_title_value);
        mTitleUnitView = (TextView) view.findViewById(R.id.tv_title_unit);
        mBottomTitleView = (TextView) view.findViewById(R.id.tv_bottom_title);
        mBottomTitleValueView = (TextView) view.findViewById(R.id.tv_bottom_title_value);
        mBottomTitleUnitView = (TextView) view.findViewById(R.id.tv_bottom_title_unit);
        llBottomLayout = (LinearLayout) view.findViewById(R.id.ll_bottom_layout);

        setDefaultTextString(mTitle, mTitleView);
        setDefaultTextString(mTitleValue, mTitleValueView);
        setDefaultTextString(mTitleUnit, mTitleUnitView);
        setDefaultTextString(mBottomTitle, mBottomTitleView);
        setDefaultTextString(mBottomTitleValue, mBottomTitleValueView);
        setDefaultTextString(mBottomTitleUnit, mBottomTitleUnitView);

        setDefaultTextColor(mBottomValueTextColor, mBottomTitleValueView);
        setDefaultTextColor(mBottomUnitTextColor, mBottomTitleUnitView);

        if (showBottomLayout) {
            llBottomLayout.setVisibility(VISIBLE);
        } else {
            llBottomLayout.setVisibility(GONE);
        }

        addView(view);
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
