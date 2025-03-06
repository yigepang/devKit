package com.pang.devkit.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.pang.devkit.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 由hoozy于2023/12/27 13:47进行创建
 * 描述：文本索引view
 */
public class LetterSelectorCustomView extends View {
    private Paint paintText;
    private Paint paintBackGround;
    private int textColor;
    private int selectBackGroundColor;
    private int textSize;
    private int orientation;
    private int circleDiameter;
    private int itemMeasure;
    private List<String> mLetters = new ArrayList<>();
    private String mCurrentLetter = "";


    public LetterSelectorCustomView(Context context) {
        this(context, null);
    }

    public LetterSelectorCustomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterSelectorCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LetterSelectorCustomView);
        textColor = array.getColor(R.styleable.LetterSelectorCustomView_textColor, Color.parseColor("#333333"));
        selectBackGroundColor = array.getColor(R.styleable.LetterSelectorCustomView_selectBackGroundColor, Color.parseColor("#FEA322"));
        textSize = array.getDimensionPixelOffset(R.styleable.LetterSelectorCustomView_textSizeCustom,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics()));
        orientation = array.getInt(R.styleable.LetterSelectorCustomView_orientation, 0);
        circleDiameter = array.getDimensionPixelOffset(R.styleable.LetterSelectorCustomView_circleDiameter,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));

        initData();

        paintText = new Paint();
        paintText.setColor(textColor);
        paintText.setTextSize(textSize);
        paintText.setAntiAlias(true);//抗锯齿效果 麻刻刻

        paintBackGround = new Paint();
        paintBackGround.setColor(selectBackGroundColor);
    }

    private void initData() {
        String[] arr = new String[]{"10", "9", "8", "7", "6", "5", "4", "3", "2", "1", "0"};
        mLetters.addAll(Arrays.asList(arr));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int textLayoutParams = (int) paintText.measureText("10");
        if (orientation == 0) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = getPaddingTop() + getPaddingBottom() + textLayoutParams;
            setMeasuredDimension(width, height);
        } else {
            int width = getPaddingLeft() + getPaddingRight() + textLayoutParams;
            int height = MeasureSpec.getSize(heightMeasureSpec);
            setMeasuredDimension(width, height);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (orientation == 0) {//横向
            Log.e("he", "onDraw");
            int itemHeight = getHeight() - getPaddingTop() - getPaddingBottom();
            int letterCenterY = itemHeight / 2 + getPaddingTop();
            Paint.FontMetrics fontMetrics = paintText.getFontMetrics();
            int dy = (int) ((fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom);
            int baseLine = letterCenterY + dy;

            int itemWidth = (getWidth() - getPaddingLeft() - getPaddingRight()) / mLetters.size();

            for (int i = 0; i < mLetters.size(); i++) {
                int letterCenterX = itemWidth * i + itemWidth / 2 + getPaddingStart();
                int startX = letterCenterX - (int) (paintText.measureText(mLetters.get(i)) / 2);
                if (mCurrentLetter.equals(mLetters.get(i))) {
                    canvas.drawCircle(letterCenterX, letterCenterY, circleDiameter, paintBackGround);
                    paintText.setColor(Color.parseColor("#FFFFFF"));
                    canvas.drawText(mCurrentLetter, startX, baseLine, paintText);
                } else {
                    paintText.setColor(textColor);
                    canvas.drawText(mLetters.get(i), startX, baseLine, paintText);
                }
            }
        } else {//纵向
            itemMeasure = (getHeight() - getPaddingTop() - getPaddingBottom()) / mLetters.size();
            int letterCenterX = getWidth() / 2;
            for (int i = 0; i < mLetters.size(); i++) {
                int letterCenterY = itemMeasure * i + itemMeasure / 2 + getPaddingTop();
                Paint.FontMetrics fontMetrics = paintText.getFontMetrics();
                int dy = (int) ((fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom);
                int baseLine = letterCenterY + dy;

                int startX = getWidth() / 2 - (int) (paintText.measureText(mLetters.get(i)) / 2);

                if (mCurrentLetter.equals(mLetters.get(i))) {
                    canvas.drawCircle(letterCenterX, letterCenterY, circleDiameter, paintBackGround);
                    paintText.setColor(Color.parseColor("#FFFFFF"));
                    canvas.drawText(mCurrentLetter, startX, baseLine, paintText);
                } else {
                    paintText.setColor(textColor);
                    canvas.drawText(mLetters.get(i), startX, baseLine, paintText);
                }
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (orientation == 0) {
                    float currentMoveY = event.getX() - 40;//测试 触摸位置有差距
                    int itemWidth = (getWidth() - getPaddingLeft() - getPaddingRight()) / mLetters.size();
                    int currentPosition = (int) (currentMoveY / itemWidth);
                    if (currentPosition < 0) {
                        currentPosition = 0;
                    }
                    if (currentPosition > mLetters.size() - 1) {
                        currentPosition = mLetters.size() - 1;
                    }
                    if (!mCurrentLetter.equals(mLetters.get(currentPosition))) {
                        mCurrentLetter = mLetters.get(currentPosition);
                        invalidate();
                    }
                } else {
                    float currentMoveY = event.getY();
                    int itemHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) / mLetters.size();
                    int currentPosition = (int) (currentMoveY / itemHeight);
                    if (currentPosition < 0) {
                        currentPosition = 0;
                    }
                    if (currentPosition > mLetters.size() - 1) {
                        currentPosition = mLetters.size() - 1;
                    }
                    if (!mCurrentLetter.equals(mLetters.get(currentPosition))) {
                        mCurrentLetter = mLetters.get(currentPosition);
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                listener.select(mCurrentLetter);
                break;
        }

        return true;
    }

    /**
     * 获取当前选中内容
     */
    public String getCurrentContent() {
        return mCurrentLetter;
    }

    /**
     * 设置选中内容
     */
    public void setSelectContent(Context context, String selectContent) {
        boolean isHaveContent = false;
        for (int i = 0; i < mLetters.size(); i++) {
            String s = mLetters.get(i);
            if (s.equals(selectContent)) {
                isHaveContent = true;
                break;
            }
        }
        if (isHaveContent) {
            mCurrentLetter = selectContent;
            invalidate();
        } else {
            Toast.makeText(context, "当前内容不在选项内", Toast.LENGTH_LONG).show();
        }
    }

    public void clearStatus() {
        mCurrentLetter = "";
        invalidate();
    }

    /**
     * 设置选择内容
     */
    public void setNewContent(List<String> newList) {
        mLetters.clear();
        mLetters.addAll(newList);
        invalidate();
    }

    public interface OnSelectContentListener {
        void select(String s);
    }

    public OnSelectContentListener listener;

    public void setOnSelectContentListener(OnSelectContentListener listener) {
        this.listener = listener;
    }
}
