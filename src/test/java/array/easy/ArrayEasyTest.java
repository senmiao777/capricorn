package array.easy;

import com.frank.enums.Common;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author frank
 * @version 1.0
 * @date 2018/3/4 0004 下午 9:15
 */
@Slf4j
public class ArrayEasyTest {

    /**
     * BM22 比较版本号
     * 三个要注意的点
     * 1：终止条件是 或者不是并且 i< l1 || j<l2
     * 2：while循环之后，下一步需要i++ 和 j++ ，跳过当前的 '.'
     * 3：求和的时候用long类型接收，负责可能越界
     * @param version1
     * @param version2
     * @return
     */
    public int compare (String version1, String version2) {
        // write code here
        int l1 = version1.length();
        int l2 = version2.length();

        int i =0;
        int j =0;
        while(i< l1 || j<l2){
            long num1 =0L;

            while(i< l1 &&   version1.charAt(i) != '.'){
                num1 = num1 *10 + (version1.charAt(i) - '0');
                i++;
            }
            // 注意，这步是把点跳过去
            i++;
            long num2=0L;
            while(j<l2 && version2.charAt(j) != '.'){
                num2 = num2 * 10 + (version2.charAt(j)-'0');
                j++;
            }
            // 注意，这步是把点跳过去
            j++;
            if(num1 > num2){
                return 1;
            }
            if(num1 < num2){
                return -1;
            }
        }
        return 0;
    }


    /**
     * 旋转数组最小数字
     * 思路：中间值和边界值进行比较，不断缩小区间
     * 当中间值和右边界的值相同（比如[1,1,1,1,1,1,1,0,1,1]这样）时，无法判断
     * 循环条件是 head < tail
     *
     * @param nums
     * @return
     */
    public int minNumberInRotateArray(int[] nums) {
        // write code here
        int head = 0;
        int tail = nums.length - 1;
        int mid;
        while (head < tail) {
            mid = head + (tail - head) / 2;
            if (nums[mid] > nums[tail]) {
                head = mid + 1;
            } else if (nums[mid] < nums[tail]) {
                tail = mid;
            } else {
                tail--;
            }
        }
        return nums[head];
    }


    /**
     * 找到波峰
     * 思路，中间值和其下一个值进行比较
     * 循环条件是 head < tail
     * @param nums
     * @return
     */
    public int findPeakElement(int[] nums) {
        // write code here
        int head = 0;
        int tail = nums.length - 1;
        int mid;
        while (head < tail) {
            mid = head + (tail - head) / 2;
            // 题目给的是左右边界均是最小值
            // 右边往下的不一定有波峰
            if (nums[mid] > nums[mid + 1]) {
                tail = mid;
                // 右边往上一定有波峰
            } else {
                head = mid + 1;
            }
        }
        return head;
    }


    /**
     * 二维有序数组查找目标值，从左下角开始找
     *
     * @param target
     * @param array
     * @return
     */
    public boolean Find(int target, int[][] array) {
        // write code here
        if (array.length == 0) {
            return false;
        }

        int row = array.length - 1;
        int col = array[0].length - 1;

        int curRow = row;
        int curCol = 0;
        int value;
        while (curRow >= 0 && curCol <= col) {
            value = array[curRow][curCol];
            if (value == target) {
                return true;
            } else if (value < target) {
                curCol++;
            } else {
                curRow--;
            }
        }
        return false;
    }

    /**
     * 二分查找
     * 在有序升序不重复数组中查找元素，找到返回下标，找不到返回-1
     */
    @Test
    public void testBinarySearch() {
        int[] nums = {-1, 0, 3, 4, 6, 10, 13, 14};
        //int[] nums = {-1,  9};
        int target = 13;
        System.out.println("index = " + search(nums, target));

    }

