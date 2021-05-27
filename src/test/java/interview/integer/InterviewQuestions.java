package interview.integer;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author frank
 * @version 1.0
 * @date 2019年8月5日15:12:49
 */
@Slf4j
public class InterviewQuestions {

    @Test
    public void testHangMingDistance() {
        int left = 1;
        int right = 4;
        int i = left ^ right;
        System.out.println("异或操作结果=" + i);
        System.out.println("res=" + hangMingDistance(left, right)
        );


    }

    private int hangMingDistance(int left, int right) {
        int result = 0;
        int xor = left ^ right;
        int one = 1;
        while (one <= xor) {
            if ((one & xor) != 0) {
                result++;
            }
            one = one << 1;
        }
        return result;
    }

    @Test
    public void getWay() {
        System.out.println(getWays2(100));
    }

    long getWays(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        return getWays(n - 1) + getWays(n - 2);
    }


    long getWays2(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        long a = 1;
        long b = 2;
        long temp = 0;
        for (int i = 3; i <= n; i++) {
            temp = a + b;
            a = b;
            b = temp;
        }
        return temp;
    }

    @Test
    public void twoSum() {
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        log.info("twoSum ={}", twoSum(nums, target));
    }

    private int[] twoSum(int[] nums, int target) {
        /**
         * 数组的元素为key,元素坐标为value
         */
        Map<Integer, Integer> index = new HashMap<>();
        final int[] result = new int[2];
        index.put(nums[0], 0);
        for (int i = 1; i < nums.length; i++) {
            if (index.get(target - nums[i]) != null) {
                result[0] = index.get(target - nums[i]);
                result[1] = i;
                return result;
            } else {
                index.put(nums[i], i);
            }

        }
        return result;
    }


    @Test
    public void wash() {
        int n = 10;
        int[] numbers = new int[n];
        for (int i = 0; i < n; i++) {
            numbers[i] = i;
        }

        for (int i = 0; i < n; i++) {
            log.info("before={}", numbers[i]);
        }

        int count = 7;
        wash(numbers, count);
        for (int i = 0; i < numbers.length; i++) {
            log.info("after={}", numbers[i]);
        }
    }

    /**
     * 随机从数组中取出N个不重复的数
     *
     * @param nums
     * @param n
     * @return
     */
    private void wash(int[] nums, int n) {

        int len = nums.length;
        if (n > len) {
            throw new RuntimeException("越界异常");
        }

        if (n == len) {
            return;
        }


        int index = 0;
        int temp = 0;
        for (byte i = 0; i < n; i++) {
            index = RandomUtils.nextInt(i + 1, len);
            log.info("index ={}", index);
            temp = nums[i];
            nums[i] = nums[index];
            nums[index] = temp;
        }
    }


    /**
     * Given a 32-bit signed integer, reverse digits of an integer.
     * <p>
     * Example 1:
     * <p>
     * Input: 123
     * Output: 321
     * <p>
     * Example 2:
     * <p>
     * Input: -123
     * Output: -321
     * <p>
     * Example 3:
     * <p>
     * Input: 120
     * Output: 21
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/reverse-integer
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * <p>
     * 问题1：这么知道是否超过了Integer.MAX_VALUE
     */
    @Test
    public void reverse() {
        int n = 12345670;
        final int i = reverseInteger(n);
        log.info("after reverseInteger n={},Integer.MAX_VALUE={}", i, Integer.MAX_VALUE);

        BigDecimal a = null;

        BigDecimal bigDecimal = new BigDecimal("10");

        log.info("bigDecimal={}", bigDecimal.add(a));
    }


    @Test
    public void str2int() {
        String s = "42";
        final int i = string2Int(s);
        log.info("after str2int s={},i={}", s, i);
    }


    public int reverseInteger(int number) {
        int flag = 1;
        if (number < 0) {
            flag = -1;
            number = number * -1;
        }

        int result = 0;
        int current;
        while (number > 0) {
            current = number % 10;

            /**
             * 最后一位，需要做溢出判断
             */
            if (number < 10) {
                if (result > Integer.MAX_VALUE / 10) {
                    return 0;
                } else if (result == Integer.MAX_VALUE / 10 && current > Integer.MAX_VALUE % 10) {
                    return 0;
                }
            }
            result = result * 10 + current;
            number = number / 10;
        }

        return result * flag;
    }


    public int string2Int(String s) {

        Set<Character> c = new HashSet<>();
        c.add('+');
        c.add('-');
        c.add('0');
        c.add('1');
        c.add('2');
        c.add('3');
        c.add('4');
        c.add('5');
        c.add('6');
        c.add('7');
        c.add('8');
        c.add('9');


        if (s == null || s.length() == 0) {
            return 0;
        }

        boolean isFirst = true;
        int flag = 1;
        int result = 0;
        char current;
        int len = s.length();

        for (int i = 0; i < len; i++) {

            if (' ' == (current = s.charAt(i))) {
                if (isFirst) {
                    continue;
                } else {
                    return result;
                }
            }

            if (isFirst) {
                isFirst = false;
                if (!c.contains(current)) {
                    return 0;
                }

                if ('-' == current) {
                    flag = -1;
                } else if ('+' == current) {
                    continue;
                } else {
                    result = Integer.valueOf(s.substring(i, i + 1));
                }
            } else {
                if (!c.contains(current)) {
                    return flag * result;
                }
                /**
                 * 判断是否越界
                 */
                if (result > Integer.MAX_VALUE / 10) {
                    return flag * Integer.MAX_VALUE;
                } else if (result == Integer.MAX_VALUE / 10 && (int) current > Integer.MAX_VALUE % 10) {
                    return flag * Integer.MAX_VALUE;
                }
                result = result * 10 + Integer.valueOf(s.substring(i, i + 1));
            }

        }
        return flag * result;
    }


}

