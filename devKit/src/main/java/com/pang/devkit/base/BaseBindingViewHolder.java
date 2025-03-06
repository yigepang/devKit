package com.pang.devkit.base;

import androidx.viewbinding.ViewBinding;

import com.chad.library.adapter.base.BaseViewHolder;

public class BaseBindingViewHolder<VB extends ViewBinding> extends BaseViewHolder {
    public VB binding;

    public BaseBindingViewHolder(VB binding) {

        super(binding.getRoot());

        this.binding = binding;
    }

}
