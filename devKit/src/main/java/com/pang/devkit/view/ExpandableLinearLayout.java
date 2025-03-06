package com.pang.devkit.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * 由hoozy于2023/5/30 16:58进行创建
 * 描述：
 */
public class ExpandableLinearLayout extends LinearLayout {
    //是否展开，默认展开
    private boolean isOpen = true;

    //第一个子view的高度，即收起保留高度
    private int firstChildHeight = 0;

    //所有子view高度，即总高度
    private int allChildHeight = 0;


    public ExpandableLinearLayout(Context context) {
        this(context, null);
    }

    public ExpandableLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandableLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
//        //横向的话 稍加修改计算宽度即可
//        orientation = VERTICAL;
//
//        animPercent = 1f;
//        isOpen = true;

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //重置高度
        allChildHeight = 0;
        firstChildHeight = 0;

        int childCount = getChildCount();
        if (childCount > 0) {

            //遍历计算高度
            for (int i = 0; i < childCount; i++) {
                if (i == 0) {
                    //这个地方实际使用中除了measuredHeight，以及margin等，也要计算在内
                    firstChildHeight = getChildAt(0).getMeasuredHeight()
                            + getChildAt(0).getTop() + getChildAt(0).getBottom()
                            + this.getPaddingTop() + this.getPaddingBottom();
                }
                //实际使用时或包括padding等
                allChildHeight += getChildAt(i).getMeasuredHeight() + getChildAt(i).getTop() + getChildAt(i).getBottom();

                //最后一条的时候 加上当前view自身的padding
                if (i == childCount - 1) {
                    allChildHeight += this.getPaddingTop() + this.getPaddingBottom();
                }
            }
//
//            // 根据是否展开设置高度
//            if (isOpen) {
//                setMeasuredDimension(
//                        widthMeasureSpec,
//                        firstChildHeight + ((allChildHeight - firstChildHeight) * animPercent).toInt()
//                );
//            } else {
//                setMeasuredDimension(
//                        widthMeasureSpec,
//                        allChildHeight - ((allChildHeight - firstChildHeight) * animPercent).toInt()
//                );
//            }
        }

    }
}
