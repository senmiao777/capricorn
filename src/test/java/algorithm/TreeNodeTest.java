package algorithm;


import com.frank.model.leetcode.TreeNode;
import com.google.common.collect.Lists;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import sun.reflect.generics.tree.Tree;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class TreeNodeTest {


    @Test
    public void testShowAllNodes() {
        // 构造二叉查找树
        LinkedList<Integer> numbers = Lists.newLinkedList();
        numbers.add(3);
        numbers.add(2);
        numbers.add(9);
        numbers.add(null);
        numbers.add(null);
        numbers.add(10);
        numbers.add(null);
        numbers.add(null);
        numbers.add(8);
        numbers.add(null);
        numbers.add(4);


        final List<Integer> collect = numbers.stream().filter(n -> {
            System.out.println("filter number is " + n);
            return n != null && n > 3;
        }).map(n -> {
            System.out.println("do nothing number is " + n);
            return n;
        }).limit(2).collect(Collectors.toList());
        System.out.println(collect);

        TreeNode treeNode = createTreeNode(numbers);
        preOrder(treeNode);
        System.out.println("----------------------");
        levelPost(treeNode);

        int i = maxDeep(treeNode);
        System.out.println("treeNode maxDeep="+i);
    }


    /**
     * 获取二叉树的深度
     * @param treeNode
     * @return
     */
    private int maxDeep(TreeNode treeNode) {
        if (treeNode == null) {
            return 0;
        }
        int left = maxDeep(treeNode.left);
        int right = maxDeep(treeNode.right);
        return Math.max(left, right) + 1;
    }

    /**
     * 根据链表创建二叉查找树
     *
     * @param numbers
     * @return
     */
    public static final TreeNode createTreeNode(LinkedList<Integer> numbers) {
        TreeNode node = null;
        if (Objects.nonNull(numbers) && !numbers.isEmpty()) {
            // 移除第一个元素
            Integer value = numbers.removeFirst();
            if (value != null) {
                node = new TreeNode(value);
                node.setLeft(createTreeNode(numbers));
                node.setRight(createTreeNode(numbers));
            }
        }
        return node;
    }

    public void preOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        System.out.println(node.getVal());
        preOrder(node.getLeft());
        preOrder(node.getRight());
    }

    /**
     * 中序遍历
     *
     * @param node
     */
    public void inOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        inOrder(node.getLeft());
        System.out.println(node.getVal());
        inOrder(node.getRight());
    }

    public void postOrder(TreeNode node) {
        if (node == null) {
            return;
        }
        postOrder(node.getLeft());
        postOrder(node.getRight());
        System.out.println(node.getVal());
    }


    /**
     * 广度优先遍历
     * 出队，打印值，将孩子入队（如不为空的话）
     *
     * @param root
     */
    public void levelPost(TreeNode root) {
        Queue<TreeNode> treeNodes = new LinkedList<>();
        treeNodes.offer(root);
        while (!treeNodes.isEmpty()) {
            TreeNode node = treeNodes.poll();
            System.out.println(node.getVal());
            if (node.getLeft() != null) {
                treeNodes.offer(node.getLeft());
            }
            if (node.getRight() != null) {
                treeNodes.offer(node.getRight());
            }
        }
    }
}
