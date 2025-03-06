package com.pang.devkit.base;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

public abstract class BaseBindingAdapter<T, VB extends ViewBinding> extends BaseQuickAdapter<T, BaseBindingViewHolder<VB>> {

    public BaseBindingAdapter(@Nullable List<T> data) {
        super(data);
    }

    public BaseBindingAdapter() {
        super(0);
    }

    @Override
    protected BaseBindingViewHolder<VB> onCreateDefViewHolder(ViewGroup parent, int viewType) {
        VB binding = ViewBindingUtil.viewBindingJavaClass(LayoutInflater.from(parent.getContext()), parent, null, this.getClass());
        return new BaseBindingViewHolder(binding);
    }

    @Override
    protected void convert(BaseBindingViewHolder<VB> helper, T item) {
        convert(helper.binding, item, helper.getLayoutPosition());
    }

    public abstract void convert(VB binding, T item, int position);
}
