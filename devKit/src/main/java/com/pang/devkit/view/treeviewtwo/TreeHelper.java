package com.pang.devkit.view.treeviewtwo;

import java.util.ArrayList;
import java.util.List;

/**
 * 由hoozy于2024/1/19 13:49进行创建
 * 描述：
 */
public class TreeHelper {
    private volatile static TreeHelper treeHelper = null;

    public static TreeHelper getInstance() {
        if (null == treeHelper) {
            synchronized (TreeHelper.class) {
                if (null == treeHelper) {
                    treeHelper = new TreeHelper();
                }
            }
        }
        return treeHelper;
    }

    /**
     * 传入node 返回排序后的Node
     * 拿到用户传入的数据，转化为List<Node>以及设置Node间关系，然后根节点，从根往下遍历进行排序；
     *
     * @param datas
     * @param defaultExpandLevel 默认显示
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public List<NodeBean> getSortedNodes(List<NodeBean> datas, int defaultExpandLevel) {
        List<NodeBean> result = new ArrayList<NodeBean>();
        // 设置Node间父子关系
        List<NodeBean> nodeBeans = convetData2Node(datas);
        // 拿到根节点
        List<NodeBean> rootNodeBeans = getRootNodes(nodeBeans);
        // 排序以及设置Node间关系
        for (NodeBean nodeBean : rootNodeBeans) {
            addNode(result, nodeBean, defaultExpandLevel, 1);
        }
        return result;
    }

    /**
     * 过滤出所有可见的Node
     * 过滤Node的代码很简单，遍历所有的Node，只要是根节点或者父节点是展开状态就添加返回
     *
     * @param nodeBeans
     * @return
     */
    public List<NodeBean> filterVisibleNode(List<NodeBean> nodeBeans) {
        List<NodeBean> result = new ArrayList<NodeBean>();

        for (NodeBean nodeBean : nodeBeans) {
            // 如果为跟节点，或者上层目录为展开状态
            if (nodeBean.isRootNode() || nodeBean.isParentExpand()) {
                setNodeIcon(nodeBean);
                result.add(nodeBean);
            }
        }
        return result;
    }

    /**
     * 将我们的数据转化为树的节点
     * 设置Node间，父子关系;让每两个节点都比较一次，即可设置其中的关系
     */
    public List<NodeBean> convetData2Node(List<NodeBean> nodeBeans) {

        for (int i = 0; i < nodeBeans.size(); i++) {
            NodeBean n = nodeBeans.get(i);
            for (int j = i + 1; j < nodeBeans.size(); j++) {
                NodeBean m = nodeBeans.get(j);
                if (m.getPid() != null) {
                    //n时m的父节点
                    if (m.getPid().equals(n.getId())) {
                        n.getChildren().add(m);
                        m.setParent(n);
                        //m时n的父节点
                    } else if (m.getId().equals(n.getPid())) {
                        m.getChildren().add(n);
                        n.setParent(m);
                    }
                } else {
                    if (m.getPid() == n.getId()) {
                        n.getChildren().add(m);
                        m.setParent(n);
                    } else if (m.getId() == n.getPid()) {
                        m.getChildren().add(n);
                        n.setParent(m);
                    }
                }
            }
        }
        return nodeBeans;
    }

    /**
     * 获得根节点
     *
     * @param nodeBeans
     * @return
     */
    public List<NodeBean> getRootNodes(List<NodeBean> nodeBeans) {
        List<NodeBean> root = new ArrayList<NodeBean>();
        for (NodeBean nodeBean : nodeBeans) {
            if (nodeBean.isRootNode()) {
                root.add(nodeBean);
            }
        }
        return root;
    }

    /**
     * 把一个节点上的所有的内容都挂上去
     * 通过递归的方式，把一个节点上的所有的子节点等都按顺序放入
     */
    public <T> void addNode(List<NodeBean> nodeBeans, NodeBean<T> nodeBean, int defaultExpandLeval, int currentLevel) {
        nodeBeans.add(nodeBean);
        if (defaultExpandLeval >= currentLevel) {
            nodeBean.setExpand(true);
        }

        if (nodeBean.isLeaf()) {
            return;
        }
        for (int i = 0; i < nodeBean.getChildren().size(); i++) {
            addNode(nodeBeans, nodeBean.getChildren().get(i), defaultExpandLeval, currentLevel + 1);
        }
    }

    /**
     * 设置节点的图标
     *
     * @param nodeBean
     */
    private void setNodeIcon(NodeBean nodeBean) {
        if (nodeBean.getChildren().size() > 0 && nodeBean.isExpand()) {
            nodeBean.setIcon(nodeBean.iconExpand);
        } else if (nodeBean.getChildren().size() > 0 && !nodeBean.isExpand()) {
            nodeBean.setIcon(nodeBean.iconNoExpand);
        } else {
            nodeBean.setIcon(-1);
        }
    }
}
