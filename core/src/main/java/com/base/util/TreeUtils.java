package com.base.util;

import com.base.core.entity.BaseTree;

import java.util.*;

/**
 * Tree工具类
 */
public abstract class TreeUtils<N> {
    /**
     * 获取节点唯一标识方法
     *
     * @param node
     * @return
     */
    protected abstract Object getKey(N node);

    /**
     * 获取节点父节点唯一标识方法
     *
     * @param node
     * @return
     */
    protected abstract Object getParentId(N node);

    /**
     * 子节点
     *
     * @param node
     * @return
     */
    protected abstract List<N> getChildren(N node);

    /**
     * @param nodes
     * @param node
     */
    protected abstract void setChildren(List<N> nodes, N node);


    /**
     * 构建树形结构数据
     * @param list
     * @return
     */
    public static List<BaseTree> buildTree(List<BaseTree> list) {
        TreeUtils<BaseTree> tree = new TreeUtils<>() {
            @Override
            protected Object getKey(BaseTree node) {
                return node.getId();
            }

            @Override
            protected Object getParentId(BaseTree node) {
                return node.getParentId();
            }

            @Override
            protected List<BaseTree> getChildren(BaseTree node) {
                return node.getChildren();
            }

            @Override
            protected void setChildren(List<BaseTree> nodes, BaseTree node) {
                if (ValidateHelper.isNotEmptyCollection(nodes)) {
                    nodes.sort(Comparator.comparing(BaseTree::getSeq, Comparator.nullsLast(Integer::compareTo)));
                    node.setChildren(nodes);
                }
            }
        };
        return tree.getTree(list);
    }


    /**
     * 生成树
     * @param oldList
     * @return
     */
    public List<N> getTree(List<N> oldList) {
        Map<Object, Object> newMap = new HashMap<>();
        Map<Object, Object> childMap = new HashMap<>();
        List<N> newList = new ArrayList<>();
        for (N tree : oldList) {
            newMap.put(getKey(tree), tree);
            childMap.put(getParentId(tree), tree);
        }
        for (N tree : oldList) {
            N parent = (N) newMap.get(getParentId(tree));
            if (parent != null) {
                if (getChildren(parent) == null) {
                    List<N> ch = new ArrayList<>();
                    ch.add(tree);
                    setChildren(ch, parent);
                } else {
                    List<N> ch = getChildren(parent);
                    ch.add(tree);
                    setChildren(ch, parent);
                }
            } else {
                newList.add(tree);
            }

            Object childs = (N) childMap.get(getKey(tree));
            if (Objects.isNull(childs)) {
                setChildren(new ArrayList<>(), tree);
            }
        }

        return newList;
    }

}