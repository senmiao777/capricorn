package algorithm;


import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class Stack {

    /**
     * 20. 有效的括号
     * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
     * 有效字符串需满足：
     * 左括号必须用相同类型的右括号闭合。
     * 左括号必须以正确的顺序闭合。
     * 每个右括号都有一个对应的相同类型的左括号。
     */
    @Test
    public void testIsValid() {
        String s = "((";
        System.out.println("isValid = " + isValid(s));
    }

    /**
     * 要点：
     * 1：退出条件之一，当前下标大于等于栈（原有数组一半）的大小，是大于等于，不是大于
     *
     * @param s
     * @return
     */
    private boolean isValid(String s) {
        Map<Character, Character> dict = new HashMap<>(8);
        dict.put(')', '(');
        dict.put(']', '[');
        dict.put('}', '{');
        int length = s.length();
        // 字符是奇数个，肯定不匹配
        if (length % 2 != 0) {
            return false;
        }
        int half = length / 2;
        char[] container = new char[half];
        int index = 0;
        char c;
        Character t;
        for (int i = 0; i < length; i++) {
            c = s.charAt(i);
            // 左括号，入栈
            if ((t = dict.get(c)) == null) {
                if (index >= half) {
                    return false;
                }
                container[index] = c;
                index++;
            } else {
                index--;
                if (index < 0 || container[index] != t) {
                    return false;
                }
            }
        }
        return index == 0;
    }
}
