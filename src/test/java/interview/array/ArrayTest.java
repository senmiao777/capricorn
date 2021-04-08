package interview.array;

import com.frank.model.leetcode.LetterCombination;
import com.frank.model.leetcode.ListNode;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Slf4j
public class ArrayTest {

    /**
     * 已知一个长度为 n 的数组，预先按照升序排列，经由 1 到 n 次 旋转 后，得到输入数组。例如，原数组 nums = [0,1,2,4,5,6,7] 在变化后可能得到：
     * 若旋转 4 次，则可以得到 [4,5,6,7,0,1,2]
     * 若旋转 4 次，则可以得到 [0,1,2,4,5,6,7]
     * 注意，数组 [a[0], a[1], a[2], ..., a[n-1]] 旋转一次 的结果为数组 [a[n-1], a[0], a[1], a[2], ..., a[n-2]] 。
     * 给你一个元素值 互不相同 的数组 nums ，它原来是一个升序排列的数组，并按上述情形进行了多次旋转。请你找出并返回数组中的 最小元素
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/find-minimum-in-rotated-sorted-array
     */
    @Test
    public void testFindMinValue() {
        int[] nums = {3, 4, 5, 1, 2};
        int minValue = findMinValue(nums);
        System.out.println("minValue=" + minValue);

    }

    /**
     * 遍历法
     *
     * @return
     */
    private int findMinValue(int[] nums) {
        int length = nums.length;
        if (length == 1) {
            return nums[0];
        }
        if (nums[0] < nums[length - 1]) {
            return nums[0];
        }
        for (int i = 1; i < length; i++) {
            if (nums[i - 1] > nums[i]) {
                return nums[i];
            }
        }
        return 0;
    }

    /**
     * 二分思想查找
     *
     * @return
     */
    private int findMinValue2() {
        int[] nums = {3, 4, 5, 1, 2};
        int length = nums.length;
        if (length == 1) {
            return nums[0];
        }
        if (nums[0] < nums[length - 1]) {
            return nums[0];
        }
        for (int i = 1; i < length; i++) {
            if (nums[i - 1] > nums[i]) {
                return nums[i];
            }
        }
        return 0;
    }


    /**
     * 给你一个有序数组 nums ，请你 原地 删除重复出现的元素，使每个元素 最多出现两次 ，返回删除后数组的新长度。
     * <p>
     * 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/remove-duplicates-from-sorted-array-ii
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * <p>
     * 示例 1：
     * <p>
     * 输入：nums = [1,1,1,2,2,3]
     * 输出：5, nums = [1,1,2,2,3]
     * 解释：函数应返回新长度 length = 5, 并且原数组的前五个元素被修改为 1, 1, 2, 2, 3 。 不需要考虑数组中超出新长度后面的元素。
     * <p>
     * 示例 2：
     * <p>
     * 输入：nums = [0,0,1,1,1,1,2,3,3]
     * 输出：7, nums = [0,0,1,1,2,3,3]
     * 解释：函数应返回新长度 length = 7, 并且原数组的前五个元素被修改为 0, 0, 1, 1, 2, 3, 3 。 不需要考虑数组中超出新长度后面的元素。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/remove-duplicates-from-sorted-array-ii
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    @Test
    public void testRemoveDuplicates() {


    }

    private int removeDuplicates(int[] nums) {

        if (nums == null) {
            return 0;
        }
        int length = nums.length;
        if (length < 3) {
            return length;
        }
        // 待替换位置下标
        int index = 0;
        int t = nums[0];
        int repeat = 0;
        // 发生替换的次数
        int count = 0;
        // 重复出现超过两次，则该位置需要替换为新的元素
        for (int i = 1; i < length; i++) {
            if (nums[i] == t) {
                repeat++;
            }
            if (repeat > 2) {
                index = i;
                count++;
            }

            // 当第一次出现不重复的数字，替换
            if (nums[i] != t) {
                t = nums[i];
                nums[index] = t;
                index++;
            }
        }
        return length - count;
    }
}
