package com.pang.devkit.view.treeview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 由hoozy于2023/7/27 15:32进行创建
 * 描述：
 */
public abstract class BaseTreeViewAdapter extends RecyclerView.Adapter {
    protected Context mContext;

    /**
     * 默认不展开
     */
    private int defaultExpandLevel = 0;

    /**
     * 展开与关闭的图片
     */
    private int iconExpand = -1, iconNoExpand = -1;

    /**
     * 存储所有的Node
     */
    protected List<NodeBean> mAllNodeBeans = new ArrayList<>();

    /**
     * 存储所有可见的Node
     */
    protected List<NodeBean> mNodeBeans = new ArrayList<>();

    protected LayoutInflater mInflater;

    /**
     * 点击的回调接口
     */
    private OnTreeClickListener treeClickListener;

    public interface OnTreeClickListener {
        void onClick(NodeBean nodeBean, int position);
    }

    /**
     * 接口监听
     *
     * @param treeClickListener
     */
    public void setOnTreeClickListener(OnTreeClickListener treeClickListener) {
        this.treeClickListener = treeClickListener;
    }

    /**
     * 接口调用
     *
     * @param nodeBean
     * @param position
     */
    public void onTreeClickListener(NodeBean nodeBean, int position) {
        if (null != treeClickListener) {
            treeClickListener.onClick(nodeBean, position);
        }
    }

    public BaseTreeViewAdapter(RecyclerView recyclerView, Context context, List<NodeBean> datas,
                               int defaultExpandLevel, int iconExpand, int iconNoExpand) {
        mContext = context;
        this.defaultExpandLevel = defaultExpandLevel;
        this.iconExpand = iconExpand;
        this.iconNoExpand = iconNoExpand;

        for (NodeBean nodeBean : datas) {
            nodeBean.getChildren().clear();
            nodeBean.iconExpand = iconExpand;
            nodeBean.iconNoExpand = iconNoExpand;
        }

        /**
         * 对所有的Node进行排序
         */
        mAllNodeBeans = TreeHelper.getInstance().getSortedNodes(datas, defaultExpandLevel);

        /**
         * 过滤出可见的Node
         */
        mNodeBeans = TreeHelper.getInstance().filterVisibleNode(mAllNodeBeans);

        mInflater = LayoutInflater.from(context);
    }

