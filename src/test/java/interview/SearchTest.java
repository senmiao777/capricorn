package interview;

import org.junit.Test;

/**
 * @author: A + RT
 * @description:
 * @date: 2022/7/21
 **/
public class SearchTest {

    @Test
    public void testHalfSearch() {

    }

    /**
     * 查找数组中有没有等于K的元素，如果返回元素K所在下标
     *
     * @param nums 数组
     * @param k    待查找元素
     * @return 待查找元素下标
     */
    public int search(int[] nums, int k) {
        int low = 0;
        int high = nums.length;
        /**
         * 这里简答写mid = (high + low)/2 有可能会出现high + low值溢出问题
         * (high - low) / 2效率不如 (high - low) >> 1这种位移操作效率高
         */
        int mid;
        int value;
        // 注意：边界条件是low小于等于high,不是low小于high
        while (low <= high) {
            mid = low + ((high - low) >> 1);
            value = nums[mid];
            if (value == k) {
                return mid;
            } else if (value < k) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    /**
     * 二分查找递归实现
     *
     * @param nums
     * @param low
     * @param high
     * @param k
     * @return
     */
    private int search2(int[] nums, int low, int high, int k) {
        return 0;
    }
}
