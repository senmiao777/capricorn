package interview.tree;

import com.frank.model.leetcode.TreeNode;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TreeTest {

    @Test
    public void twoSim() {


    }

    public boolean leafSimilar(TreeNode left, TreeNode right) {
        List<Integer> one = new ArrayList<>();
        if (left != null) {
            dfs(left, one);
        }

        List<Integer> two = new ArrayList<>();
        if (right != null) {
            dfs(right, two);
        }
        return one.equals(two);
    }

    public void dfs(TreeNode root, List<Integer> members) {
        if (root.left == null && root.right == null) {
            members.add(root.val);
        } else {
            if (root.left != null) {
                dfs(root.left, members);
            }
            if (root.right != null) {
                dfs(root.right, members);
            }
        }
    }
}
