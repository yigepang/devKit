package com.pang.devkit.view.spinner;

import android.content.Context;

import java.util.List;

/**
 * 由hoozy于2024/4/19 09:06进行创建
 * 描述：
 */
public class NiceSpinnerShowSelectAdapter<T> extends NiceSpinnerBaseAdapter {
    private final List<T> items;

    NiceSpinnerShowSelectAdapter(
            Context context,
            List<T> items,
            int textColor,
            int backgroundSelector,
            SpinnerTextFormatter spinnerTextFormatter,
            PopUpTextAlignment horizontalAlignment,
            int textSize
    ) {
        super(context, textColor, backgroundSelector, spinnerTextFormatter, horizontalAlignment, textSize);
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public T getItem(int position) {
        return items.get(position);
    }

    @Override
    public T getItemInDataset(int position) {
        return items.get(position);
    }
}
