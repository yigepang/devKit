package com.pang.devkit.view.treeview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pang.devkit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 由hoozy于2023/7/28 11:18进行创建
 * 描述：
 */
public class TreeView extends LinearLayout {

    private TreeViewAdapter adapter;
    private List<NodeBean> dataList = new ArrayList<>();

    public TreeView(Context context) {
        super(context);
        initView(context);
        initData();
    }

    public TreeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initData();
    }

    public TreeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

        //第一个参数  ListView & RecyclerView
        //第二个参数  上下文
        //第三个参数  数据集
        //第四个参数  默认展开层级数 0为不展开
        //第五个参数  展开的图标
        //第六个参数  闭合的图标
        adapter = new TreeViewAdapter(mTreeHeaderViewRecy, context, dataList, 0, R.drawable.svg_expand_more, R.drawable.svg_navigate_next);

        mTreeHeaderViewRecy.setAdapter(adapter);
    }

    private void initData() {
        //根节点0 1 2
        dataList.add(new NodeBean<>("0", "-1", "A 1级节点"));
        dataList.add(new NodeBean<>("1", "-1", "B 1级节点"));
        dataList.add(new NodeBean<>("2", "-1", "C 1级节点"));

//        //根节点1的二级节点
        dataList.add(new NodeBean<>("3", "0", "A 2级节点"));
        dataList.add(new NodeBean<>("4", "0", "A 2级节点"));
        dataList.add(new NodeBean<>("5", "0", "A 2级节点"));

        //根节点2的二级节点
        dataList.add(new NodeBean<>("6", "1", "B 2级节点"));
        dataList.add(new NodeBean<>("7", "1", "B 2级节点"));
        dataList.add(new NodeBean<>("8", "1", "B 2级节点"));

        //根节点3的二级节点
        dataList.add(new NodeBean<>("9", "2", "C 2级节点"));
        dataList.add(new NodeBean<>("10", "2", "C 2级节点"));
        dataList.add(new NodeBean<>("11", "2", "C 2级节点"));

        //三级节点
        dataList.add(new NodeBean<>("12", "3", "A 3级节点"));
        dataList.add(new NodeBean<>("13", "3", "A 3级节点"));
        dataList.add(new NodeBean<>("14", "3", "A 3级节点"));

        dataList.add(new NodeBean<>("15", "4", "A 3级节点"));
        dataList.add(new NodeBean<>("16", "4", "A 3级节点"));
        dataList.add(new NodeBean<>("17", "4", "A 3级节点"));

        dataList.add(new NodeBean<>("18", "5", "A 3级节点"));
        dataList.add(new NodeBean<>("19", "5", "A 3级节点"));
        dataList.add(new NodeBean<>("20", "5", "A 3级节点"));

        //四级节点
        dataList.add(new NodeBean<>("21", "12", "A 4级节点"));

        //五级节点
        dataList.add(new NodeBean<>("22", "21", "A 5级节点"));
        //六级节点
        dataList.add(new NodeBean<>("23", "22", "A 6级节点"));
        //七级节点
        dataList.add(new NodeBean<>("24", "23", "A 7级节点"));
        //八级节点
        dataList.add(new NodeBean<>("25", "24", "A 8级节点"));


        adapter.addData(dataList);

//        //获取所有节点
//        final List<Node> allNodes = mAdapter.getAllNodes();
//        for (Node allNode : allNodes) {
//            Log.e("TAG1231", "onCreate: " + allNode.getName());
//        }

        //选中状态监听
        adapter.setTreeCheckedChangeListener(new TreeViewAdapter.OnTreeCheckedChangeListener() {
            @Override
            public void onCheckChange(NodeBean nodeBean, int position, boolean isChecked) {
                Log.e("TAG1231", "onCheckChange position: " + nodeBean.getName());

                //获取所有选中节点
                List<NodeBean> selectedNodeBean = adapter.getSelectedNode();

                StringBuilder builder = new StringBuilder();
                for (NodeBean n : selectedNodeBean) {
                    builder.append(n.getName()).append("\n");
                }
                Log.e("TAG1231", "builder: " + builder);
            }
        });

        //总item点击状态监听
        adapter.setOnTreeClickListener(new BaseTreeViewAdapter.OnTreeClickListener() {
            @Override
            public void onClick(NodeBean nodeBean, int position) {
                Log.e("TAG1231", "setOnTreeClickListener: " + nodeBean.getName());
            }
        });
    }
}
