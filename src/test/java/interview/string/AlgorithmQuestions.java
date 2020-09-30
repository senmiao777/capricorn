package interview.string;


import com.frank.model.leetcode.LetterCombination;
import com.frank.model.leetcode.ListNode;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Slf4j
public class AlgorithmQuestions {


    @Test
    public void testFind() {

        ListNode tail = new ListNode(14, null);
        ListNode a1 = new ListNode(10, tail);
        ListNode a2 = new ListNode(8, a1);
        ListNode a = new ListNode(2, a2);
        ListNode one = new ListNode(1, a);
        final ListNode latestN = deleteLatestN(one, 1);
        log.info("latestN={}", latestN);
    }

    /**
     * 要删除倒数第N个节点，则找到倒数第N+1个节点，N+1 的 next 指向 N的next
     *
     * @param head
     * @param target
     * @return
     */
    private ListNode deleteLatestN(ListNode head, int target) {

        int count = -1;
        ListNode slow = head;
        ListNode res = head;
        /**
         * for (int i = 1; i <= n + 1; i++) {
         *   first = first.next;
         * }
         *
         */
        while (head != null) {
            if (count == target) {
                slow = slow.getNext();
            }
            if (count < target) {
                count++;
            }
            head = head.getNext();
        }

        /**
         * 删除头结点，特殊处理
         */
        if (slow == res && count < target) {
            return res.getNext();
        }
        ListNode current = slow;
        slow = slow.getNext();
        if (slow == null) {
            current.setNext(null);
        } else {
            current.setNext(slow.getNext());
        }

        return res;
    }


    @Test
    public void testMerge() {
        ListNode tail = new ListNode(14, null);
        ListNode a1 = new ListNode(10, tail);
        ListNode a2 = new ListNode(8, a1);
        ListNode a = new ListNode(2, a2);
        ListNode one = new ListNode(1, a);

        ListNode tail2 = new ListNode(24, null);
        ListNode b2 = new ListNode(7, tail2);
        ListNode another = new ListNode(4, b2);

        final ListNode merge = merge(one, another);
        log.info("merge={}", merge);
    }

    /**
     * Input: 1->2->4,
     * 1->3->4
     * Output: 1->1->2->3->4->4
     *
     * @param one
     * @param another
     * @return
     */
    private ListNode merge(ListNode one, ListNode another) {

        if (one == null) {
            return another;
        }
        if (another == null) {
            return one;
        }

        ListNode anotherTemp;
        ListNode head = one.getVal() <= another.getVal() ? one : another;
        ListNode pre = head;
        while (one != null && another != null) {
            if (one.getVal() > another.getVal()) {
                anotherTemp = another.getNext();
                pre.setNext(another);
                pre = another;
                another.setNext(one);
                another = anotherTemp;
            }
            one = one.getNext();
            pre = pre.getNext();
        }

        return null;
    }


    /**
     * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。
     * <p>
     * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
     */
    @Test
    public void testPhoneNumberCombination() {
        LetterCombination combination = new LetterCombination();
        List<String> strings = combination.letterCombinations2("234");
        log.info("strings={}", strings);

        String s = "12";
        String substring = s.substring(1);
        log.info("substring={}", substring);
    }


    @Test
    public void testThreeSum() {
        //int[] nums = {-1, 0, 1, 2, -1, -4};
        int[] nums = {-4, -2, -2, -2, 0, 1, 2, 2, 2, 3, 3, 4, 4, 6, 6};


        final int[][] threeSum2 = getThreeSum2(nums);

        for (int[] i : threeSum2) {
            log.info("member={}", i);
        }

        List<List<Integer>> threeSum3 = getThreeSum3(nums);

        for (List<Integer> i : threeSum3) {
            log.info("i={}", i);
        }
    }

    List<List<Integer>> getThreeSum3(int[] nums) {
        if (nums.length < 3) {
            return new ArrayList<>();
        }
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        final int length = nums.length;
        int count = 0;
        for (int i = 0; i < length; i++) {
            int start = i + 1;
            int end = length - 1;
            /**
             * 第一个值都大于零了，后边在加两个数不可能等于零
             */
            if (nums[i] > 0) {
                break;
            }
            if (nums[end] < 0) {
                break;
            }
            /**
             * 怎么防止重复呢？
             * 用过的元素不再使用，就不会重复了。
             * 比如你上次用的i下标取出的元素值为8，那下次如果i+1下标取出的元素值仍为8的话，就直接跳过。
             */
            if (res.size() > 0 && res.get(count - 1).get(0) == nums[i]) {
                continue;
            }
            while (start < end) {
                if (nums[i] + nums[start] + nums[end] < 0) {
                    start++;
                } else if (nums[i] + nums[start] + nums[end] > 0) {
                    end--;
                } else {
                    /**
                     * 比较前两个元素，去重，然后左下标右移
                     * 有缺陷
                     * 比如，排序后的数组元素是-4, -2, -2, -2, 0, 1, 2, 2, 2, 3, 3, 4, 4, 6, 6
                     * 结果有重复[-2, -2, 4]，[-2, 0, 2]，[-2, -2, 4]，[-2, 0, 2]
                     *
                     * 比如你上次用的start-1下标取出的元素值为8，那下次如果start下标取出的元素值仍为8的话，就直接跳过。
                     */
                    if ((start > i + 1) && (nums[start - 1] == nums[start])) {
                        start++;
                        continue;
                    }
                    List<Integer> temp = new ArrayList<>(4);
                    temp.add(nums[i]);
                    temp.add(nums[start]);
                    temp.add(nums[end]);
                    res.add(temp);
                    count++;
                    start++;
                }
            }
        }
        return res.subList(0, count);
    }


