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
        // String s = " -123+- c456789123456";
        String s = " -+- c";
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
         * str.charAt(i) - '0' 这才是得到的数值 str.charAt(i) 得到的是ASCII码的值 a= 97
         */
        for (; i < str.length(); i++) {
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
