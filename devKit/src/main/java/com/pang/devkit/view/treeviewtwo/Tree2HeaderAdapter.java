package com.pang.devkit.view.treeviewtwo;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pang.devkit.R;

/**
 * 由hoozy于2024/1/19 13:49进行创建
 * 描述：
 */
public class Tree2HeaderAdapter extends BaseQuickAdapter<NodeBean, BaseViewHolder> {
    public Tree2HeaderAdapter() {
        super(R.layout.layout_item_select_equipment_children);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final NodeBean item) {
        //最大展开8级宽度距离
        int level = Math.min(item.getLevel(), 12);
        // 设置内边距
        helper.itemView.setPadding(level * 50, 10, 10, 10);
        //设置文字内容
        helper.setText(R.id.mTreeName, item.getName());
        //设置展开与收起的图片
        helper.setImageResource(R.id.mTreeExpand, item.isExpand() ? R.drawable.svg_expand_more : R.drawable.svg_navigate_next);
        //图片显示隐藏
        helper.setVisible(R.id.mTreeExpand, item.getChildren().size() > 0);

        if (item.isChecked()) {
            helper.setImageResource(R.id.mTreeCheck, R.drawable.icon_select_check);
        } else {
            helper.setImageResource(R.id.mTreeCheck, R.drawable.icon_default_check);
        }

        helper.addOnClickListener(R.id.mTreeCheck);
    }

//    //选中的回调接口
//    private TreeViewAdapter.OnTreeCheckedChangeListener checkedChangeListener;
//
//    public interface OnTreeCheckedChangeListener {
//        void onCheckChange(NodeBean nodeBean, int position, boolean isChecked);
//    }
//
//    /**
//     * 接口监听
//     */
//    public void setTreeCheckedChangeListener(TreeViewAdapter.OnTreeCheckedChangeListener checkedChangeListener) {
//        this.checkedChangeListener = checkedChangeListener;
//    }
}
