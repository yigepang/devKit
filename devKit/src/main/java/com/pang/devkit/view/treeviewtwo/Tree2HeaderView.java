package com.pang.devkit.view.treeviewtwo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.pang.devkit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 由hoozy于2024/1/19 13:48进行创建
 * 描述：
 */
public class Tree2HeaderView extends LinearLayout implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {
    Tree2HeaderAdapter mAdapter = new Tree2HeaderAdapter();
    private List<NodeBean> mAllNodeBeans;
    private List<NodeBean> mNodeBeans;

    public Tree2HeaderView(Context context) {
        super(context);
        initView(context);
        initData();
    }

    public Tree2HeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initData();
    }

    public Tree2HeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initData();
    }

    private void initView(Context context) {
        //树结构布局
        View headerLayout = LayoutInflater.from(context).inflate(R.layout.tree_header_layout, this, true);
//        LinearLayoutManager manager = new LinearLayoutManager(context);
        //禁止滑动  布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(context) {
            //禁止竖向滑动 RecyclerView 为垂直状态（VERTICAL）
            @Override
            public boolean canScrollVertically() {
                return false;
            }
            //禁止横向滑动 RecyclerView 为水平状态（HORIZONTAL）
            /*@Override
            public boolean canScrollHorizontally() {
                return false;
            }*/
        };

        RecyclerView mTreeHeaderViewRecy = headerLayout.findViewById(R.id.mTreeHeaderViewRecy);
        mTreeHeaderViewRecy.setLayoutManager(manager);
        mTreeHeaderViewRecy.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
    }

    private void initData() {
        List<NodeBean> maxDataList = new ArrayList<>();
        //根节点0 1 2
        maxDataList.add(new NodeBean<>("0", "-1", "A 1级节点"));
        maxDataList.add(new NodeBean<>("0", "-1", "A 1级节点"));
        maxDataList.add(new NodeBean<>("0", "-1", "A 1级节点"));
        maxDataList.add(new NodeBean<>("1", "-1", "B 1级节点"));
        maxDataList.add(new NodeBean<>("2", "-1", "C 1级节点"));

//        //根节点1的二级节点
        maxDataList.add(new NodeBean<>("3", "0", "A 2级节点"));
        maxDataList.add(new NodeBean<>("4", "0", "A 2级节点"));
        maxDataList.add(new NodeBean<>("5", "0", "A 2级节点"));

        //根节点2的二级节点
        maxDataList.add(new NodeBean<>("6", "1", "B 2级节点"));
        maxDataList.add(new NodeBean<>("7", "1", "B 2级节点"));
        maxDataList.add(new NodeBean<>("8", "1", "B 2级节点"));

        //根节点3的二级节点
        maxDataList.add(new NodeBean<>("9", "2", "C 2级节点"));
        maxDataList.add(new NodeBean<>("10", "2", "C 2级节点"));
        maxDataList.add(new NodeBean<>("11", "2", "C 2级节点"));

        //三级节点
        maxDataList.add(new NodeBean<>("12", "3", "A 3级节点"));
        maxDataList.add(new NodeBean<>("13", "3", "A 3级节点"));
        maxDataList.add(new NodeBean<>("14", "3", "A 3级节点"));

        maxDataList.add(new NodeBean<>("15", "4", "A 3级节点"));
        maxDataList.add(new NodeBean<>("16", "4", "A 3级节点"));
        maxDataList.add(new NodeBean<>("17", "4", "A 3级节点"));

        maxDataList.add(new NodeBean<>("18", "5", "A 3级节点"));
        maxDataList.add(new NodeBean<>("19", "5", "A 3级节点"));
        maxDataList.add(new NodeBean<>("20", "5", "A 3级节点"));

        //四级节点
        maxDataList.add(new NodeBean<>("21", "12", "A 4级节点"));

        //五级节点
        maxDataList.add(new NodeBean<>("22", "21", "A 5级节点"));
        //六级节点
        maxDataList.add(new NodeBean<>("23", "22", "A 6级节点"));
        //七级节点
        maxDataList.add(new NodeBean<>("24", "23", "A 7级节点"));
        //八级节点
        maxDataList.add(new NodeBean<>("25", "24", "A 8级节点"));


        maxDataList.add(new NodeBean<>("26", "25", "A 9级节点"));
        maxDataList.add(new NodeBean<>("27", "26", "A 10级节点"));

        maxDataList.add(new NodeBean<>("28", "27", "A 11级节点"));

        maxDataList.add(new NodeBean<>("29", "28", "A 12级节点"));

        maxDataList.add(new NodeBean<>("30", "29", "A 13级节点"));

        maxDataList.add(new NodeBean<>("31", "30", "A 14级节点"));

        maxDataList.add(new NodeBean<>("32", "31", "A 15级节点"));

        maxDataList.add(new NodeBean<>("33", "32", "A 16级节点"));
        maxDataList.add(new NodeBean<>("33", "32", "A 16级节点"));

        maxDataList.add(new NodeBean<>("34", "33", "A 17级节点"));
        maxDataList.add(new NodeBean<>("34", "33", "A 17级节点"));


        maxDataList.add(new NodeBean<>("35", "-1", "D 1级节点"));
        maxDataList.add(new NodeBean<>("36", "-1", "E 1级节点"));
        maxDataList.add(new NodeBean<>("37", "-1", "F 1级节点"));

        maxDataList.add(new NodeBean<>("38", "35", "D 2级节点"));
        maxDataList.add(new NodeBean<>("39", "36", "E 2级节点"));
//        maxDataList.add(new NodeBean<>("40", "37", "F 2级节点"));


        /**
         * 对所有的Node进行排序
         */
        mAllNodeBeans = TreeHelper.getInstance().getSortedNodes(maxDataList, 0);

        /**
         * 过滤出可见的Node
         */
        mNodeBeans = TreeHelper.getInstance().filterVisibleNode(mAllNodeBeans);
        mAdapter.setNewData(mNodeBeans);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        expandOrCollapse(adapter, position);
    }

    /**
     * 相应ListView的点击事件 展开或关闭某节点
     *
     * @param position
     */
    public void expandOrCollapse(BaseQuickAdapter adapter, int position) {
        NodeBean n = (NodeBean) adapter.getData().get(position);
        // 排除传入参数错误异常
        if (n != null) {
            if (!n.isLeaf()) {
                n.setExpand(!n.isExpand());
                mNodeBeans = TreeHelper.getInstance().filterVisibleNode(mAllNodeBeans);
                //替换视图
                adapter.replaceData(mNodeBeans);
            }
        }

        Log.e("adapter", n.getName() + "****" + position);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        Log.e("adapter", mAdapter.getItem(position).getName() + "****" + position);
        for (int i = 0; i < mAllNodeBeans.size(); i++) {//清除原数据的选中效果
            NodeBean nodeBean = mAllNodeBeans.get(i);
            nodeBean.setChecked(false);
        }

        List<NodeBean> data = mAdapter.getData();//改变选中效果
        for (int i = 0; i < data.size(); i++) {
            NodeBean nodeBean = data.get(i);
            nodeBean.setChecked(false);
        }

        NodeBean item = mAdapter.getItem(position);
        item.setChecked(!item.isChecked());
        mAdapter.notifyDataSetChanged();
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
}
