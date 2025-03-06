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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.pang.devkit.R;

/**
 * Created by hoozy on 2022/5/12
 * Describe:
 */
public class TwoTextTopAndBottomView extends LinearLayout {

    private String mTopTextString;
    private String mBottomString;
    private int mBottomTextColor;
    private int mTopTextColor;
    private int mTopTextSize;
    private int mBottomTextSize;
    private TextView mTopTextView;
    private TextView mBottomTextView;
    private Drawable backGround;
    private int paddingStart, paddingEnd, paddingTop, paddingBottom;

    public String getmTopTextString() {
        return mTopTextString;
    }

    public void setmTopTextString(String mTopTextString) {
        mTopTextView.setText(mTopTextString);
        this.mBottomString = mTopTextString;
    }

    public String getmBottomString() {
        return mBottomString;
    }

    public void setmBottomString(String mBottomString) {
        mBottomTextView.setText(mBottomString);
        this.mBottomString = mBottomString;
    }

    public TwoTextTopAndBottomView(Context context) {
        this(context, null);
    }

    @SuppressLint("NewApi")
    public TwoTextTopAndBottomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TwoTextTopAndBottomView);

        mTopTextString = a.getString(R.styleable.TwoTextTopAndBottomView_topTextString);
        mBottomString = a.getString(R.styleable.TwoTextTopAndBottomView_bottomTextString);

        mBottomTextColor = a.getColor(R.styleable.TwoTextTopAndBottomView_bottomTextColor, context.getColor(R.color.text_black));
        mTopTextColor = a.getColor(R.styleable.TwoTextTopAndBottomView_topTextColor, context.getColor(R.color.color_999999));

        mBottomTextSize = a.getInteger(R.styleable.TwoTextTopAndBottomView_bottomTextSize, 0);
        mTopTextSize = a.getInteger(R.styleable.TwoTextTopAndBottomView_topTextSize, 0);

        backGround = a.getDrawable(R.styleable.TwoTextTopAndBottomView_backgroundTopBottom);

        paddingStart = a.getInteger(R.styleable.TwoTextTopAndBottomView_rootLinearLayoutPaddingStart, 0);
        paddingEnd = a.getInteger(R.styleable.TwoTextTopAndBottomView_rootLinearLayoutPaddingEnd, 0);
        paddingTop = a.getInteger(R.styleable.TwoTextTopAndBottomView_rootLinearLayoutPaddingTop, 0);
        paddingBottom = a.getInteger(R.styleable.TwoTextTopAndBottomView_rootLinearLayoutPaddingBottom, 0);

        a.recycle();
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_two_text_top_and_bottom, this, false);
        mTopTextView = (TextView) view.findViewById(R.id.tv_top_text_two_text);
        mBottomTextView = (TextView) view.findViewById(R.id.tv_bottom_text_two_text);

        view.setBackground(backGround);
        view.setPadding(paddingStart, paddingTop, paddingEnd, paddingBottom);

        setDefaultSize(mBottomTextSize, mBottomTextView);
        setDefaultSize(mTopTextSize, mTopTextView);

        setDefaultTextColor(mTopTextColor, mTopTextView);
        setDefaultTextColor(mBottomTextColor, mBottomTextView);

        setDefaultTextString(mTopTextString, mTopTextView);
        setDefaultTextString(mBottomString, mBottomTextView);

        setShowAllTextClick(mBottomTextView);

        addView(view);
    }

    private void setShowAllTextClick(final TextView tv) {
        tv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showAllText(getContext(), tv);
            }
        });
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
