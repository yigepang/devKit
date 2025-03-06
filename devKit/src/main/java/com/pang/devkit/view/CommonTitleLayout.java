package com.pang.devkit.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pang.devkit.R;

/**
 * Created by hoozy on 2022/3/31
 * Describe:常用的小标题头 布局
 */
public class CommonTitleLayout extends RelativeLayout implements View.OnClickListener {
    private int rightTextBackground;
    private int rightTextDrawRight;
    private String leftTitle;
    private String rightText;
    private float leftLineWidth;
    private float leftLineHeight;
    private int leftLineColor;
    private int leftTitleTextColor;
    private int rightTextColor;
    private int leftTitleTextSize;
    private boolean toJump = false;
    private RelativeLayout rightRelayout;

    public void setLeftTitleSingleLineFalse() {
        leftTitleTextView.setSingleLine(false);
    }

    public void setRightTextGone() {
        rightTextView.setVisibility(GONE);
    }

    public void setRightTextBackground(int rightTextBackground) {
        rightTextView.setBackgroundResource(rightTextBackground);
    }

    public void setLeftTitleText(String leftTitle) {
        leftTitleTextView.setText(leftTitle);
    }

    public void setRightText(String rightText) {
        rightTextView.setText(rightText);
    }

    public void setLeftLineWidth(float leftLineWidth) {
        this.leftLineWidth = leftLineWidth;
    }

    public void setLeftLineHeight(float leftLineHeight) {
        this.leftLineHeight = leftLineHeight;
    }

    public void setLeftLineColor(int leftLineColor) {
        leftLineView.setBackgroundColor(leftLineColor);
    }

    public void setLeftTitleTextColor(int leftTitleTextColor) {
        leftTitleTextView.setTextColor(leftTitleTextColor);
    }

    public void setRightTextColor(int rightTextColor) {
        rightTextView.setTextColor(rightTextColor);
    }

    public void setLeftTitleTextSize(int leftTitleTextSize) {
        leftTitleTextView.setTextSize(leftTitleTextSize);
    }

    public void setRightTextSize(int rightTextSize) {
        rightTextView.setTextSize(rightTextSize);
    }

    public void setShowLeftLine(boolean showLeftLine) {
        if (showLeftLine) {
            leftLineView.setVisibility(VISIBLE);
        } else {
            leftLineView.setVisibility(GONE);
        }
    }

    private int rightTextSize;
    private boolean isShowLeftLine;
    private View leftLineView;
    private TextView leftTitleTextView;
    private TextView rightTextView;
    private OnRightTextClick onRightTextClick;

    public TextView getRightTextView() {
        return rightTextView;
    }

    public void setRightTextView(TextView rightTextView) {
        this.rightTextView = rightTextView;
    }

    public void setOnRightTextClick(OnRightTextClick onRightTextClick) {
        this.onRightTextClick = onRightTextClick;
    }

    public CommonTitleLayout(Context context) {
        this(context, null);
    }

    @SuppressLint("NewApi")
    public CommonTitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CommonTitleLayout);
        //右侧文字背景
        rightTextBackground = a.getResourceId(R.styleable.CommonTitleLayout_rightTextBackground, 0);
        rightTextDrawRight = a.getResourceId(R.styleable.CommonTitleLayout_rightTextDrawRight, 0);
        //文本文字
        leftTitle = a.getString(R.styleable.CommonTitleLayout_titleText);
        rightText = a.getString(R.styleable.CommonTitleLayout_rightText);
        //左侧线条宽高
        leftLineWidth = a.getDimension(R.styleable.CommonTitleLayout_leftLineWidth, 0);
        leftLineHeight = a.getDimension(R.styleable.CommonTitleLayout_leftLineHeight, 0);
        //文本文字的颜色
        leftLineColor = a.getColor(R.styleable.CommonTitleLayout_leftLineColor, context.getColor(R.color.color_0F6EFF));
        leftTitleTextColor = a.getColor(R.styleable.CommonTitleLayout_titleTextColor, context.getColor(R.color.text_black));
        rightTextColor = a.getColor(R.styleable.CommonTitleLayout_rightTextColor, context.getColor(R.color.text_black));
        //文本文字大小
        leftTitleTextSize = a.getInteger(R.styleable.CommonTitleLayout_titleTextSize, 0);
        rightTextSize = a.getInteger(R.styleable.CommonTitleLayout_rightTextSize, 0);
        //左侧线条是否显示
        isShowLeftLine = a.getBoolean(R.styleable.CommonTitleLayout_showLeftLine, true);

        toJump = a.getBoolean(R.styleable.CommonTitleLayout_jumpEnable, false);

        a.recycle();
        initView(context);
    }

    @SuppressLint("NewApi")
    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_common_title, this, false);

        leftLineView = view.findViewById(R.id.v_left_line_vertical);
        leftTitleTextView = (TextView) view.findViewById(R.id.tv_left_title);
        rightTextView = (TextView) view.findViewById(R.id.tv_right_btn);
        rightRelayout = (RelativeLayout) view.findViewById(R.id.ll_common_title_right_layout);

        setDefaultShowView(isShowLeftLine, leftLineView);

        setDefaultSize(leftTitleTextSize, leftTitleTextView);
        setDefaultSize(rightTextSize, rightTextView);

        setDefaultTextString(leftTitle, leftTitleTextView);
        setDefaultTextString(rightText, rightTextView);

        setDefaultTextColor(leftTitleTextColor, leftTitleTextView);
        setDefaultTextColor(rightTextColor, rightTextView);

        if (0 != leftLineColor) {
            leftLineView.setBackgroundColor(leftLineColor);
        }

        if (0 != rightTextBackground) {
            rightTextView.setBackground(context.getDrawable(rightTextBackground));
        }

        if (0 != rightTextDrawRight) {
            Drawable drawable = context.getResources().getDrawable(rightTextDrawRight);
            rightTextView.setCompoundDrawables(null, null, drawable, null);
        }

        rightRelayout.setOnClickListener(this);

        addView(view);
        if (toJump) {
            setOnClickListener();
        }
    }

    private void setOnClickListener() {
        leftTitleTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showAllText(getContext(), leftTitleTextView);
            }
        });
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_common_title_right_layout) {
            if (null != onRightTextClick) {
                onRightTextClick.onClick();
            }
        }
    }

    public interface OnRightTextClick {
        void onClick();
    }
}
