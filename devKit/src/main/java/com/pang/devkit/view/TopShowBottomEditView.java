package com.pang.devkit.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.pang.devkit.R;

/**
 * 由hoozy于2024/2/20 13:43进行创建
 * 描述：
 */
public class TopShowBottomEditView extends LinearLayout {
    private String topString, bottomString;
    private int topTextColor, bottomTextColor;
    private int topTextSize, bottomTextSize;
    private Drawable bottomBgDrawable;

    private TextView tvTittle;
    private EditText evContent;

    public TopShowBottomEditView(Context context) {
        this(context, null);
    }

    public TopShowBottomEditView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TopShowBottomEditView);
        topString = array.getString(R.styleable.TopShowBottomEditView_topTextString);
        bottomString = array.getString(R.styleable.TopShowBottomEditView_bottomTextString);

        topTextColor = array.getColor(R.styleable.TopShowBottomEditView_topTextColor, Color.parseColor("#646D76"));
        bottomTextColor = array.getColor(R.styleable.TopShowBottomEditView_bottomTextColor, Color.parseColor("#333333"));

        topTextSize = array.getInteger(R.styleable.TopShowBottomEditView_topTextSize, 12);
        bottomTextSize = array.getInteger(R.styleable.TopShowBottomEditView_bottomTextSize, 12);

        bottomBgDrawable = array.getDrawable(R.styleable.TopShowBottomEditView_bottomTextBackground);

        initView(context);
        array.recycle();
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_top_show_bottom_edit_view, null);
        evContent = (EditText) view.findViewById(R.id.ev_content);
        evContent.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        tvTittle = (TextView) view.findViewById(R.id.tv_title);

        setDefaultSize(topTextSize, tvTittle);
        setDefaultSize(bottomTextSize, evContent);

        setDefaultTextColor(topTextColor, tvTittle);
        setDefaultTextColor(bottomTextColor, evContent);

        setDefaultTextString(topString, tvTittle);
        setDefaultTextString(bottomString, evContent);
        addView(view);
    }

    public String getBottomEditText() {
        return evContent.getText().toString().trim();
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
