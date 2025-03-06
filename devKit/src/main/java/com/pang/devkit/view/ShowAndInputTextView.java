package com.pang.devkit.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
 * 由hoozy于2023/3/29 14:09进行创建
 * 描述：左侧标题   右边显示或者输入
 */
public class ShowAndInputTextView extends LinearLayout {

    private String leftTitleText;
    private String rightShowText;
    private String rightEditText;
    private int leftTextSize;
    private int rightTextSize;
    private int rightEditTextSize;
    private int leftTextColor;
    private int rightTextColor;
    private int rightEditTextColor;
    private int rightTextType;
    private Drawable rightEditTextBackGround;
    private TextView leftTextView;
    private TextView rightTextView;
    private EditText rightEditTextView;
    private Boolean rightEditTextFocusable;

    public TextView getLeftTextView() {
        return leftTextView;
    }

    public TextView getRightTextView() {
        return rightTextView;
    }

    public EditText getRightEditTextView() {
        return rightEditTextView;
    }

    public ShowAndInputTextView(Context context) {
        this(context, null);
    }

    public ShowAndInputTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShowAndInputTextView);
        leftTitleText = typedArray.getString(R.styleable.ShowAndInputTextView_leftText);
        rightShowText = typedArray.getString(R.styleable.ShowAndInputTextView_rightText);
        rightEditText = typedArray.getString(R.styleable.ShowAndInputTextView_rightEditText);

        leftTextSize = typedArray.getInteger(R.styleable.ShowAndInputTextView_leftTextSize, 14);
        rightTextSize = typedArray.getInteger(R.styleable.ShowAndInputTextView_rightTextSize, 14);
        rightEditTextSize = typedArray.getInteger(R.styleable.ShowAndInputTextView_rightEditSize, 14);

        leftTextColor = typedArray.getColor(R.styleable.ShowAndInputTextView_leftTextColor, Color.parseColor("#ff333333"));
        rightTextColor = typedArray.getColor(R.styleable.ShowAndInputTextView_rightTextColor, Color.parseColor("#ff333333"));
        rightEditTextColor = typedArray.getColor(R.styleable.ShowAndInputTextView_rightEditColor, Color.parseColor("#ff333333"));

        //右侧文字控件模式  显示还是可输入
        rightTextType = typedArray.getInt(R.styleable.ShowAndInputTextView_rightTextType, 0);

        rightEditTextBackGround = typedArray.getDrawable(R.styleable.ShowAndInputTextView_rightEditTextBackground);

        rightEditTextFocusable = typedArray.getBoolean(R.styleable.ShowAndInputTextView_rightEditTextFocusable, true);

        initView(context);

        //释放资源  避免oom
        typedArray.recycle();
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_show_or_input_text_view, this, false);

        leftTextView = (TextView) view.findViewById(R.id.tv_title_name);
        rightTextView = (TextView) view.findViewById(R.id.tv_content_value);
        rightEditTextView = (EditText) view.findViewById(R.id.ev_content_value);

        setDefaultSize(leftTextSize, leftTextView);
        setDefaultSize(rightTextSize, rightTextView);
        setDefaultSize(rightEditTextSize, rightEditTextView);

        setDefaultTextColor(leftTextColor, leftTextView);
        setDefaultTextColor(rightTextColor, rightTextView);
        setDefaultTextColor(rightEditTextColor, rightEditTextView);

        setDefaultTextString(leftTitleText, leftTextView);
        setDefaultTextString(rightShowText, rightTextView);
        setDefaultEvTextHint("请输入" + leftTitleText, rightEditTextView);

        setBackGround(rightEditTextBackGround, rightEditTextView);
        setViewFocusable(rightEditTextFocusable, rightEditTextView);


        switch (rightTextType) {
            case 0:
                rightTextView.setVisibility(VISIBLE);
                rightEditTextView.setVisibility(GONE);
                break;
            case 1:
                rightTextView.setVisibility(GONE);
                rightEditTextView.setVisibility(VISIBLE);
                break;
            default:
                break;
        }

        setOnClickListener();

        addView(view);
    }

    private void setOnClickListener() {
        rightTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showAllText(getContext(), rightTextView);
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

    private void setViewFocusable(boolean b, View view) {
        view.setFocusable(b);
        view.setFocusableInTouchMode(b);
        view.setClickable(b);
    }

    private void setBackGround(Drawable drawable, View view) {
        if (null != drawable) {
            view.setBackground(drawable);
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
     * editTextView默认字体大小控制
     */
    private void setDefaultEvTextHint(String s, TextView view) {
        if (!TextUtils.isEmpty(s) && !"NULL".equals(s) && !"null".equals(s)) {
            if (s.contains("("))
                s = s.split("\\(")[0];
            view.setHint(s);
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

    public String getLeftTitleText() {
        return leftTextView.getText().toString().trim();
    }

    public void setLeftTitleText(String leftTitleText) {
        leftTextView.setText(leftTitleText);
        this.leftTitleText = leftTitleText;
    }

    public String getRightShowText() {
        return rightTextView.getText().toString().trim();
    }

    public void setRightShowText(String rightShowText) {
        rightTextView.setText(rightShowText);
        this.rightShowText = rightShowText;
    }

    public String getRightEditText() {
        return rightEditTextView.getText().toString().trim();
    }

    public void setRightEditText(String rightEditText) {
        rightEditTextView.setText(rightEditText);
        this.rightEditText = rightEditText;
    }
}
