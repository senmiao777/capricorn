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
public class AlgorithmQuestions {


    @Test
    public void testZ() {
        String s = "LEETCODEISHIRING";

        log.info("getLongestPalindrom(s)={}", zCycle(s, 3));
    }


    private String zCycle(String s, int hight) {
        final int length = s.length();
        if (hight >= length || hight == 1) {
            return s;
        }

        int wide = length / hight + 1;

        int[][] matrix = new int[wide][hight];
        int y = 0;

        int x = 0;

        for (int i = 0; i < length; i++) {
            matrix[x][y] = s.charAt(i);
            if (x % (hight - 1) == 0) {
                y++;
            }

            if (x != 0 && x % (hight - 1) != 0) {
                x++;
                y--;
            }

            /**
             * 一列的最后一个元素
             */
            if (y == hight) {
                x++;
                y = y - 2;
            }


        }

        StringBuilder a = new StringBuilder();
        for (int i = 0; i < wide; i++) {
            for (int j = 0; j < hight; j++) {
                if (matrix[i][j] > 0) {
                    a.append(String.valueOf(matrix[i][j]));
                }
            }
        }
        log.info("s={}", a.toString());

        return null;
    }


    @Test
    public void testLongestPalindrom() {
        String s = "0ababab5";
        String s1 = "0abcddcbab5";

        log.info("getLongestPalindrom(s)={}", getLongestPalindrom(s));
    }

    /**
     * 中心扩展发解决找出最长回文子串
     *
     * @param s
     * @return
     */
    private String getLongestPalindrom(String s) {
        int start = 0;
        int end = 0;
        for (int i = 0; i < s.length(); i++) {
            int length1 = longestLength(i, i, s);
            log.info("i={},length1={}", i, length1);
            int length2 = longestLength(i, i + 1, s);
            /**
             * 找到了最大的距离，怎么把左右的位置记录下来？
             */
            int result = Math.max(length1, length2);
            log.info("i={},length2={}", i, length2);
            if (result > end - start) {
                start = i - (result - 1) / 2;
                end = i + result / 2;
            }

        }
        return s.substring(start, end + 1);
    }

    /**
     * 注意
     * 1、传进来的值要拷贝一个副本用，直接用会导致“下一次”的位置是错的
     * 2、left >= 0,不是>0,为啥？没想明白，举例试试确实是要这么做
     *
     * @param i
     * @param j
     * @param s
     * @return
     */
    private int longestLength(int i, int j, String s) {
        int left = i;
        int right = j;
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left - 1;
    }


    /**
     * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
     */
    @Test
    public void testCycle() {

        long a = 21120L;
        log.info("palindrome ={}", palindrome(a));
        int b = 21120;
        log.info("palindrome2 ={}", palindrome2(b));
    }

    /**
     * palindrome
     * 英[ˈpælɪndrəʊm]
     * 1、怎么知道一个数字有多少位？
     * 了避免数字反转可能导致的溢出问题，考虑只反转数字的一半？
     * 毕竟，如果该数字是回文，其后半部分反转后应该与原始数字的前半部分相同。
     * <p>
     * 例如，输入 1221，我们可以将数字 “1221” 的后半部分从 “21” 反转为 “12”，并将其与前半部分 “12” 进行比较，因为二者相同，我们得知数字 1221 是回文。
     * <p>
     * 2、怎么知道数字到了一半了？
     * 这个是关键，如果，“后半部分”的数字，大于等于“前半部分”的数字，那就说明到了一半了
     * 链接：https://leetcode-cn.com/problems/palindrome-number/solution/hui-wen-shu-by-leetcode/
     */
    private boolean palindrome(long num) {
        if (num < 0) {
            return false;
        }

        long number = num;
        long reverse = 0L;
        while (number / 10 > 0) {
            reverse = reverse * 10 + number % 10;
            number /= 10;
        }
        reverse = reverse * 10 + number % 10;
        return reverse == num;
    }

    private boolean palindrome2(int num) {
        if (num < 0) {
            return false;
        }
        if (num < 10) {
            return true;
        }
        /**
         * 2200
         * 21120
         * reverse =  0    2     21
         * num     2112    211   21
         * ->只要一个大于10的数字的最后一位是0，那肯定不是回文数了
         */
        if (num % 10 == 0) {
            return false;
        }

        int reverse = 0;
        while (reverse < num) {
            reverse = reverse * 10 + num % 10;

            num /= 10;
        }

       /* if (reverse == num) {
            return true;
        }
        //原值是12321,reverse = 123,num =12这种情况
        return reverse == num * 10 + reverse % 10;*/
        return reverse == num || reverse % 10 == num;
    }

