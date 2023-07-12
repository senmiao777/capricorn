package com.frank.model.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.*;

public class BinaryTree {


    /**
     * BM28 二叉树的最大深度
     *
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {
        // write code here
        if (root == null) {
            return 0;
        }
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;

    }

    /**
     * 之字形遍历，即第一行从左到右，第二行从右到左
     *
     * @param pRoot
     * @return
     */
    public ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();

        if (pRoot == null) {
            return res;
        }
        // write code here
        Queue<TreeNode> q = new LinkedList<TreeNode>();
        q.offer(pRoot);
        boolean flag = true;
        while (!q.isEmpty()) {
            /**
             * 每次放这一层的元素个数
             */
            int size = q.size();
            ArrayList<Integer> row = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                row.add(node.val);
                if (node.left != null) {
                    q.offer(node.left);
                }
                if (node.right != null) {
                    q.offer(node.right);
                }
            }
            flag = !flag;
            if (flag) {
                Collections.reverse(row);
            }
            res.add(row);
        }
        return res;


    }

    /**
     * 给定一个二叉树，返回该二叉树层序遍历的结果，（从左到右，一层一层地遍历） 例如： 给定的二叉树是{3,9,20,#,#,15,7}, 该二叉树层序遍历的结果是 [ [3], [9,20], [15,7] ] 提示: 0 1500
     * 示例1
     * 输入
     * {1,2}
     * 输出
     * [[1],[2]]
     * 示例2
     * 输入
     * {1,2,3,4,#,#,5}
     * 输出
     * [[1],[2,3],[4,5]]
     * <p>
     * BM26 求二叉树的层序遍历
     *
     * @param root
     * @return
     */
    public ArrayList<ArrayList<Integer>> levelOrder(TreeNode root) {
        // write code here
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        Collections.reverse(res);
        if (root == null) {
            return res;
        }
        Queue<TreeNode> queue = new ArrayDeque<>();
        //LinkedList
        // 先把根节点放进去
        queue.add(root);
        while (!queue.isEmpty()) {
            // 当前行的list
            ArrayList<Integer> row = new ArrayList<>();
            // 该层元素的数量
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                // 依次遍历队列里的元素，将其左右孩子（如有）放入队列尾部
                TreeNode cur = queue.poll();
                row.add(cur.val);
                if (cur.left != null) {
                    queue.add(cur.left);
                }
                if (cur.right != null) {
                    queue.add(cur.right);
                }
            }
            // 遍历完该层的所有元素之后，添加到结果list中
            res.add(row);
        }
        return res;
    }

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
