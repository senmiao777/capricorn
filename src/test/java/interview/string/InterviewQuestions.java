package interview.string;


import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author frank
 * @version 1.0
 * @date 2019年7月29日18:32:57
 */
@Slf4j
public class InterviewQuestions {


    /**
     *
     */
    @Test
    public void testUnduplicate() {
        String s = "abcabcbb";

    }

    private int longestUnduplicateNumber(String s) {
        if (s == null || s.equals("")) {
            return 0;
        }


        return 0;
    }

    /**
     * 在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。
     * 数组中某些数字是重复的，但不知道有几个数字重复了，也不知道每个数字重复了几次。
     * 请找出数组中任意一个重复的数字。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/shu-zu-zhong-zhong-fu-de-shu-zi-lcof
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * 2 <= n <= 100000
     */
    @Test
    public void findRepeatNumberTest() {
        int[] nums = new int[10];
        for (int i = 0; i < 10; i++) {
            nums[i] = i;
        }
        nums[4] = 5;
        nums[6] = 7;
        final int repeatNumber = findRepeatNumber2(nums);
        log.info("repeatNumber={}", repeatNumber);
    }

    /**
     * 思路：1、可以用位图，有多少个数字就弄多少位，然后遍历往位图放数据
     * 放数据的时候，之前如果这一位如果是1，那就说明已经有这个数字了
     * 2、HashMap
     * 3、先排序，然后如果相邻的两个元素是同一个值，那就说明是重复的
     *
     * @param nums
     * @return
     */
    public int findRepeatNumber(int[] nums) {
        Map<Integer, Integer> numCount = new HashMap<>(nums.length * 2);

        /**
         * 遍历数组
         */
        for (int num : nums) {
            if (numCount.containsKey(num)) {
                return num;
            } else {
                numCount.put(num, num);
            }
        }
        return -1;
    }

    /**
     * 建立一个新的数组，用数组存数据，就好像建立索引
     *
     * @param nums
     * @return
     */
    public int findRepeatNumber2(int[] nums) {

        int[] newNumbers = new int[nums.length];
        /**
         * 遍历数组
         */
        for (int num : nums) {
            if (newNumbers[num] == -1) {
                return num;
            } else {
                newNumbers[num] = -1;
            }
        }
        return -1;
    }

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
        String a3 = "abecdef";
        String a4 = "adcdef";
        String a5 = "aecdef";
        List<String> list = Lists.newArrayList(a, a2, a3, a4, a5);
        log.info("getLongestStr={}", getLongestStr(list));


        String a6 = "aa";
        String a7 = "a";
        List<String> list2 = Lists.newArrayList(a6, a7);
        log.info("getLongestStr2={}", getLongestStr2(list2));

    }


    /**
     * 垂直比较
     * 很容易数组越界
     *
     * @param members
     * @return
     */
    private String getLongestStr2(List<String> members) {

        if (members == null | members.size() == 0) {
            return "";
        }
        final int size = members.size();
        int commonLength = 0;
        int longestLength = members.get(0).length();
        for (int j = 0; j < longestLength; j++) {
            char c = members.get(0).charAt(j);
            int count = 1;
            int i = 1;
            while (i < size && members.get(i).length() > j) {
                if (c != members.get(i).charAt(j)) {
                    return members.get(0).substring(0, commonLength);
                }
                count++;
                i++;
            }

            if (count == size) {
                commonLength++;
            }
        }


        return members.get(0).substring(0, commonLength);
    }

    /**
     * 获取集合中所有字符串的最长公共字符串
     * 水平比较
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