    /**
     * int head = 0;
     * int tail = nums.length - 1;
     * while (head <= tail) {
     * int m = head + (tail - head) / 2;
     * int mv = nums[m];
     * if (mv == target) {
     * return m;
     * } else if (mv > target) {
     * tail = m - 1;
     * } else {
     * head = m + 1;
     * }
     * }
     * return -1;
     *
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {
        // write code here
        if (nums.length <= 0) {
            return -1;
        }
        int head = 0;
        int tail = nums.length - 1;
        while (head <= tail) {
            int mid = head + (tail - head) / 2;
            int value = nums[mid];
            if (value == target) {
                return mid;
            } else if (value > target) {
                tail = mid - 1;
            } else {
                head = mid + 1;
            }
        }
        return -1;

    }


    @Test
    public void testFindPosition() {
        int[] nums = {1, 3, 5, 6};
        //int[] nums = {-1,  9};
        int target = 7;
        System.out.println("index = " + findPosition(nums, target));

    }

    /**
     * https://leetcode.cn/problems/first-bad-version/?envType=study-plan&id=suan-fa-ru-men&plan=algorithms&plan_progress=fqntkk3
     * 两个关键点
     * 1：当isBadVersion(m) tail = m ,而不是 m -1。因为有可能m是第一个错误的版本，而m-1就是正确的了，不是答案
     * 2：返回值是head（此时head和tail是同一个值），而不是m
     *
     * @param n
     * @return
     */
    private int firstBadVersion(int n) {
        int head = 1;
        int tail = n;
        int m = 0;
        while (head < tail) {
            m = head + (tail - head) / 2;
            if (isBadVersion(m)) {
                tail = m;
            } else {
                head = m + 1;
            }
        }
        return head;
    }

    private boolean isBadVersion(int n) {
        return n / 2 == 0;
    }

    /**
     * 两个关键位置
     * 1：while 循环是head 小于等于 tail,小于在部分情况不准
     * 2：中间下标计算位置 是 head + (tail - head)/2 直接 ( head + tail ) / 2 可能有int超长问题
     *
     * @param nums
     * @param target
     * @return
     */
    private int binarySearch(int[] nums, int target) {
        if (nums.length == 0) {
            return -1;
        }
        int head = 0;
        int tail = nums.length - 1;
        while (head <= tail) {
            int m = head + (tail - head) / 2;
            int mv = nums[m];
            if (mv == target) {
                return m;
            } else if (mv > target) {
                tail = m - 1;
            } else {
                head = m + 1;
            }
        }
        return -1;
    }

    /**
     * https://leetcode.cn/problems/search-insert-position/?envType=study-plan&id=suan-fa-ru-men&plan=algorithms&plan_progress=fqntkk3
     * <p>
     * => 找到第一个大于等于target的下标
     *
     * @param nums
     * @param target
     * @return
     */
    private int findPosition(int[] nums, int target) {

        int head = 0;
        int tail = nums.length - 1;
        int m;
        while (head <= tail) {
            m = head + (tail - head) / 2;
            if (nums[m] < target) {
                head = m + 1;
            } else {
                tail = m - 1;
            }
        }
        return head;
    }


    @Test
    public void string2Integer() {
        log.info(" ".charAt(0) == ' ' ? "true" : "false");
        char c = "a".charAt(0);
        log.info("char = {}", c + 0);
        String s = " -123456789";
        int result = string2Integer(s);
        log.info("result ={}", result);
    }

    private int string2Integer(String str) {
        if (str == null || str.isEmpty()) {
            return 0;
        }

        /**
         * 默认正数
         */
        int sign = 1;

        /**
         * 字符位置
         */
        int i = 0;

        while (str.charAt(i) == ' ') {
            i++;
        }

        if (str.charAt(i) == '-') {
            // 负号
            sign = -1;
            i++;
        }

        // 如果为正号，符号位保持不变，下标加一
        if (str.charAt(i) == '+') {
            i++;
        }

        int result = 0;
        /**
         * Integer.MIN_VALUE = -2147483648
         Integer.MAX_VALUE = 2147483647
         */

        /**
         * 注意，是 >= 0 <= 9
         * str.charAt(i) - '0' 这才是得到的数值 str.charAt(i) 得到的是ASCII码的值 a= 97
         */
        while (i < str.length() && (str.charAt(i) >= '0' && str.charAt(i) <= '9')) {
            /**
             * result / 10 > max 开始是这么写的，已经爆了
             */
            if (result > Integer.MAX_VALUE / 10 || result == Integer.MAX_VALUE / 10 && (str.charAt(i) - '0') > 7) {
                return sign > 0 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            result = result * 10 + (str.charAt(i) - '0');
            i++;
        }
        return sign > 0 ? result : result * -1;
    }

    @Test
    public void moveZeroes2() {
        log.info(Common.LOG_BEGIN.getValue());
        ArrayList<Object> objects = Lists.newArrayList();

        log.info(Common.LOG_END.getValue());
    }

    @Test
    public void moveZeroes() {
        log.info(Common.LOG_BEGIN.getValue());
        int[] nums = {0, 2, 5, 7, 0, 11, 15};
        int target = 18;
        log.info("moveZeroes={}", moveZeroes(nums));
        log.info("moveZeroes={}", nums);

        log.info(Common.LOG_END.getValue());
    }

    /**
     * Given an array nums, write a function to move all 0's to the end of it while maintaining the relative order of the non-zero elements.
     * <p>
     * For example, given nums = [0, 1, 0, 3, 12], after calling your function, nums should be [1, 3, 12, 0, 0].
     * <p>
     * Note:
     * <p>
     * You must do this in-place without making a copy of the array.
     * Minimize the total number of operations.
     *
     * @param nums
     */
    public int moveZeroes(int[] nums) {
        int index = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (nums[i] != 0) {
                nums[index] = nums[i];
                index++;
            }
        }

        for (int i = index; i < n; i++) {
            nums[i] = 0;
        }
        return index;
    }

