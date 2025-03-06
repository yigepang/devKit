package com.pang.devkit.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
 * Created by hoozy on 2022/6/6
 * Describe:
 */
public class TwoTextLeftView extends LinearLayout {

    private String titleText;
    private String contentText;
    private int titleTextSize;
    private int contentTextSize;
    private int titleTextColor;
    private int contentTextColor;
    private int paddingStart, paddingEnd, paddingTop, paddingBottom;
    private TextView titleTextView;
    private TextView valueTextView;
    private LinearLayout llRoot;
    private boolean toJump = false;
    private boolean titleTextBold = false, contentTextBold = false;

    public TextView getTitleTextView() {
        return titleTextView;
    }

    public void setTitleTextView(TextView titleTextView) {
        this.titleTextView = titleTextView;
    }

    public TextView getValueTextView() {
        return valueTextView;
    }

    public void setValueTextView(TextView valueTextView) {
        this.valueTextView = valueTextView;
    }

    public TwoTextLeftView(Context context) {
        this(context, null);
    }

    public TwoTextLeftView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TwoTextLeftView);
        titleText = array.getString(R.styleable.TwoTextLeftView_leftViewTitleText);
        contentText = array.getString(R.styleable.TwoTextLeftView_leftViewContentText);

        titleTextSize = array.getInteger(R.styleable.TwoTextLeftView_leftViewTitleTextSize, 13);
        contentTextSize = array.getInteger(R.styleable.TwoTextLeftView_leftViewContentTextSize, 13);

        paddingStart = array.getInteger(R.styleable.TwoTextLeftView_rootLinearLayoutPaddingStart, 0);
        paddingEnd = array.getInteger(R.styleable.TwoTextLeftView_rootLinearLayoutPaddingEnd, 0);
        paddingTop = array.getInteger(R.styleable.TwoTextLeftView_rootLinearLayoutPaddingTop, 12);
        paddingBottom = array.getInteger(R.styleable.TwoTextLeftView_rootLinearLayoutPaddingBottom, 12);
        toJump = array.getBoolean(R.styleable.TwoTextLeftView_jumpEnable, true);
        titleTextBold = array.getBoolean(R.styleable.TwoTextLeftView_leftViewTitleTextIsBold, false);
        contentTextBold = array.getBoolean(R.styleable.TwoTextLeftView_leftViewContentTextIsBold, false);

        titleTextColor = array.getColor(R.styleable.TwoTextLeftView_leftViewTitleTextColor, Color.parseColor("#777777"));
        contentTextColor = array.getColor(R.styleable.TwoTextLeftView_leftViewContentTextColor, Color.parseColor("#333333"));

        initView(context);
        array.recycle();
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_two_text_left, this, false);

        titleTextView = (TextView) view.findViewById(R.id.tv_title_name);
        valueTextView = (TextView) view.findViewById(R.id.tv_content_value);
        llRoot = (LinearLayout) view.findViewById(R.id.root_ll);

        llRoot.setPadding(paddingStart, paddingTop, paddingEnd, paddingBottom);

        setDefaultSize(titleTextSize, titleTextView);
        setDefaultSize(contentTextSize, valueTextView);

        setDefaultTextColor(titleTextColor, titleTextView);
        setDefaultTextColor(contentTextColor, valueTextView);

        setDefaultTextString(titleText, titleTextView);
        setDefaultTextString(contentText, valueTextView);
        addView(view);

        if (toJump) {
            setOnClickListener();
        }

        if (titleTextBold) {
            titleTextView.getPaint().setFakeBoldText(true);
        }

        if (contentTextBold) {
            valueTextView.getPaint().setFakeBoldText(true);
        }
    }

    private void setOnClickListener() {
        valueTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showAllText(getContext(), valueTextView);
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

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        valueTextView.setText(contentText);
        this.contentText = contentText;
    }
}
