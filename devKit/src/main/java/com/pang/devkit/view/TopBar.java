package com.pang.devkit.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.pang.devkit.R;


/**
 * Created by hoozy on 2022/3/28
 * Describe:自定义统一标题栏
 */
public class TopBar extends LinearLayout implements View.OnClickListener {

    //左边图标资源
    private int mLeftIvRes;
    //右边图标资源
    private int mRightIvRes;
    //最右侧图标资源
    private int mRightEndIvRes;
    //左侧文字
    private String mLeftString;
    //标题文字
    private String mTitleString;
    //右侧文字
    private String mRightString;
    //右侧中间文字
    private String mRightMiddleString;
    //最右侧文字
    private String mRightEndString;
    //最右侧图标右上角文字
    private String mRightIvTopTvString;
    //最右侧文字颜色
    private int mRightEndTvColor;
    //左侧文字大小
    private float mLeftTvSize;
    //标题文字大小
    private float mTitleTvSize;
    //右侧文字大小
    private float mRightTvSize;
    //右侧中间文字大小
    private float mRightTvMiddleSize;
    //最右侧文字大小
    private float mRightEndTvSize;
    //最右侧图标右上角文字大小
    private float mRightEndIvTopTvSize;

    /**
     * 是否显示view 除个别true，其余false
     */
    private boolean mShowLeftIv; //默认显示 true
    private boolean mShowLeftTv;
    private boolean mShowTitleTv; //默认显示  true
    private boolean mShowRightTv;
    private boolean mShowRightMiddleTv;
    private boolean mShowRightIv;
    private boolean mShowRightEndTv;
    private boolean mShowRightEndIv;
    private boolean mShowRightEndIvTopTv;


    private RelativeLayout mTopBarLayout;
    private TextView mTvCenter;
    private TextView mTvTitle;
    private TextView mTvRight;
    private TextView mTvRightMiddle;
    private TextView mTvRightEnd;
    private TextView mTvIvRightTop;
    private ImageView mIvLeft;
    private ImageView mIvRight;
    private ImageView mIvRightEnd;

    private OnLeftIvClickListener onLeftIvClickListener;
    private OnLeftTvClickListener onLeftTvClickListener;
    private OnRightIvClickListener onRightIvClickListener;
    private OnRightTvClickListener onRightTvClickListener;
    private OnRightMiddleTvClickListener onRightMiddleTvClickListener;
    private OnRightEndIvClickListener onRightEndIvClickListener;
    private OnRightEndTvClickListener onRightEndTvClickListener;

    public TopBar(Context context) {
        this(context, null);
    }

