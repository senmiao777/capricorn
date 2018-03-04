package array.easy;

import com.frank.enums.Common;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

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
        log.info(Common.LOG_END.getValue());
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
}