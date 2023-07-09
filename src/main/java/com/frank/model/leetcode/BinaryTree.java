package com.frank.model.leetcode;

import java.util.LinkedList;
import java.util.List;

public class BinaryTree {

    /**
     * 前序遍历
     *
     * @param root
     * @return
     */
    public int[] preorderTraversal(TreeNode root) {
        // write code here
        List<Integer> list = new LinkedList<>();
        preorder(list, root);
        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }
        return res;

    }

    /**
     * 前中后序遍历就是输出顺序不同
     * 前
     * list.add(root.val);
     * preorder(list,root.left);
     * preorder(list,root.right);
     * <p>
     * <p>
     * <p>
     * 中
     * preorder(list,root.left);
     * list.add(root.val);
     * preorder(list,root.right);
     * <p>
     * <p>
     * 后
     * preorder(list,root.left);
     * preorder(list,root.right);
     * list.add(root.val);
     *
     * @param list
     * @param root
     */
    public void preorder(List<Integer> list, TreeNode root) {
        if (root == null) {
            return;
        }
        list.add(root.val);
        preorder(list, root.left);
        preorder(list, root.right);
    }
}
