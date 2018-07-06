package interview;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.List;

/**
 * Created by frank on 2018/4/16.
 */
@Slf4j
public class String2Integer {

    // 初始化数组
    public static final List<Character> NUMBER = Lists.newArrayList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');

    public static final Character BLANK = ' ';

    public static final Character ADD = '+';

    public static final Character MINUS = '-';

    @Test
    public void test() {
        log.info(" NUMBER.contains(c)={}", NUMBER.contains("1".charAt(0)));
        String s = "  234234 sdf d3467";

        final Integer integer = string2Integer(s);
        log.info("integer={}", integer);

    }

    private Integer string2Integer(String str) {
        if (StringUtils.isBlank(str)) {
            return Integer.valueOf(0);
        }
        final int length = str.length();
        boolean numberBegin = false;

        // 默认是正数
        boolean negtive = false;
        Character temp;
        Integer result = 0;

        for (int i = 0; i < length; i++) {
            temp = str.charAt(i);
            // 找到数字起始的点
            if (!numberBegin && MINUS.equals(temp)) {
                negtive = true;
                numberBegin = true;
                continue;
            }

            if (!numberBegin && ADD.equals(temp)) {
                numberBegin = true;
                continue;
            }

            if (NUMBER.contains(temp)) {
                result = result * 10 + (int)temp;
            }
            //TODO  超过INT_MAX (2147483647) or INT_MIN (-2147483648) 没思路处理
            // TODO 各种判断有点low啊
            /**
             * TODO sddsd
             * ddd
             *
             */
        }


        return result;

    }
}
