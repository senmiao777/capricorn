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


    @Test
    public void testStringSort() {
        int[] strings = {3, 30, 34, 5, 9};
        Integer[] array = {3, 30, 34, 5, 9};

        Arrays.sort(array, (x, y) -> {

            return y-x;
        });

        for(Integer i:array){
            System.out.println(i);
        }

        Arrays.sort(strings);
        System.out.println("结果=" + largestNumber(strings));


//        System.out.println(strings);

    }

    /**
     * 给定一组非负整数 nums，重新排列每个数的顺序（每个数不可拆分）使之组成一个最大的整数。
     * <p>
     * 注意：输出结果可能非常大，所以你需要返回一个字符串而不是整数
     * 输入：nums = [3,30,34,5,9]
     * 输出："9534330"
     *
     * @param nums
     * @return
     */
    private String largestNumber(int[] nums) {

        Integer[] array = new Integer[nums.length];
        for (int i = 0; i < nums.length; i++) {
            array[i] = nums[i];
        }

        /**
         * 先记住结论，前 - 后 是升序
         * 调用compare方法，当方法的返回值大于0的时候就将数组的前一个数和后一个数做交换
         *  x - y ，是前一个元素 减 下一个元素，如果大于零，说明前一个元素大，需要交换位置，升序
         *  y - x ，是后一个元素减 减 前一个元素，如果大于零，说明后一个元素大，需要交换位置，降序
         */
        Arrays.sort(array, (x, y) -> {
            long xx = 10;
            long yy = 10;
            /**
             * 小于等于
             *
             * 这里是为了判断需要补位的位数
             */
            while (xx <= x) {
                xx = xx * 10;
            }
            while (yy <= y) {
                yy = yy * 10;
            }
            return (int) (-(x * yy + y) + (y * xx + x));
        });


        if (array[0] == 0) {
            return "0";
        }
        StringBuilder result = new StringBuilder();
        for (Integer i : array) {
            result.append(i.toString());
        }
        return result.toString();

    }

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
        int[] nums = {9, 1, 2, 3, 4, 5, 6, 7, 8};
        int minValue = findMinValue2(nums);
        System.out.println("minValue=" + minValue);

    }

    @Test
    public void testFindNthValue() {

        int nthValue = findNthValue(1);
        System.out.println("nthValue=" + nthValue);

    }

    /**
     * 找到第N个丑数，最小堆实现方式
     * 每次都从堆（由丑数构成的队列里）里取出一个元素，取N次，即为第N个丑数
     * 每次取数后，都由当前元素分别乘以 2,3,5得到新的丑数放到队列中（这一步需要用的Set去重）
     *
     * @param n
     * @return
     */
    private int findNthValue(int n) {
        Set<Long> values = new HashSet<>(n);

        int[] factors = {2, 3, 5};
        PriorityQueue<Long> heap = new PriorityQueue();
        values.add(1L);
        heap.offer(1L);

        long temp;
        long result = 1L;
        /**
         * 时间复杂度 o ( n * log(n))
         * 空间复杂度 n (Set<3n, PriorityQueue<3n)
         */
        for (int i = 0; i < n; i++) {
            result = heap.poll();
            for (int f : factors) {
                temp = result * f;
                if (values.add(temp)) {
                    heap.offer(temp);
                }
            }
        }
        return (int) result;
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
        /**
         * 当前元素比上一个元素小，即找到了最小值
         */
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
    private int findMinValue2(int[] nums) {

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
