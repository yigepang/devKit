package com.pang.devkit.view.treeviewtwo;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pang.devkit.R;

/**
 * 由hoozy于2024/1/19 13:47进行创建
 * 描述：
 */
public class TreeListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public TreeListAdapter() {
        super(R.layout.sdk_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.mOppoSdkTextPosition, String.valueOf(helper.getAdapterPosition()));
        helper.setText(R.id.mOppoSdkText, item);
    }
}
