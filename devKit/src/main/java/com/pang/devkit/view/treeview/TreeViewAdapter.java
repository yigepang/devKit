package com.pang.devkit.view.treeview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pang.devkit.R;

import java.util.List;

/**
 * 由hoozy于2023/7/27 16:49进行创建
 * 描述：
 */
public class TreeViewAdapter extends BaseTreeViewAdapter {

    public TreeViewAdapter(RecyclerView recyclerView, Context context, List<NodeBean> datas, int defaultExpandLevel, int iconExpand, int iconNoExpand) {
        super(recyclerView, context, datas, defaultExpandLevel, iconExpand, iconNoExpand);
    }

    public TreeViewAdapter(RecyclerView mTree, Context context, List<NodeBean> datas, int defaultExpandLevel) {
        super(mTree, context, datas, defaultExpandLevel);
    }

    //选中的回调接口
    private OnTreeCheckedChangeListener checkedChangeListener;

    public interface OnTreeCheckedChangeListener {
        void onCheckChange(NodeBean nodeBean, int position, boolean isChecked);
    }

    /**
     * 接口监听
     */
    public void setTreeCheckedChangeListener(OnTreeCheckedChangeListener checkedChangeListener) {
        this.checkedChangeListener = checkedChangeListener;
    }

    /**
     * 接口调用
     */
    public void onTreeCheckedChangeListener(NodeBean nodeBean, int position, boolean isChecked) {
        if (null != checkedChangeListener) {
            checkedChangeListener.onCheckChange(nodeBean, position, isChecked);
        }
    }

    @Override
    public void onBindViewHolder(final NodeBean nodeBean, final RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.mTreeHeaderName.setText(nodeBean.getName());
        if (nodeBean.getIcon() == -1) {
            viewHolder.mTreeHeaderExpand.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.mTreeHeaderExpand.setVisibility(View.VISIBLE);
            viewHolder.mTreeHeaderExpand.setImageResource(nodeBean.getIcon());
        }

        viewHolder.mTreeHeaderCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChecked(nodeBean, viewHolder.mTreeHeaderCheckBox.isChecked());
                //接口调用
                onTreeCheckedChangeListener(nodeBean, position, viewHolder.mTreeHeaderCheckBox.isChecked());
            }
        });

        if (nodeBean.isChecked()) {
            viewHolder.mTreeHeaderCheckBox.setChecked(true);
        } else {
            viewHolder.mTreeHeaderCheckBox.setChecked(false);
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = View.inflate(mContext, R.layout.tree_header_item, null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        inflate.setLayoutParams(params);
        return new ViewHolder(inflate);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox mTreeHeaderCheckBox;
        private TextView mTreeHeaderName;
        private ImageView mTreeHeaderExpand;


        public ViewHolder(View itemView) {
            super(itemView);
            mTreeHeaderCheckBox = itemView.findViewById(R.id.mTreeHeaderCheckBox);
            mTreeHeaderName = itemView.findViewById(R.id.mTreeHeaderName);
            mTreeHeaderExpand = itemView.findViewById(R.id.mTreeHeaderExpand);
        }
    }
}
