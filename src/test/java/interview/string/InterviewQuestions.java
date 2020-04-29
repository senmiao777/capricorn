package interview.string;


import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author frank
 * @version 1.0
 * @date 2019年7月29日18:32:57
 */
@Slf4j
public class InterviewQuestions {

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
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/longest-substring-without-repeating-characters
     */
    @Test
    public void lengthOfLongestSubstring() {
        String str = "abcabcbb";
        final int i = lengthOfLongestSubstring(str);
        log.info("lengthOfLongestSubstring={}", i);
    }

    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        int length = s.length();
        int first = 0;
        int second = 0;
        int max = 1;
        for (int i = 0; i < length; i++) {
            if (s.charAt(first) == s.charAt(i)) {
                max = Math.max(max, i - first);
                first++;
            }

        }


        return 0;
    }

    @Test
    public void jdk7NewFeature2() {
        String a = "abcdef";
        String a2 = "abcdg";
        String a3 = "habecdef";
        String a4 = "adcdef";
        String a5 = "aecdef";
        List<String> list = Lists.newArrayList(a, a2, a3, a4, a5);
        log.info("getLongestStr={}", getLongestStr(list));


    }

    /**
     * 获取集合中所有字符串的最长公共字符串
     *
     * @param members
     * @return
     */
    private String getLongestStr(List<String> members) {
        if (CollectionUtils.isEmpty(members)) {
            throw new RuntimeException("数组为空");
        }

        if (members.size() == 1) {
            return members.get(0);
        }

        String temp = members.get(0);
        for (int i = 1; i < members.size(); i++) {
            temp = longestCommonString(temp, members.get(i));
            if (temp == null) {
                return null;
            }
        }
        return temp;
    }


    /**
     * 获取两个字符串的最长公共字符
     *
     * @param first
     * @param second
     * @return
     */
    private String longestCommonString(String first, String second) {
        if (first == null || second == null) {
            return null;
        }
        /**
         * 获取短字符串的长度
         */
        int length = first.length() > second.length() ? second.length() : first.length();
        int commonLength = 0;
        for (int i = 0; i < length; i++) {
            if (first.charAt(i) == second.charAt(i)) {
                commonLength++;
            } else {
                break;
            }
        }
        if (commonLength == 0) {
            return null;
        }
        return first.substring(0, commonLength);
    }

}

