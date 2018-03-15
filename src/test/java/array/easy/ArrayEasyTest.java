package array.easy;

import com.frank.enums.Common;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
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

        for(int i = index;i<n;i++){
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