package com.pang.devkit.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.pang.devkit.R;
import com.pang.devkit.view.carouseltext.utils.DisplayUtils;

/**
 * Created by hoozy on 2022/4/15
 * Describe:
 */
public class CommonText extends LinearLayout implements View.OnClickListener {

    private String mLeftText;
    private String mRightText;
    private int mLeftTextColor;
    private int mRightTextColor;
    private int mLeftTextSize;
    private int mRightTextSize;
    private int mMinHeight;
    private int leftTextBold;
    private TextView tvLeft;
    private TextView tvRight;
    private boolean showBottomLine;
    private View mBottomLine;
    private Context mContext;
    private LinearLayout llRootLayout;

    public TextView getTvLeft() {
        return tvLeft;
    }

    public TextView getTvRight() {
        return tvRight;
    }

    public String getmLeftText() {
        return tvLeft.getText().toString().trim();
    }

    public void setmLeftText(String mLeftText) {
        tvLeft.setText(mLeftText);
    }

    public String getmRightText() {
        return tvRight.getText().toString().trim();
    }

    public void setmRightText(String s) {
        if (s == null || s.length() == 0 || s.equals("null") || s.equals("NULL") || s.equals("NU")) {
            tvRight.setText("");
        } else {
            tvRight.setText(s);
        }
    }

    private OnRightTextClick onRightTextClick;

    public void setOnRightTextClick(OnRightTextClick onRightTextClick) {
        this.onRightTextClick = onRightTextClick;
    }

    public CommonText(Context context) {
        this(context, null);
    }

    public CommonText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CommonText);
        mLeftText = ta.getString(R.styleable.CommonText_setLeftText);
        mRightText = ta.getString(R.styleable.CommonText_setRightText);

        mLeftTextColor = ta.getColor(R.styleable.CommonText_setLeftTextColor, Color.BLACK);
        mRightTextColor = ta.getColor(R.styleable.CommonText_setRightTextColor, Color.BLACK);

        mLeftTextSize = ta.getInteger(R.styleable.CommonText_setLeftTextSize, 12);
        mRightTextSize = ta.getInteger(R.styleable.CommonText_setRightTextSize, 12);

        showBottomLine = ta.getBoolean(R.styleable.CommonText_showBottomLine, true);

        mMinHeight = ta.getInteger(R.styleable.CommonText_layout_min_height, 39);

        leftTextBold = ta.getInt(R.styleable.CommonText_leftTextBold, 0);

        mContext = context;

        initView(context);

        ta.recycle();
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_common_text, this, false);
        tvLeft = (TextView) view.findViewById(R.id.tv_left_text);
        tvRight = (TextView) view.findViewById(R.id.tv_right_content);
        mBottomLine = view.findViewById(R.id.v_bottom_line);
        llRootLayout = view.findViewById(R.id.ll_root_layout);

        llRootLayout.setMinimumHeight(DisplayUtils.dip2px(context, mMinHeight));


        setDefaultTextString(mLeftText, tvLeft);
        setDefaultTextString(mRightText, tvRight);

        setDefaultTextColor(mLeftTextColor, tvLeft);
        setDefaultTextColor(mRightTextColor, tvRight);

        setDefaultSize(mLeftTextSize, tvLeft);
        setDefaultSize(mRightTextSize, tvRight);
        setDefaultShowView(showBottomLine, mBottomLine);

        tvRight.setOnClickListener(this);

        if (leftTextBold == 1) {
            tvLeft.setTypeface(Typeface.DEFAULT_BOLD);
        }

        addView(view);
    }

    /**
     * view默认显示控制
     */
    private void setDefaultShowView(boolean isShow, View view) {
        if (isShow) {
            view.setVisibility(VISIBLE);
        } else {
            view.setVisibility(GONE);
        }
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_right_content) {
            if (null != onRightTextClick) {
                onRightTextClick.onClick();
            } else {
                showAllText(getContext(), tvRight);
            }
        }
    }

    public interface OnRightTextClick {
        void onClick();
    }

    /**
     * 判断文本是否超长有省略，进行弹框处理
     */
    private void showAllText(Context context, TextView tv) {
        int lNum = tv.getLayout().getEllipsisCount(tv.getLineCount() - 1);//判定 >0 则有隐藏
        if (lNum > 0) {
            AlertDialog.Builder ad = new AlertDialog.Builder(context);
            ad.setItems(new String[]{"" + tv.getText().toString().trim()}, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).create().show();
        }
    }
}