    /**
     * @param mTree
     * @param context
     * @param datas
     * @param defaultExpandLevel 默认展开几级树
     */
    public BaseTreeViewAdapter(RecyclerView mTree, Context context, List<NodeBean> datas, int defaultExpandLevel) {
        this(mTree, context, datas, defaultExpandLevel, -1, -1);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        NodeBean nodeBean = mNodeBeans.get(position);
        //        convertView = getConvertView(node, position, convertView, parent);
        // 设置内边距
        holder.itemView.setPadding(nodeBean.getLevel() * 50, 10, 10, 10);
        /**
         * 设置节点点击时，可以展开以及关闭,将事件继续往外公布
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandOrCollapse(position);
                //接口调用
                onTreeClickListener(mNodeBeans.get(position), position);
            }
        });
        onBindViewHolder(nodeBean, holder, position);
    }

    @Override
    public int getItemCount() {
        return mNodeBeans.size();
    }


    /**
     * 获取排序后所有节点
     *
     * @return
     */
    public List<NodeBean> getAllNodes() {
        if (mAllNodeBeans == null) {
            mAllNodeBeans = new ArrayList<NodeBean>();
        }
        return mAllNodeBeans;
    }

    /**
     * 获取所有选中节点
     *
     * @return
     */
    public List<NodeBean> getSelectedNode() {
        List<NodeBean> checks = new ArrayList<NodeBean>();
        for (int i = 0; i < mAllNodeBeans.size(); i++) {
            NodeBean n = mAllNodeBeans.get(i);
            if (n.isChecked()) {
                checks.add(n);
            }
        }
        return checks;
    }


    /**
     * 相应ListView的点击事件 展开或关闭某节点
     *
     * @param position
     */
    public void expandOrCollapse(int position) {
        NodeBean n = mNodeBeans.get(position);
        // 排除传入参数错误异常
        if (n != null) {
            if (!n.isLeaf()) {
                n.setExpand(!n.isExpand());
                mNodeBeans = TreeHelper.getInstance().filterVisibleNode(mAllNodeBeans);
                // 刷新视图
                notifyDataSetChanged();
            }
        }
    }

    /**
     * 设置多选
     *
     * @param nodeBean
     * @param checked
     */
    protected void setChecked(final NodeBean nodeBean, boolean checked) {
        nodeBean.setChecked(checked);
        setChildChecked(nodeBean, checked);
        if (nodeBean.getParent() != null) {
            setNodeParentChecked(nodeBean.getParent(), checked);
        }
        notifyDataSetChanged();
    }

    /**
     * 设置是否选中
     *
     * @param nodeBean
     * @param checked
     */
    public <T> void setChildChecked(NodeBean<T> nodeBean, boolean checked) {
        if (!nodeBean.isLeaf()) {
            nodeBean.setChecked(checked);
            for (NodeBean childrenNodeBean : nodeBean.getChildren()) {
                setChildChecked(childrenNodeBean, checked);
            }
        } else {
            nodeBean.setChecked(checked);
        }
    }

    private void setNodeParentChecked(NodeBean nodeBean, boolean checked) {
        if (checked) {
            nodeBean.setChecked(checked);
            if (nodeBean.getParent() != null) {
                setNodeParentChecked(nodeBean.getParent(), checked);
            }
        } else {
            List<NodeBean> childrens = nodeBean.getChildren();
            boolean isChecked = false;
            for (NodeBean children : childrens) {
                if (children.isChecked()) {
                    isChecked = true;
                }
            }
            //如果所有自节点都没有被选中 父节点也不选中
            if (!isChecked) {
                nodeBean.setChecked(checked);
            }
            if (nodeBean.getParent() != null) {
                setNodeParentChecked(nodeBean.getParent(), checked);
            }
        }
    }

    /**
     * 清除掉之前数据并刷新  重新添加
     *
     * @param mlists
     * @param defaultExpandLevel 默认展开几级列表
     */
    public void addDataAll(List<NodeBean> mlists, int defaultExpandLevel) {
        mAllNodeBeans.clear();
        addData(-1, mlists, defaultExpandLevel);
    }

    /**
     * 在指定位置添加数据并刷新 可指定刷新后显示层级
     *
     * @param index
     * @param mlists
     * @param defaultExpandLevel 默认展开几级列表
     */
    public void addData(int index, List<NodeBean> mlists, int defaultExpandLevel) {
        this.defaultExpandLevel = defaultExpandLevel;
        notifyData(index, mlists);
    }

    /**
     * 在指定位置添加数据并刷新
     *
     * @param index
     * @param mlists
     */
    public void addData(int index, List<NodeBean> mlists) {
        notifyData(index, mlists);
    }

    /**
     * 添加数据并刷新
     *
     * @param mlists
     */
    public void addData(List<NodeBean> mlists) {
        addData(mlists, defaultExpandLevel);
    }

    /**
     * 添加数据并刷新 可指定刷新后显示层级
     *
     * @param mlists
     * @param defaultExpandLevel
     */
    public void addData(List<NodeBean> mlists, int defaultExpandLevel) {
        this.defaultExpandLevel = defaultExpandLevel;
        notifyData(-1, mlists);
    }

    /**
     * 添加数据并刷新
     *
     * @param nodeBean
     */
    public void addData(NodeBean nodeBean) {
        addData(nodeBean, defaultExpandLevel);
    }

    /**
     * 添加数据并刷新 可指定刷新后显示层级
     *
     * @param nodeBean
     * @param defaultExpandLevel
     */
    public void addData(NodeBean nodeBean, int defaultExpandLevel) {
        List<NodeBean> nodeBeans = new ArrayList<>();
        nodeBeans.add(nodeBean);
        this.defaultExpandLevel = defaultExpandLevel;
        notifyData(-1, nodeBeans);
    }

    /**
     * 刷新数据
     *
     * @param index
     * @param mListNodeBeans
     */
    private void notifyData(int index, List<NodeBean> mListNodeBeans) {
        for (int i = 0; i < mListNodeBeans.size(); i++) {
            NodeBean nodeBean = mListNodeBeans.get(i);
            nodeBean.getChildren().clear();
            nodeBean.iconExpand = iconExpand;
            nodeBean.iconNoExpand = iconNoExpand;
        }
        for (int i = 0; i < mAllNodeBeans.size(); i++) {
            NodeBean nodeBean = mAllNodeBeans.get(i);
            nodeBean.getChildren().clear();
            // node.isNewAdd = false;
        }
        if (index != -1) {
            mAllNodeBeans.addAll(index, mListNodeBeans);
        } else {
            mAllNodeBeans.addAll(mListNodeBeans);
        }
        /**
         * 对所有的Node进行排序
         */
        mAllNodeBeans = TreeHelper.getInstance().getSortedNodes(mAllNodeBeans, defaultExpandLevel);
        /**
         * 过滤出可见的Node
         */
        mNodeBeans = TreeHelper.getInstance().filterVisibleNode(mAllNodeBeans);
        //刷新数据
        notifyDataSetChanged();
    }

    public abstract void onBindViewHolder(NodeBean nodeBean, RecyclerView.ViewHolder holder, final int position);
}
