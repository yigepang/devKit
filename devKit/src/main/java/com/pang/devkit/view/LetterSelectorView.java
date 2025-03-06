package com.pang.devkit.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.pang.devkit.R;

import java.util.Arrays;
import java.util.List;

/**
 * 由hoozy于2023/12/27 09:46进行创建
 * 描述：A-Z字母选择器 文本选择器
 */
public class LetterSelectorView extends View {

    private static final double ANGLE_45 = Math.PI * 45 / 100;
    private int mBackgroundColor;
    private int mStrokeColor;
    private int mTextColor;
    private int mTextSize;
    private int mSelectTextColor;
    private int mSelectTextSize;
    private int mHintTextColor;
    private int mHintTextSize;
    private int mHintCircleRadius;
    private int mHintCircleColor;
    private int mWaveColor;
    private int mWaveRadius;
    private int mContentPadding;
    private int mBarPadding;
    private int mBarWidth;

    private List<String> mLetters;
    private RectF mSlideBarRect;
    private TextPaint mTextPaint;
    private Paint mPaint;
    private Paint mWavePaint;
    private Path mWavePath;
    private int mSelect;
    private int mPreSelect;
    private int mNewSelect;
    private ValueAnimator mRatioAnimator;
    private float mAnimationRatio;
    private OnLetterChangeListener mListener;
    private int mTouchY = -1;
    private boolean mIsTouching;
    private static final boolean CONFIG_DRAW_STROKE = false; //配置是否绘制边框
    private static final boolean CONFIG_DRAW_WAVE = false; //配置是否绘制选中波纹

    public LetterSelectorView(Context context) {
        this(context, null);
    }

