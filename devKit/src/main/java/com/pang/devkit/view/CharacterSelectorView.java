package com.pang.devkit.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
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
 * 由hoozy于2023/12/28 10:51进行创建
 * 描述：最长中文字5个
 */
public class CharacterSelectorView extends View {
    private Paint paintText;
    private Paint paintBackGround;
    private int textColor;
    private int selectBackGroundColor;
    private int textSize;//默认14sp
    private int selectBackGroundHorizonWidth;//默认70dp
    private List<String> mLetters = new ArrayList<>();
    private String mCurrentLetter = "";

    public CharacterSelectorView(Context context) {
        this(context, null);
    }

    public CharacterSelectorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CharacterSelectorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CharacterSelectorView);
        textColor = array.getColor(R.styleable.CharacterSelectorView_textColor, Color.parseColor("#333333"));
        selectBackGroundColor = array.getColor(R.styleable.CharacterSelectorView_selectBackGroundColor, Color.parseColor("#FEA322"));
        textSize = array.getDimensionPixelOffset(R.styleable.CharacterSelectorView_textSizeCustom,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics()));
        selectBackGroundHorizonWidth = array.getDimensionPixelOffset(R.styleable.CharacterSelectorView_selectBackGroundHorizonWidth,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, getResources().getDisplayMetrics()));

        initData();

        paintText = new Paint();
        paintText.setColor(textColor);
        paintText.setTextSize(textSize);
        paintText.setAntiAlias(true);//抗锯齿效果 麻刻刻

        paintBackGround = new Paint();
        paintBackGround.setColor(selectBackGroundColor);
    }

    private void initData() {
        String[] arr = new String[]{"认同", "基本认同", "不认同", "不了解", "弃权"};
        mLetters.addAll(Arrays.asList(arr));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int textLayoutParams = (int) paintText.measureText("胡");
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = getPaddingTop() + getPaddingBottom() + textLayoutParams;
        setMeasuredDimension(width, height);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
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
                canvas.drawRoundRect(new RectF(letterCenterX - selectBackGroundHorizonWidth, letterCenterY - 50, letterCenterX + selectBackGroundHorizonWidth, letterCenterY + 50), 68, 68, paintBackGround);
                paintText.setColor(Color.parseColor("#FFFFFF"));
                canvas.drawText(mCurrentLetter, startX, baseLine, paintText);
            } else {
                paintText.setColor(textColor);
                canvas.drawText(mLetters.get(i), startX, baseLine, paintText);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
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
                break;
            case MotionEvent.ACTION_UP:
                //获取选中值
                listener.select(mCurrentLetter);
                break;
        }
        return true;
    }

    public interface OnSelectContentListener {
        void select(String s);
    }

    public OnSelectContentListener listener;

    public void setOnSelectPersonListener(OnSelectContentListener listener) {
        this.listener = listener;
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
        if (null != selectContent && !"".equals(selectContent) && !"null".equals(selectContent) && !"Null".equals(selectContent) && !"Nul".equals(selectContent)) {
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
                Toast.makeText(context, "数据错误,当前内容:'" + selectContent + "'不在选项内", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * 格式化状态
     */
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

}
