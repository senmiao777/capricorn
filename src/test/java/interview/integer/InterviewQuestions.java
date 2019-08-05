package interview.integer;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

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
        log.info("after reverseInteger n={}", i);
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


}