    public LetterSelectorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterSelectorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttribute(context, attrs, defStyleAttr);
        initData();
    }


    private void initAttribute(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LetterSelectorView, defStyleAttr, 0);
        mBackgroundColor = typedArray.getColor(R.styleable.LetterSelectorView_backgroundColor, getResources().getColor(android.R.color.transparent));
        mStrokeColor = typedArray.getColor(R.styleable.LetterSelectorView_strokeColor, Color.parseColor("#000000"));
        mTextColor = typedArray.getColor(R.styleable.LetterSelectorView_textColor, Color.parseColor("#969696"));
        mSelectTextColor = typedArray.getColor(R.styleable.LetterSelectorView_selectTextColor, Color.parseColor("#FFFFFF"));
        mHintTextColor = typedArray.getColor(R.styleable.LetterSelectorView_hintTextColor, Color.parseColor("#FFFFFF"));
        mHintCircleColor = typedArray.getColor(R.styleable.LetterSelectorView_hintCircleColor, Color.parseColor("#bef9b81b"));
        mWaveColor = typedArray.getColor(R.styleable.LetterSelectorView_waveColor, Color.parseColor("#bef9b81b"));
        mTextSize = typedArray.getDimensionPixelOffset(R.styleable.LetterSelectorView_letterTextSize,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
        mSelectTextSize = typedArray.getDimensionPixelOffset(R.styleable.LetterSelectorView_selectTextSize,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
        mHintTextSize = typedArray.getDimensionPixelOffset(R.styleable.LetterSelectorView_hintTextSize,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        mHintCircleRadius = typedArray.getDimensionPixelOffset(R.styleable.LetterSelectorView_hintCircleRadius,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
        mWaveRadius = typedArray.getDimensionPixelOffset(R.styleable.LetterSelectorView_waveRadius,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics()));
        mContentPadding = typedArray.getDimensionPixelOffset(R.styleable.LetterSelectorView_contentPadding,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
        mBarPadding = typedArray.getDimensionPixelOffset(R.styleable.LetterSelectorView_barPadding,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, getResources().getDisplayMetrics()));
        mBarWidth = typedArray.getDimensionPixelOffset(R.styleable.LetterSelectorView_barWidth,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics()));
        if (mBarWidth == 0) {
            mBarWidth = 2 * mTextSize;
        }
        typedArray.recycle();
    }


    private void initData() {
        String[] arr = new String[]{"10", "9", "8", "7", "6", "5", "4", "3", "2", "1", "0"};
        mLetters = Arrays.asList(arr);
        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mWavePaint = new Paint();
        mWavePaint.setAntiAlias(true);
        mWavePath = new Path();
        mSelect = -1;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mSlideBarRect == null) {
            mSlideBarRect = new RectF();
        }
        float contentLeft = getMeasuredWidth() - mBarWidth - mBarPadding;
        float contentRight = getMeasuredWidth() - mBarPadding;
        float contentTop = mBarPadding;
        float contentBottom = getMeasuredHeight() - mBarPadding;
        mSlideBarRect.set(contentLeft, contentTop, contentRight, contentBottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制slide bar上的字母列表
        drawLetters(canvas);
        //绘制选中时的波纹/圆形效果
        if (CONFIG_DRAW_WAVE) {
            drawWave(canvas);
        }
        //绘制选中时的提示信息（圆+文字）
        drawHint(canvas);
        //绘制选中时的slidebar上的文字
        drawSelect(canvas);

    }

    /**
     * 绘制Slide bar上的字母列表
     */
    private void drawLetters(Canvas canvas) {
        //绘制圆角矩形
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mBackgroundColor);
        canvas.drawRoundRect(mSlideBarRect, mBarWidth / 2.0f, mBarWidth / 2.0f, mPaint);
        //绘制描边
        if (CONFIG_DRAW_STROKE) {
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(mStrokeColor);
            canvas.drawRoundRect(mSlideBarRect, mBarWidth / 2.0f, mBarWidth / 2.0f, mPaint);
        }
        //绘制文字
        float itemHeight = (mSlideBarRect.bottom - mSlideBarRect.top - mContentPadding * 2) / mLetters.size();
        for (int index = 0; index < mLetters.size(); index++) {
            float baseLine = getTextBaseLineByCenter(mSlideBarRect.top + mContentPadding + itemHeight * index + itemHeight / 2,
                    mTextPaint, mTextSize);
            mTextPaint.setColor(mTextColor);
            mTextPaint.setTextSize(mTextSize);
            mTextPaint.setTextAlign(Paint.Align.CENTER);
            float pointX = mSlideBarRect.left + (mSlideBarRect.right - mSlideBarRect.left) / 2.0f;
            canvas.drawText(mLetters.get(index), pointX, baseLine, mTextPaint);
        }
    }

    /**
     * 绘制选中时的波纹效果
     */
    private void drawWave(Canvas canvas) {
        mWavePath.reset();
        //移动到起始点
        int startX = getMeasuredWidth();
        int startY = mTouchY - 3 * mWaveRadius;
        mWavePath.moveTo(startX, startY);
        //计算上部控制点的Y轴位置
        int topControlX = getMeasuredWidth();
        int topControlY = mTouchY - 2 * mWaveRadius;
        int topEndX = (int) (getMeasuredWidth() - mWaveRadius * Math.cos(ANGLE_45) * mAnimationRatio);
        int topEndY = (int) (topControlY + mWaveRadius * Math.sin(ANGLE_45));
        mWavePath.quadTo(topControlX, topControlY, topEndX, topEndY);
        //计算中心控制点的坐标
        int centerControlX = (int) (getMeasuredWidth() - 1.0f * mWaveRadius * mAnimationRatio);
        int centerControlY = mTouchY;
        int centerEndX = topEndX;
        int centerEndY = (int) (mTouchY + 2 * mWaveRadius - mWaveRadius * Math.cos(ANGLE_45));
        mWavePath.quadTo(centerControlX, centerControlY, centerEndX, centerEndY);
        //计算下部借宿点的坐标
        int bottomEndX = getMeasuredWidth();
        int bottomEndY = mTouchY + 3 * mWaveRadius;
        int bottomControlX = getMeasuredWidth();
        int bottomControlY = mTouchY + 2 * mWaveRadius;
        mWavePath.quadTo(bottomControlX, bottomControlY, bottomEndX, bottomEndY);
        mWavePath.close();
        mWavePaint.setStyle(Paint.Style.FILL);
        mWavePaint.setColor(mWaveColor);
        canvas.drawPath(mWavePath, mWavePaint);
    }

    /**
     * 绘制选中时的提示文字（圆+文字）
     */
    private void drawSelect(Canvas canvas) {
        if (mSelect != -1) {
            mTextPaint.setColor(mSelectTextColor);
            mTextPaint.setTextSize(mSelectTextSize);
            mTextPaint.setTextAlign(Paint.Align.CENTER);
            mWavePaint.setStyle(Paint.Style.FILL);
            mWavePaint.setColor(mHintCircleColor);
            float itemHeight = (mSlideBarRect.bottom - mSlideBarRect.top - mContentPadding * 2) / mLetters.size();
            float baseLine = getTextBaseLineByCenter(mSlideBarRect.top + mContentPadding + itemHeight * mSelect + itemHeight / 2,
                    mTextPaint, mTextSize);
            float pointX = mSlideBarRect.left + (mSlideBarRect.right - mSlideBarRect.left) / 2.0f;
            //绘制选中圆形
            canvas.drawCircle(pointX, itemHeight * (mSelect + 1) - mContentPadding * 2, mTextSize / 2.0f + 1, mWavePaint);
            canvas.drawText(mLetters.get(mSelect), pointX, baseLine, mTextPaint);
        }
    }

    /**
     * 绘制选中的Slide Bar上的文字
     */
    private void drawHint(Canvas canvas) {
        //x轴的移动路径
        if (mSelect != -1 && mTouchY != -1) {
            float circleCenterX = (getMeasuredWidth() + mHintCircleRadius) - (2.0f * mWaveRadius + 2.0f * mHintCircleRadius) * mAnimationRatio - 17;
            mWavePaint.setStyle(Paint.Style.FILL);
            mWavePaint.setColor(mHintCircleColor);
            canvas.drawCircle(circleCenterX, mTouchY, mHintCircleRadius, mWavePaint);
            //绘制提示字符
            if (mAnimationRatio >= 0.9f && mSelect != -1) {
                String target = mLetters.get(mSelect);
                float textY = getTextBaseLineByCenter(mTouchY, mTextPaint, mHintTextSize);
                mTextPaint.setColor(mHintTextColor);
                mTextPaint.setTextSize(mHintTextSize);
                mTextPaint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(target, circleCenterX, textY, mTextPaint);
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final float x = event.getX();
        final float y = event.getY();
        mPreSelect = mSelect;
        mNewSelect = (int) (y / (mSlideBarRect.bottom - mSlideBarRect.top) * mLetters.size());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mIsTouching = true;
                mTouchY = (int) y;
                startAnimation(1.0f);
                break;
            case MotionEvent.ACTION_MOVE:
                mTouchY = (int) y;
                if (mPreSelect != mNewSelect && mNewSelect >= 0 && mNewSelect < mLetters.size()) {
                    mSelect = mNewSelect;
                    if (mListener != null) {
                        mListener.onLetterChange(mLetters.get(mNewSelect));
                    }
                }
                invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mIsTouching = false;
                startAnimation(0f);
                mTouchY = -1;
                break;

        }
        return true;
    }

    private void startAnimation(float value) {
        if (mRatioAnimator == null) {
            mRatioAnimator = new ValueAnimator();
        }
        mRatioAnimator.cancel();
        mRatioAnimator.setFloatValues(value);
        mRatioAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimationRatio = (float) animation.getAnimatedValue();
                if (mAnimationRatio == 1f && mPreSelect != mNewSelect) {
                    if (mNewSelect >= 0 && mNewSelect < mLetters.size()) {
                        mSelect = mNewSelect;
                        if (mListener != null) {
                            mListener.onLetterChange(mLetters.get(mNewSelect));
                        }
                    }
                }
                invalidate();
            }
        });
        mRatioAnimator.start();
    }


    /**
     * 判断当前是否在触摸状态
     */
    public boolean isTouching() {
        return mIsTouching;
    }

    /**
     * 给定文字的center获取文字的baseLine
     */
    private float getTextBaseLineByCenter(float center, TextPaint paint, int size) {
        paint.setTextSize(size);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float height = fontMetrics.bottom - fontMetrics.top;
        return center + height / 2 - fontMetrics.bottom;
    }

    public void setOnLetterChangeListener(OnLetterChangeListener listener) {
        this.mListener = listener;
    }

    public interface OnLetterChangeListener {
        void onLetterChange(String letter);
    }
}