    public TopBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TopBar);
        mLeftIvRes = a.getResourceId(R.styleable.TopBar_left_iv_drawable, 0);
        mRightIvRes = a.getResourceId(R.styleable.TopBar_right_iv_drawable, 0);
        mRightEndIvRes = a.getResourceId(R.styleable.TopBar_right_end_iv_drawable, 0);

        mLeftString = a.getString(R.styleable.TopBar_left_tv_string);
        mTitleString = a.getString(R.styleable.TopBar_title_tv_string);
        mRightString = a.getString(R.styleable.TopBar_right_tv_string);
        mRightMiddleString = a.getString(R.styleable.TopBar_right_tv_middle_string);
        mRightEndString = a.getString(R.styleable.TopBar_right_end_tv_string);
        mRightIvTopTvString = a.getString(R.styleable.TopBar_right_end_iv_top_tv_string);

        mLeftTvSize = a.getInteger(R.styleable.TopBar_left_tv_size, 0);
        mTitleTvSize = a.getInteger(R.styleable.TopBar_title_tv_size, 0);
        mRightTvSize = a.getInteger(R.styleable.TopBar_right_tv_size, 0);
        mRightTvMiddleSize = a.getInteger(R.styleable.TopBar_right_tv_middle_size, 0);
        mRightEndTvSize = a.getInteger(R.styleable.TopBar_right_end_tv_size, 0);
        mRightEndIvTopTvSize = a.getInteger(R.styleable.TopBar_right_end_iv_top_tv_size, 0);

        mRightEndTvColor = a.getColor(R.styleable.TopBar_right_end_tv_color, Color.BLACK);

        mShowLeftIv = a.getBoolean(R.styleable.TopBar_show_left_iv, true);
        mShowLeftTv = a.getBoolean(R.styleable.TopBar_show_left_tv, false);
        mShowTitleTv = a.getBoolean(R.styleable.TopBar_show_title_tv, true);
        mShowRightTv = a.getBoolean(R.styleable.TopBar_show_right_tv, false);
        mShowRightMiddleTv = a.getBoolean(R.styleable.TopBar_show_right_middle_tv, false);
        mShowRightIv = a.getBoolean(R.styleable.TopBar_show_right_iv, false);
        mShowRightEndTv = a.getBoolean(R.styleable.TopBar_show_right_end_tv, false);
        mShowRightEndIv = a.getBoolean(R.styleable.TopBar_show_right_end_iv, false);
        mShowRightEndIvTopTv = a.getBoolean(R.styleable.TopBar_right_end_iv_show_top_tv, false);

        a.recycle();
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_top_bar, this, false);
        mTopBarLayout = (RelativeLayout) view.findViewById(R.id.rl_top_bar);

        mTvCenter = (TextView) view.findViewById(R.id.tv_left);
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mTvRight = (TextView) view.findViewById(R.id.tv_right);
        mTvRightMiddle = (TextView) view.findViewById(R.id.tv_right_middle);
        mTvRightEnd = (TextView) view.findViewById(R.id.tv_right_end);
        mTvIvRightTop = (TextView) view.findViewById(R.id.tv_iv_right_end_top_tv);

        mIvLeft = (ImageView) view.findViewById(R.id.iv_left);
        mIvRight = (ImageView) view.findViewById(R.id.iv_right);
        mIvRightEnd = (ImageView) view.findViewById(R.id.iv_right_end);

        mIvLeft.setOnClickListener(this);
        mTvCenter.setOnClickListener(this);
        mTvTitle.setOnClickListener(this);
        mTvRight.setOnClickListener(this);
        mTvRightMiddle.setOnClickListener(this);
        mTvRightEnd.setOnClickListener(this);
        mIvRight.setOnClickListener(this);
        mIvRightEnd.setOnClickListener(this);

        //设置左侧图标
        if (0 != mLeftIvRes) {
            mIvLeft.setImageResource(mLeftIvRes);
        } else {
            mIvLeft.setImageResource(R.drawable.icon_back);
        }
        //设置右侧图标
        if (0 != mRightIvRes) {
            mIvRight.setImageResource(mRightIvRes);
        }
        //设置最右侧图标
        if (0 != mRightEndIvRes) {
            mIvRightEnd.setImageResource(mRightEndIvRes);
        }

        //设置默认文本
        setDefaultTextString(mLeftString, mTvCenter);
        setDefaultTextString(mTitleString, mTvTitle);
        setDefaultTextString(mRightString, mTvRight);
        setDefaultTextString(mRightMiddleString, mTvRightMiddle);
        setDefaultTextString(mRightEndString, mTvRightEnd);
        setDefaultTextString(mRightIvTopTvString, mTvIvRightTop);

        //设置左侧文本大小
        setDefaultSize(mLeftTvSize, mTvCenter);
        //设置标题文本大小
        setDefaultSize(mTitleTvSize, mTvTitle);
        //设置右侧文本大小
        setDefaultSize(mRightTvSize, mTvRight);
        //设置右侧中间文本大小
        setDefaultSize(mRightTvMiddleSize, mTvRightMiddle);
        //设置最右侧文本大小
        setDefaultSize(mRightEndTvSize, mTvRightEnd);
        //设置最右侧图标右上角标记文本大小
        setDefaultSize(mRightEndIvTopTvSize, mTvIvRightTop);

        //设置初始颜色
        setDefaultTextColor(mRightEndTvColor, mTvRightEnd);

        //设置默认是否显示
        setDefaultShowView(mShowLeftIv, mIvLeft);
        setDefaultShowView(mShowLeftTv, mTvCenter);
        setDefaultShowView(mShowTitleTv, mTvTitle);
        setDefaultShowView(mShowRightTv, mTvRight);
        setDefaultShowView(mShowRightMiddleTv, mTvRightMiddle);
        setDefaultShowView(mShowRightEndTv, mTvRightEnd);
        setDefaultShowView(mShowRightIv, mIvRight);
        setDefaultShowView(mShowRightEndIv, mIvRightEnd);
        setDefaultShowView(mShowRightEndIvTopTv, mTvIvRightTop);

        //将布局设置到layout,以显示出布局
        addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * textView文本颜色
     */
    private void setDefaultTextColor(int color, TextView textView) {
        if (Color.BLACK != color) {
            textView.setTextColor(color);
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
        int id = v.getId();
        if (id == R.id.iv_left) {
            if (null != onLeftIvClickListener) {
                onLeftIvClickListener.click();
            }
        } else if (id == R.id.tv_left) {
            if (null != onLeftTvClickListener) {
                onLeftTvClickListener.click();
            }
        } else if (id == R.id.tv_right) {
            if (null != onRightTvClickListener) {
                onRightTvClickListener.click();
            }
        } else if (id == R.id.iv_right) {
            if (null != onRightIvClickListener) {
                onRightIvClickListener.click();
            }
        } else if (id == R.id.tv_right_middle) {
            if (null != onRightMiddleTvClickListener) {
                onRightMiddleTvClickListener.click();
            }
        } else if (id == R.id.tv_right_end) {
            if (null != onRightEndTvClickListener) {
                onRightEndTvClickListener.click();
            }
        } else if (id == R.id.iv_right_end) {
            if (null != onRightEndIvClickListener) {
                onRightEndIvClickListener.click();
            }
        }
    }

    /**
     * 左侧图标点击事件
     */
    public interface OnLeftIvClickListener {
        void click();
    }

    /**
     * 左侧btn点击事件
     */
    public interface OnLeftTvClickListener {
        void click();
    }

    /**
     * 右侧btn点击事件
     */
    public interface OnRightTvClickListener {
        void click();
    }

    /**
     * 右侧btn中间点击事件
     */
    public interface OnRightMiddleTvClickListener {
        void click();
    }

    /**
     * 最右侧btn点击事件
     */
    public interface OnRightEndTvClickListener {
        void click();
    }

    /**
     * 右侧图标点击事件
     */
    public interface OnRightIvClickListener {
        void click();
    }

    /**
     * 最右侧图标点击事件
     */
    public interface OnRightEndIvClickListener {
        void click();
    }

    public void setOnLeftIvClickListener(OnLeftIvClickListener onLeftIvClickListener) {
        this.onLeftIvClickListener = onLeftIvClickListener;
    }

    public void setOnLeftTvClickListener(OnLeftTvClickListener onLeftTvClickListener) {
        this.onLeftTvClickListener = onLeftTvClickListener;
    }

    public void setOnRightIvClickListener(OnRightIvClickListener onRightIvClickListener) {
        this.onRightIvClickListener = onRightIvClickListener;
    }

    public void setOnRightTvClickListener(OnRightTvClickListener onRightTvClickListener) {
        this.onRightTvClickListener = onRightTvClickListener;
    }

    public void setOnRightMiddleTvClickListener(OnRightMiddleTvClickListener onRightMiddleTvClickListener) {
        this.onRightMiddleTvClickListener = onRightMiddleTvClickListener;
    }

    public void setOnRightEndIvClickListener(OnRightEndIvClickListener onRightEndIvClickListener) {
        this.onRightEndIvClickListener = onRightEndIvClickListener;
    }

    public void setOnRightEndTvClickListener(OnRightEndTvClickListener onRightEndTvClickListener) {
        this.onRightEndTvClickListener = onRightEndTvClickListener;
    }

    /**
     * 设置topBar背景颜色
     *
     * @param color
     */
    public void setBackGroundColor(int color) {
        mTopBarLayout.setBackgroundColor(color);
    }

    /**
     * 左侧图标资源
     */
    public void setLeftIvRes(int mLeftIvRes) {
        if (mIvLeft.getVisibility() == GONE) {
            mIvLeft.setVisibility(VISIBLE);
        }
        mIvLeft.setImageResource(mLeftIvRes);
    }

    /**
     * 右侧图标资源
     */
    public void setRightIvRes(int mRightIvRes) {
        if (mIvRight.getVisibility() == GONE) {
            mIvRight.setVisibility(VISIBLE);
        }
        mIvRight.setImageResource(mRightIvRes);
    }

    /**
     * 最右侧图标资源
     */
    public void setRightEndIvRes(int mRightEndIvRes) {
        if (mIvRightEnd.getVisibility() == GONE) {
            mIvRightEnd.setVisibility(VISIBLE);
        }
        mIvRightEnd.setImageResource(mRightEndIvRes);
    }

    /**
     * 左侧文本
     */
    public void setLeftString(String mLeftString) {
        if (mTvCenter.getVisibility() == GONE) {
            mTvCenter.setVisibility(VISIBLE);
        }
        mTvCenter.setText(mLeftString);
    }

    /**
     * 标题文本
     */
    public void setTitleString(String mTitleString) {
        if (mTvTitle.getVisibility() == GONE) {
            mTvTitle.setVisibility(VISIBLE);
        }
        mTvTitle.setText(mTitleString);
    }

    /**
     * 右侧文本
     */
    public void setRightString(String mRightString) {
        if (mTvRight.getVisibility() == GONE) {
            mTvRight.setVisibility(VISIBLE);
        }
        mTvRight.setText(mRightString);
    }

    /**
     * 右侧中间文本
     */
    public void setRightMiddleString(String rightMiddleString) {
        if (mTvRightMiddle.getVisibility() == GONE) {
            mTvRightMiddle.setVisibility(VISIBLE);
        }
        mTvRightMiddle.setText(rightMiddleString);
    }

    /**
     * 最右侧文本
     */
    public void setRightEndString(String mRightEndString) {
        if (mTvRightEnd.getVisibility() == GONE) {
            mTvRightEnd.setVisibility(VISIBLE);
        }
        mTvRightEnd.setText(mRightEndString);
    }

    /**
     * 最右侧图标右上角文本（标记红点view）
     */
    public void setRightIvTopTvString(String mRightIvTopTvString) {
        if (mTvIvRightTop.getVisibility() == GONE) {
            mTvIvRightTop.setVisibility(VISIBLE);
        }
        mTvIvRightTop.setText(mRightIvTopTvString);
    }

    /**
     * 左侧文本大小
     */
    public void setLeftTvSize(float mLeftTvSize) {
        mTvCenter.setTextSize(mLeftTvSize);
    }

    /**
     * 标题文本大小
     */
    public void setTitleTvSize(float mTitleTvSize) {
        mTvTitle.setTextSize(mTitleTvSize);
    }

    /**
     * 右侧文本大小
     */
    public void setRightTvSize(float mRightTvSize) {
        mTvRight.setTextSize(mRightTvSize);
    }

    /**
     * 右侧中间文字大小
     */
    public void setRightMiddleTvSize(float mRightMiddleTvSize) {
        mTvRightMiddle.setTextSize(mRightMiddleTvSize);
    }

    /**
     * 最右侧文本大小
     */
    public void setRightEndTvSize(float mRightEndTvSize) {
        mTvRightEnd.setTextSize(mRightEndTvSize);
    }

    /**
     * 最右侧图标右上角文本大小（红点标记view）
     */
    public void setRightEndIvTopTvSize(float mRightEndIvTopTvSize) {
        mTvIvRightTop.setTextSize(mRightEndIvTopTvSize);
    }

    /**
     * 是否显示左侧图标
     */
    public void isShowLeftIv(boolean mShowLeftIv) {
        if (mShowLeftIv) {
            mIvLeft.setVisibility(VISIBLE);
        } else {
            mIvLeft.setVisibility(GONE);
        }
    }

    /**
     * 是否显示左侧文本
     */
    public void isShowLeftTv(boolean mShowLeftTv) {
        if (mShowLeftTv) {
            mTvCenter.setVisibility(VISIBLE);
        } else {
            mTvCenter.setVisibility(GONE);
        }
    }

    /**
     * 是否显示标题
     */
    public void isShowTitleTv(boolean mShowTitleTv) {
        if (mShowTitleTv) {
            mTvTitle.setVisibility(VISIBLE);
        } else {
            mTvTitle.setVisibility(GONE);
        }
    }

    /**
     * 是否显示右侧文本
     */
    public void isShowRightTv(boolean mShowRightTv) {
        if (mShowRightTv) {
            mTvRight.setVisibility(VISIBLE);
        } else {
            mTvRight.setVisibility(GONE);
        }
    }

    /**
     * 是否显示右侧文本
     */
    public void isShowRightMiddleTv(boolean showRightMiddleTv) {
        if (showRightMiddleTv) {
            mTvRightMiddle.setVisibility(VISIBLE);
        } else {
            mTvRightMiddle.setVisibility(GONE);
        }
    }

    /**
     * 是否显示右侧图标
     */
    public void isShowRightIv(boolean mShowRightIv) {
        if (mShowRightIv) {
            mIvRight.setVisibility(VISIBLE);
        } else {
            mIvRight.setVisibility(GONE);
        }
    }

    /**
     * 是否显示最右侧文本
     */
    public void isShowRightEndTv(boolean mShowRightEndTv) {
        if (mShowRightEndTv) {
            mTvRightEnd.setVisibility(VISIBLE);
        } else {
            mTvRightEnd.setVisibility(GONE);
        }
    }

    /**
     * 是否显示最右侧图标
     */
    public void isShowRightEndIv(boolean mShowRightEndIv) {
        if (mShowRightEndIv) {
            mIvRightEnd.setVisibility(VISIBLE);
        } else {
            mIvRightEnd.setVisibility(GONE);
        }
    }

    /**
     * 是否显示最右侧图标右上角标记
     */
    public void isShowRightEndIvTopTv(boolean mShowRightEndIvTopTv) {
        if (mShowRightEndIvTopTv) {
            mTvIvRightTop.setVisibility(VISIBLE);
        } else {
            mTvIvRightTop.setVisibility(GONE);
        }
    }

    public TextView getmTvRightEnd() {
        return mTvRightEnd;
    }

    public void setmTvRightEnd(TextView mTvRightEnd) {
        this.mTvRightEnd = mTvRightEnd;
    }

    public String getTitleValue() {
        return mTvTitle.getText().toString();
    }
}
