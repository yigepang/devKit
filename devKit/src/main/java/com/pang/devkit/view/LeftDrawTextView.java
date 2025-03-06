package com.pang.devkit.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.pang.devkit.R;

public class LeftDrawTextView extends LinearLayout {

    private int mLeftDrawAbleColor;
    private Drawable mLeftDrawable;
    private int mTextColor;
    private int mTextSize;
    private TextView mTextView;

    public LeftDrawTextView(Context context) {
        this(context, null);
    }

    public LeftDrawTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LeftDrawTextView);

        mLeftDrawAbleColor = array.getColor(R.styleable.LeftDrawTextView_left_Drawable, getResources().getColor(R.color.text_black));
        mLeftDrawable = array.getDrawable(R.styleable.LeftDrawTextView_left_Drawable);

        mTextColor = array.getColor(R.styleable.LeftDrawTextView_textColor, getResources().getColor(R.color.text_black));
        mTextSize = array.getInteger(R.styleable.LeftDrawTextView_textSize, 0);

        array.recycle();

        initView(context);
    }

    private void initView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_left_drawable_text_view, this, false);
        mTextView = inflate.findViewById(R.id.tv_text);
        mTextView.setTextSize(mTextSize);
        mTextView.setTextColor(mTextColor);

        mTextView.setCompoundDrawables(mLeftDrawable, null, null, null);

    }

}