    /**
     * Implement atoi which converts a string to an integer.
     * <p>
     * The function first discards as many whitespace characters as necessary until the first non-whitespace character is found. Then, starting from this character, takes an optional initial plus or minus sign followed by as many numerical digits as possible, and interprets them as a numerical value.
     * <p>
     * The string can contain additional characters after those that form the integral number, which are ignored and have no effect on the behavior of this function.
     * <p>
     * If the first sequence of non-whitespace characters in str is not a valid integral number, or if no such sequence exists because either str is empty or it contains only whitespace characters, no conversion is performed.
     * <p>
     * If no valid conversion could be performed, a zero value is returned.
     * <p>
     * Note:
     * <p>
     * Only the space character ' ' is considered as whitespace character.
     * Assume we are dealing with an environment which could only store integers within the 32-bit signed integer range: [−231,  231 − 1]. If the numerical value is out of the range of representable values, INT_MAX (231 − 1) or INT_MIN (−231) is returned.
     * Example 1:
     * <p>
     * Input: "42"
     * Output: 42
     * Example 2:
     * <p>
     * Input: "   -42"
     * Output: -42
     * Explanation: The first non-whitespace character is '-', which is the minus sign.
     * Then take as many numerical digits as possible, which gets 42.
     * Example 3:
     * <p>
     * Input: "4193 with words"
     * Output: 4193
     * Explanation: Conversion stops at digit '3' as the next character is not a numerical digit.
     * Example 4:
     * <p>
     * Input: "words and 987"
     * Output: 0
     * Explanation: The first non-whitespace character is 'w', which is not a numerical
     * digit or a +/- sign. Therefore no valid conversion could be performed.
     * Example 5:
     * <p>
     * Input: "-91283472332"
     * Output: -2147483648
     * Explanation: The number "-91283472332" is out of the range of a 32-bit signed integer.
     * Thefore INT_MIN (−231) is returned.
     * <p>
     * 几个要注意的点：
     * 1、边界，是否超过int的最大值
     * 2、正负号
     */
    @Test
    public void testGetNumber() {

        String s = "42";

        log.info("getNumber={}", getNumber(s));
    }

    private int getNumber(String s) {
        final char BLANK = ' ';
        final char PLUS = '+';
        final char MINUS = '-';
        boolean negative = false;
        int num = 0;
        int min = Integer.valueOf('0');
        int max = Integer.valueOf('9');
        boolean findFirst = false;
        for (int i = 0; i < s.length(); i++) {

            if (!findFirst) {
                if (s.charAt(i) == BLANK) {
                    continue;
                } else if (s.charAt(i) == PLUS) {
                    findFirst = true;
                    continue;
                } else if (s.charAt(i) == MINUS) {
                    findFirst = true;
                    negative = true;
                    continue;
                } else if (min <= s.charAt(i) || s.charAt(i) <= max) {
                    findFirst = true;
                } else {
                    return 0;
                }
            }

            /**
             * 非数字，结束
             */
            if (s.charAt(i) < min || s.charAt(i) > max) {
                break;
            }
            int currentValue = s.charAt(i) - min;
            if (!negative && (Integer.MAX_VALUE / 10) == num) {
                if (Integer.MAX_VALUE % 10 <= currentValue) {
                    return Integer.MAX_VALUE;
                }
                return num * 10 + currentValue;
            }
            if (!negative && (Integer.MAX_VALUE / 10) < num) {
                return Integer.MAX_VALUE;
            }
            if (negative && -(Integer.MIN_VALUE / 10) < num) {
                return Integer.MIN_VALUE;
            }
            if (negative && -(Integer.MIN_VALUE / 10) == num) {
                if (-(Integer.MIN_VALUE % 10) <= currentValue) {
                    return Integer.MIN_VALUE;
                }
                return -(num * 10 + currentValue);
            }
            num = num * 10 + currentValue;
        }

        return negative ? -num : num;
    }


    /**
     * 获取最长不重复字符个数
     */
    @Test
    public void testUnduplicate() {
        String s = "abcadbcbb";
        final int i = longestUnduplicateNumber(s);
        log.info("longestUnduplicateNumber is {}", i);

    }

    private int longestUnduplicateNumber(String s) {
        if (s == null || s.equals("")) {
            return 0;
        }

        final int length = s.length();

        int index = 0;
        int result = 0;
        Map<Character, Integer> position = new HashMap(length);
        for (int i = 0; i < length; i++) {

            /**
             * 到某个位置重复了，说明“之前没重复”
             * 有重复的，就把这个位置跳过去，从下一位置开始算
             */
            if (position.containsKey(s.charAt(i))) {
                index = Math.max(index, position.get(s.charAt(i)) + 1);
            }
            position.put(s.charAt(i), i);
            result = Math.max(result, i - index + 1);
        }


        return result;
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