    /**
     * 看到数组，想着排序，很有帮助。很多情况下排好序问题就好解决了。
     *
     * @param nums
     * @return
     */
    int[][] getThreeSum2(int[] nums) {
        int[][] result = new int[nums.length][];
        if (nums.length < 3) {
            return result;
        }
        log.info("nums={}", nums);
        Arrays.sort(nums);
        log.info("after sort nums={}", nums);
        final int length = nums.length;

        int count = 0;
        for (int i = 0; i < length; i++) {

            int start = i + 1;
            int end = length - 1;

            /**
             * 第一个值都大于零了，后边在加两个数不可能等于零
             */
            if (nums[i] > 0) {
                break;
            }

            if (nums[end] < 0) {
                break;
            }

            while (start < end) {
                if (nums[i] + nums[start] + nums[end] < 0) {
                    start++;
                } else if (nums[i] + nums[start] + nums[end] > 0) {
                    end--;
                } else {

                    if (count > 0 && result[count - 1][0] == nums[i] && result[count - 1][1] == nums[start]) {
                        start++;
                        continue;
                    }
                    int[] temp = new int[3];
                    temp[0] = nums[i];
                    temp[1] = nums[start];
                    temp[2] = nums[end];
                    result[count] = temp;
                    count++;
                    start++;
                }
            }


        }
        int[][] finalResult = new int[count][];
        System.arraycopy(result, 0, finalResult, 0, count);
        return finalResult;
    }


    /**
     * 代码low且有bug，仅展示思路
     *
     * @param nums
     * @return
     */
    private int[][] getThreeSum(int[] nums) {
        final int length = nums.length;
        Map<Integer, Integer> numbers = new HashMap<>();
        for (int i = 0; i < length; i++) {
            numbers.put(i, nums[i]);
        }
        int count = 0;
        int sum = 0;
        int twoSum;

        int[][] result = new int[length][];
        for (int i = 0; i < length - 2; i++) {
            twoSum = sum - nums[i];
            for (int j = i + 1; j < length; j++) {
                for (int k = j + 1; k < length; k++) {
                    if (numbers.get(k) == twoSum - nums[j]) {
                        int[] temp = new int[3];
                        temp[0] = nums[i];
                        temp[1] = nums[j];
                        temp[2] = nums[k];
                        result[count] = temp;
                        count++;
                    }
                }

            }
        }
        return result;
    }


    @Test
    public void testMaxWater() {
        int[] nums = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        final int maxVolume = getMaxVolume2(nums);
        log.info("getMaxVolume={}", maxVolume);
    }

    private int getMaxVolume2(int[] nums) {

        int start = 0;
        int end = nums.length - 1;
        int volume = 0;
        for (int i = 0; i < nums.length && start <= end; i++) {
            volume = Math.max(volume, (end - start) * Math.min(nums[start], nums[end]));
            /**
             * start下标对应的高度低，则start后移
             * end下标对应的高度低，则end前移
             */
            if (nums[start] < nums[end]) {
                start++;
            } else {
                end--;
            }
        }
        return volume;
    }

    private int getMaxVolume(int[] nums) {
        int length = nums.length;
        int maxHeight = nums[0];
        int volume = 0;
        for (int i = 0; i < length; i++) {
            /**
             * 高度低于之前的，长度也小于之前的，一定不是最大容量
             */
            if (i > 0 && maxHeight > nums[i]) {
                continue;
            }
            maxHeight = nums[i];
            for (int j = i + 1; j < length; j++) {
                volume = Math.max(volume, (j - i) * Math.min(nums[i], nums[j]));
            }
        }
        return volume;
    }

    @Test
    public void testZ() {
        String s = "LEETCODEISHIRING";
//          LDREOEIIECIHNTSG
//          LDREOEIIECIHNTSG
        log.info("getLongestPalindrom(s)={}", zCycle2(s, 3));
    }

    /**
     * 按行读取方式
     *
     * @param s
     * @param height
     * @return
     */
    private String zCycle2(String s, int height) {

        if (s == null) {
            return s;
        }

        final int length = s.length();
        if (height >= length) {
            return s;
        }

        List<StringBuilder> lineContents = new ArrayList<>(height);

        for (int i = 0; i < height; i++) {
            lineContents.add(new StringBuilder());
        }
        /**
         * 行数移动标识，false：向下；true:向上
         */
        boolean up = true;
        int currentLine = 0;
        for (int i = 0; i < length; i++) {
            log.info("i={},currentLine={}", i, currentLine);
            StringBuilder builder = lineContents.get(currentLine);
            builder.append(s.charAt(i));
            /**
             * 第一行和最后一行，需要改变标识
             */
            if (currentLine == 0 || currentLine == (height - 1)) {
                up = !up;
            }
            currentLine += (up ? -1 : 1);
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < height; i++) {
            result = result.append(lineContents.get(i));
        }

        return result.toString();
    }

    /**
     * 二维数组的方式，时间复杂度，空间复杂度都很高
     *
     * @param s
     * @param height
     * @return
     */
    private String zCycle(String s, int height) {
        final int length = s.length();
        if (height >= length || height == 1) {
            return s;
        }

        int[][] matrix = new int[height][length];
        int y = 0;

        int x = 0;

        for (int i = 0; i < length; i++) {
            matrix[x][y] = s.charAt(i);
            if (y % (height - 1) == 0) {
                x++;
            }
            if (y % (height - 1) > 0) {
                y++;
                x--;
            }

            /**
             * 一列的最后一个元素
             */
            if (x == height) {
                y++;
                x = height - 2;
            }


        }

        StringBuilder a = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < length; j++) {
                if (matrix[i][j] > 0) {
                    a.append(String.valueOf((char) matrix[i][j]));
                }
            }
        }

        return a.toString();
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

