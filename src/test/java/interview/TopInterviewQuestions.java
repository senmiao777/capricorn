package interview;


import com.frank.enums.Common;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author frank
 * @version 1.0
 * @date 2018/3/9 0004 下午 18:50
 */
@Slf4j
public class TopInterviewQuestions {

    /**
     * Given a string, find the length of the longest substring without repeating characters.
     * <p>
     * Example 1:
     * <p>
     * Input: "abcabcbb"
     * Output: 3
     * Explanation: The answer is "abc", with the length of 3.
     * <p>
     * Example 2:
     * <p>
     * Input: "bbbbb"
     * Output: 1
     * Explanation: The answer is "b", with the length of 1.
     * <p>
     * Example 3:
     * <p>
     * Input: "pwwkew"
     * Output: 3
     * Explanation: The answer is "wke", with the length of 3.
     * Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
     * <p>
     * <p>
     * int lengthOfLongestSubstring(string s) {
     * vector<int> dict(256, -1);
     * int maxLen = 0, start = -1;
     * for (int i = 0; i != s.length(); i++) {
     * if (dict[s[i]] > start)
     * start = dict[s[i]];
     * dict[s[i]] = i;
     * maxLen = max(maxLen, i - start);
     * }
     * return maxLen;
     * }
     */

    @Test
    public void getTheLengthOfLongestSubstring() {
        String s = "abcabcbb";
        final int length = getTheLengthOfLongestSubstring(s);
        log.info("length={}", length);

    }

    private int getTheLengthOfLongestSubstring(String str) {
        final int length = str.length();
        Map<Character, Integer> map = new HashMap<>(length * 2);
        int maxLength = 0;
        int start = -1;
        for (int i = 0; i < length; i++) {

            if (map.get(str.charAt(i)) != null && map.get(str.charAt(i)) > start) {
                start = map.get(str.charAt(i));
            }
            map.put(str.charAt(i), i);
            maxLength = Math.max(maxLength, i - start);
        }
        return maxLength;
    }


    @Test
    public void reverseInteger() {
        int number = 1234589000;
        final int reverse = reverse(number);
        log.info("reverse={}", reverse);
    }

    private int reverse(int number) {
        boolean negative = false;
        if (number < 0) {
            negative = true;
        }

        number = Math.abs(number);

        if (number < 10) {
            if (negative) {
                return -number;
            }
            return number;
        }
        int result = 0;
        int remainder = 0;
        /**
         * 有多少位呢，我也不知道
         * 每循环一次，就乘以10就可以了，不需要关心有多少位
         * number > 10 避免最高位一直循环
         */
        while (number / 10 > 0 && number >= 10) {
            remainder = number % 10;
            result = result * 10 + remainder;
            number = number / 10;
        }

        return negative ? -(result * 10 + number) : result * 10 + number;
    }

    /**
     * Given a string, find the length of the longest substring without repeating characters.
     * <p>
     * Examples:
     * <p>
     * Given "abcabcbb", the answer is "abc", which the length is 3.
     * <p>
     * Given "bbbbb", the answer is "b", with the length of 1.
     * <p>
     * Given "pwwkew", the answer is "wke", with the length of 3. Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
     */
    @Test
    public void longestSubstring() {
        String str1 = "abcabccdbacb";
        int length = findLength(str1);
        log.info("length={}", length);

    }

    /**
     * 滑动窗口
     *
     * @param str
     * @return
     */
    private int findLength(String str) {
        int length = str.length();
        if (length == 0) {
            return 0;
        }
        int max = 0;
        int j = 0;
        Map<Character, Integer> map = new HashMap<>(length);
        for (int i = 0; i < length; i++) {
            if (map.containsKey(str.charAt(i))) {
                // 加一
                j = Math.max(j, map.get(str.charAt(i)) + 1);
            }

            map.put(str.charAt(i), i);
            max = Math.max(max, i - j + 1);

        }
        return max;
    }

    @Test
    public void addTwoNumbers() {
        log.info(Common.LOG_BEGIN.getValue());
        ListNode l1 = new ListNode(2);

        ListNode l2 = new ListNode(4);
        // ListNode l3 = new ListNode(3);
        l1.setNext(l2);
        //   l2.setNext(l3);

        ListNode l4 = new ListNode(5);
        ListNode l5 = new ListNode(6);
        ListNode l6 = new ListNode(4);
        l4.setNext(l5);
        l5.setNext(l6);

        int sum = getNumber(l1) + getNumber(l4);
        log.info("sum={}", sum);
        ListNode head = new ListNode(sum % 10);
        sum = sum / 10;
        ListNode current = head;
        //ListNode t;
        ListNode currentNode = head;
        while (sum / 10 > 0 || sum % 10 > 0) {
            ListNode t = new ListNode(sum % 10);
            currentNode.setNext(t);
            currentNode = t;
            sum = sum / 10;
        }
        ListNode next = head;
        do {

            log.info("{}->", next.val);
            next = next.getNext();
        } while (next != null);
        log.info(Common.LOG_END.getValue());
    }

    private int getNumber(ListNode currentNode) {
        StringBuilder numberString = new StringBuilder();
        ListNode nextNode;
        do {
            nextNode = currentNode.getNext();
            numberString.append(currentNode.getVal());
            currentNode = nextNode;
        } while (nextNode != null);
        return Integer.valueOf(numberString.toString());
    }

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public void setNext(ListNode next) {
            this.next = next;
        }

        public int getVal() {
            return val;
        }

        public ListNode getNext() {
            return next;
        }
    }

}