    @Test
    public void removeElement() {
        int[] n = {1, 1, 2, 3, 3, 3, 4, 4, 3, 6, 7};
        int target = 1;
        log.info("removeElement={}", removeElement(n, target));
        log.info("numbers={}", n);
    }

    /**
     * Given an array and a value, remove all instances of that value in-place and return the new length.
     * <p>
     * Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.
     * <p>
     * The order of elements can be changed. It doesn't matter what you leave beyond the new length.
     * <p>
     * Example:
     * <p>
     * Given nums = [3,2,2,3], val = 3,
     * <p>
     * Your function should return length = 2, with the first two elements of nums being 2.
     *
     * @param numbers
     * @param target
     */
    private int removeElement(int[] numbers, int target) {
        // 需要替换的元素的位置
        int index = 0;
        if (numbers.length == 0) {
            return index;
        }


        for (int i = 0; i < numbers.length; i++) {
            //
            if (numbers[i] != target) {
                numbers[index] = numbers[i];
                index++;
            }
        }
        return index;

    }

    /**
     * Given an array of integers, return indices of the two numbers such that they add up to a specific target.
     * <p>
     * You may assume that each input would have exactly one solution, and you may not use the same element twice.
     * <p>
     * Example:
     * <p>
     * Given nums = [2, 7, 11, 15], target = 9,
     * <p>
     * Because nums[0] + nums[1] = 2 + 7 = 9,
     * return [0, 1].
     */
    @Test
    public void testTwoSum() {
        log.info(Common.LOG_BEGIN.getValue());
        int[] nums = {2, 5, 7, 11, 15};
        int target = 18;
        log.info("twoSum={}", twoSum(nums, target));
        log.info("twoSum twoSumSolution={}", twoSumSolution(nums, target));

        log.info(Common.LOG_END.getValue());
    }

    /**
     * Given a sorted array, remove the duplicates in-place such that each element appear only once and return the new length.
     * <p>
     * Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.
     */
    @Test
    public void testRemoveDuplicates() {
        log.info(Common.LOG_BEGIN.getValue());
        int[] nums = {1, 1, 1, 2, 2, 3, 4, 4, 5, 6};

        log.info("removeDuplicates={}", removeDuplicates(nums));
        log.info("nums={}", nums);

        log.info(Common.LOG_END.getValue());
    }


    int removeDuplicates(int a[]) {
        int n = a.length;
        if (n < 2) {
            return n;
        }
        int id = 1;
        for (int i = 1; i < n; ++i) {
            /**
             * 赋值索引位置从1开始，第一个元素，即数组[0]不动
             * 后边的值不等于前边的值，则个数加一
             * 有不一样的就需要往前放，放到哪个位置呢
             * 第几个不一样，就放到第几个 index =几的位置
             */
            if (a[i] != a[i - 1]) {
                a[id++] = a[i];
            }
        }
        return id;
    }

    private int[] twoSum(int[] nums, int target) {
        int size = nums.length;
        int[] result = new int[2];
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (nums[i] + nums[j] == target) {
                    result[0] = i;
                    result[1] = j;
                }
            }
        }
        return result;
    }


    public int[] twoSumSolution(int[] numbers, int target) {
        int[] result = new int[2];
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < numbers.length; i++) {
            /**
             * if 为真，说明有两个数字之和符合要求
             */
            if (map.containsKey(target - numbers[i])) {
                result[1] = i;
                result[0] = map.get(target - numbers[i]);
                return result;
            }
            /**
             * 存放元素及下标
             */
            map.put(numbers[i], i);
        }
        return result;
    }
}