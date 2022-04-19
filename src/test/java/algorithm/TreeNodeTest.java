package algorithm;


import com.frank.model.leetcode.TreeNode;
import com.google.common.collect.Lists;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import sun.reflect.generics.tree.Tree;

import java.util.LinkedList;
import java.util.Objects;
import java.util.function.Function;

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

        TreeNode treeNode = createTreeNode(numbers);
        preOrder(treeNode);


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

    public void postOrder(TreeNode node){
        if(node == null){
            return;
        }
        postOrder(node.getLeft());
        postOrder(node.getRight());
        System.out.println(node.getVal());
    }
}
