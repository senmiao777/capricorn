package interview;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Created by QuantGroup on 2018/4/16.
 */
@Slf4j
public class String2Integer {
    @Test
    public void string2Integer() {
        log.info(" ".charAt(0) == ' ' ? "true" : "false");
        char c = "a".charAt(0);
        log.info("char = {}", c + 0);
        // String s = " a-123+- c4567891";
        String s = " -42";
        int result = stringToInteger2(s);
        log.info("result ={}", result);
    }

    private int stringToInteger2(String str) {
        if (str == null || str.isEmpty()) {
            return 0;
        }
        int i = 0;
        while (str.charAt(i) == ' ') {
            i++;
        }
        /**
         * 顺序执行，只会走一次
         */
        /**
         * 默认正数
         */
        int sign = 1;
        if (str.charAt(i) == '+' || str.charAt(i) == '-') {
            if (str.charAt(i) == '-') {
                sign = -1;
            }
            // 少了这个
            i++;
        }
        int result = 0;

        /**
         * str.charAt(i) - '0' 这才是得到的数值 str.charAt(i) 得到的是ASCII码的值 a= 97
         */
        while (i < str.length() && str.charAt(i) >= '0' && str.charAt(i) <= '9') {
            if (result > Integer.MAX_VALUE / 10 || result == Integer.MAX_VALUE / 10 && (str.charAt(i) - '0') > 7) {
                return sign > 0 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            result = result * 10 + (str.charAt(i) - '0');
            i++;
        }

        return sign > 0 ? result : result * -1;
    }

    private int string2Integer(String str) {
        if (str == null || str.isEmpty()) {
            return 0;
        }

        /**
         * 默认正数
         */
        int sign = 1;
        int result = 0;

        /**
         * str.charAt(i) - '0' 这才是得到的数值 str.charAt(i) 得到的是ASCII码的值 a= 97
         */
        int t = 0;
        boolean first = true;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ' ') {
                t++;
                continue;
            }
            // 第一个不是空格的字符
            if (t == i && first) {
                first = false;
                if (str.charAt(i) == '+') {
                    continue;
                } else if (str.charAt(i) == '-') {
                    sign = -1;
                } else if (str.charAt(i) >= '0' && str.charAt(i) <= '9') {
                    // go
                } else {
                    return 0;
                }
            }

            if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                continue;
            }
            if (result > Integer.MAX_VALUE / 10 || result == Integer.MAX_VALUE / 10 && (str.charAt(i) - '0') > 7) {
                return sign > 0 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            result = result * 10 + (str.charAt(i) - '0');
        }
        return sign > 0 ? result : result * -1;
    }
}
