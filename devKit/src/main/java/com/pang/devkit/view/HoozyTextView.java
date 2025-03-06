package com.pang.devkit.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * 由hoozy于2023/12/22 08:49进行创建
 * 描述：别给我留着空白换行了
 */
public class HoozyTextView extends AppCompatTextView {
    public HoozyTextView(Context context) {
        this(context, null);
    }

    public HoozyTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HoozyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int lNum = getLayout().getEllipsisCount(getLineCount() - 1);//判定 >0 则有隐藏
                if (lNum > 0) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
                    ad.setItems(new String[]{"" + getText().toString().trim()}, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();
                }
            }
        });
    }

    public void setTextValue(String text) {
        this.setText(text);
        initAutoSplitTextView();
    }

    private void initAutoSplitTextView() {
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                final CharSequence newText = autoSplitText(HoozyTextView.this);
                if (!TextUtils.isEmpty(newText)) {
                    setText(newText);
                }
            }
        });
    }

    //返回CharSequence对象
    private CharSequence autoSplitText(final TextView tv) {
        final String rawText = tv.getText().toString(); //原始文本
        final Paint tvPaint = tv.getPaint(); //paint，包含字体等信息
        final float tvWidth = tv.getWidth() - tv.getPaddingLeft() - tv.getPaddingRight(); //控件可用宽度
        //将原始文本按行拆分
        String[] rawTextLines = rawText.replaceAll("\r", "").split("\n");
        StringBuilder newStringBuilder = new StringBuilder();
        for (String rawTextLine : rawTextLines) {
            if (tvPaint.measureText(rawTextLine) <= tvWidth) {
                //如果整行宽度在控件可用宽度之内，就不处理了
                newStringBuilder.append(rawTextLine);
            } else {
                //如果整行宽度超过控件可用宽度，则按字符测量，在超过可用宽度的前一个字符处手动换行
                float lineWidth = 0;
                int startIndex = 0;
                int endIndex = 0;
                for (int cnt = 0; cnt != rawTextLine.length(); ++cnt) {
                    char ch = rawTextLine.charAt(cnt);
                    //lineWidth += tvPaint.measureText(String.valueOf(ch));
                    endIndex = cnt + 1;
                    lineWidth = tvPaint.measureText(rawTextLine.substring(startIndex, endIndex));
                    if (lineWidth <= tvWidth) {
                        newStringBuilder.append(ch);
                    } else {
                        newStringBuilder.append("\n");
                        startIndex = cnt;
                        lineWidth = 0;
                        --cnt;
                    }
                }
            }
            newStringBuilder.append("\n");
        }
        //把结尾多余的\n去掉
        if (!rawText.endsWith("\n")) {
            newStringBuilder.deleteCharAt(newStringBuilder.length() - 1);
        }

        return newStringBuilder;
    }

}
