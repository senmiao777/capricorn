package algorithm;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: somebody
 * @time: 2019-03-21 17:03
 */
@Slf4j
public class Backtracing {

    private static final Map<String, String> PHONE_MAP = new HashMap<String, String>() {
        {
            put("2", "abc");
            put("3", "def");
            put("4", "ghi");
            put("5", "jkl");
            put("6", "mno");
            put("7", "pqrs");
            put("8", "tuv");
            put("9", "wxyz");
        }
    };

    @Test
    public void backtracing() {
        String str = "234";

        List<String>  result = new ArrayList<>();
        bt("",str,result);
        log.info("result={}",result);
    }


    /**
     * @param combination 结果集中的一个元素？
     * @param nextNumbers 输入的数字
     * @param result
     */
    private void bt(String combination, String nextNumbers, List<String> result) {
        if (nextNumbers.length() == 0) {
            result.add(combination);
        } else {
            String currentNumber = nextNumbers.substring(0, 1);
            String currentStr = PHONE_MAP.get(currentNumber);
            for (int i = 0; i < currentStr.length(); i++) {
                String substring = PHONE_MAP.get(currentNumber).substring(i, i + 1);
                bt(combination + substring, nextNumbers.substring(1), result);
            }
        }
    }
}
