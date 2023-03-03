package algorithm;


import org.junit.Test;

public class DoublePointer {

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