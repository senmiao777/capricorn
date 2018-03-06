package array.easy;

import com.frank.enums.Common;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

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