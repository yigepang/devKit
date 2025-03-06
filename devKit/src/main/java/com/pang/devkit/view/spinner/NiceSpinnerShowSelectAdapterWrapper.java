package com.pang.devkit.view.spinner;

import android.content.Context;
import android.widget.ListAdapter;

/**
 * 由hoozy于2024/4/19 09:12进行创建
 * 描述：
 */
public class NiceSpinnerShowSelectAdapterWrapper extends NiceSpinnerBaseAdapter {
    private final ListAdapter baseAdapter;

    NiceSpinnerShowSelectAdapterWrapper(
            Context context,
            ListAdapter toWrap,
            int textColor,
            int backgroundSelector,
            SpinnerTextFormatter spinnerTextFormatter,
            PopUpTextAlignment horizontalAlignment,
            int textSize
    ) {
        super(context, textColor, backgroundSelector, spinnerTextFormatter, horizontalAlignment, textSize);
        baseAdapter = toWrap;
    }

    @Override
    public int getCount() {
        return baseAdapter.getCount() - 1;
    }

    @Override
    public Object getItem(int position) {
        return baseAdapter.getItem(position >= selectedIndex ? position + 1 : position);
    }

    @Override
    public Object getItemInDataset(int position) {
        return baseAdapter.getItem(position);
    }
}
