package interview.integer;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * @author frank
 * @version 1.0
 * @date 2019年8月5日15:12:49
 */
@Slf4j
public class InterviewQuestions {

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
                    result = Integer.valueOf(s.substring(i,i+1));
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
                result = result * 10 + Integer.valueOf(s.substring(i,i+1));
            }

        }
        return flag * result;
    }


}

