package algorithm;


import org.junit.Test;

import java.util.Arrays;

public class DoublePointer {

    /**
     * 344. 反转字符串
     * 不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。
     * 输入：s = ["h","e","l","l","o"]
     * 输出：["o","l","l","e","h"]
     */
    @Test
    public void testReverseCode() {
        char[] code = "hello".toCharArray();
        reverseCode(code);
        for (int i = 0;i< code.length;i++){
            System.out.println(code[i]);
        }
    }

    private void reverseCode(char[] code) {
        int head = 0;
        int tail = code.length - 1;
        char temp;
        while (tail > head) {
            if (code[head] != code[tail]) {
                temp = code[head];
                code[head] = code[tail];
                code[tail] = temp;
            }
            head++;
            tail--;
        }
    }

    /**
     * 167. 两数之和 II - 输入有序数组
     * 给你一个下标从 1 开始的整数数组 numbers ，该数组已按 非递减顺序排列  ，请你从数组中找出满足相加之和等于目标数 target 的两个数。
     * 如果设这两个数分别是 numbers[index1] 和 numbers[index2] ，则 1 <= index1 < index2 <= numbers.length 。
     * 以长度为 2 的整数数组 [index1, index2] 的形式返回这两个整数的下标 index1 和 index2。
     * 你可以假设每个输入 只对应唯一的答案 ，而且你 不可以 重复使用相同的元素。
     * <p>
     * 你所设计的解决方案必须只使用常量级的额外空间。
     * 输入：numbers = [2,7,11,15], target = 9
     * 输出：[1,2]
     * 解释：2 与 7 之和等于目标数 9 。因此 index1 = 1, index2 = 2 。返回 [1, 2] 。
     */
    @Test
    public void testTwoSum2() {
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        int[] twoSum2 = getTwoSum2(nums, target);
        for (int i = 0; i < 2; i++) {
            System.out.println(twoSum2[i]);
        }

    }

    public int[] getTwoSum2(int[] nums, int target) {
        int[] res = new int[2];
        int little = 0;
        int big = nums.length - 1;
        int value;
        for (int i = 0; i < nums.length; i++) {
            value = nums[little] + nums[big];
            if (value == target) {
                res[0] = little + 1;
                res[1] = big + 1;
                return res;
            } else if (value < target) {
                little++;
            } else {
                big--;
            }
        }
        return res;
    }

    /**
     * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
     * 请注意，必须在不复制数组的情况下原地对数组进行操作。
     * 输入: nums = [0,1,0,3,12]
     * 输出: [1,3,12,0,0]
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/move-zeroes
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    @Test
    public void testZeroMove() {
        // int[] nums = {0, 1, 0, 3, 12};
        int[] nums = {1, 12};
        zeroMove(nums);
        for (int i = 0; i < nums.length; i++) {
            System.out.println(nums[i]);
        }
    }

    private void zeroMove(int[] nums) {
        int left = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                left = Math.min(left, i);
            } else {
                if (i != left) {
                    nums[left] = nums[i];
                    nums[i] = 0;
                }
                left++;
            }
        }
    }

    private void zeroMove2(int[] nums) {
        int left = 0;
        for (int i = 0; i < nums.length; i++) {
            // 不等于零，左指针左移，赋值
            if (nums[i] != 0) {
                // 下标相同，不用交换了，左指针直接左移就行
                if (i != left) {
                    nums[left] = nums[i];
                }
                left++;
            }
        }

        // 之前没有交换位置，后边的全赋值0
        for (int i = left; i < nums.length; i++) {
            nums[i] = 0;
        }
    }


    /**
     * https://leetcode.cn/problems/squares-of-a-sorted-array/?envType=study-plan&id=suan-fa-ru-men&plan=algorithms&plan_progress=fqntkk3
     * 给你一个按 非递减顺序 排序的整数数组 nums，返回 每个数字的平方 组成的新数组，要求也按 非递减顺序 排序
     * 输入：nums = [-4,-1,0,3,10]
     * 输出：[0,1,9,16,100]
     * 解释：平方后，数组变为 [16,1,0,9,100]
     * 排序后，数组变为 [0,1,9,16,100]
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/squares-of-a-sorted-array
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    @Test
    public void testSortSquare() {
        int[] nums = {-4, -1, 0, 3, 10};
        final int[] res = sortSquare(nums);
        for (int i = 0; i < res.length; i++) {
            System.out.println(res[i]);
        }
    }


    private int[] sortSquare(int[] nums) {
        int head = 0;
        int tail = nums.length - 1;
        int[] res = new int[nums.length];
        int right = 0;
        int left = 0;
        for (int i = tail; i >= 0; i--) {
            if ((right = nums[tail] * nums[tail]) > (left = nums[head] * nums[head])) {
                res[i] = right;
                tail--;
            } else {
                res[i] = left;
                head++;
            }

        }
        return res;
    }
}