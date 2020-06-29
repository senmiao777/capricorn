package interview;


import com.frank.enums.Common;
import com.frank.model.leetcode.LinkedOneWayList;
import com.frank.model.leetcode.ListNode;
import com.frank.other.Node;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.util.*;
import java.util.Stack;

/**
 * @author frank
 * @version 1.0
 * @date 2018/3/9 0004 下午 18:50
 */
@Slf4j
public class TopInterviewQuestions {

    /**
     * 单向链表反转
     */
    @Test
    public void testReverse() {
        LinkedOneWayList list = new LinkedOneWayList();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        ListNode head = list.add(5);

    }


    @Test
    public void testMap2222() {
        LinkedOneWayList list = new LinkedOneWayList();
        list.add(1);
//        list.add(2);
//        list.add(3);
//        list.add(4);
//        list.add(4);
//        list.add(5);
//        list.add(5);
//        list.add(4);
//        list.add(3);
//        list.add(2);
        ListNode add = list.add(1);
        ListNode head = add;
        final boolean palindrome = palindrome(head);
        log.info("palindrome={}", palindrome);
    }

    /**
     * 判断一个单向链表是否为“回文链表”
     *
     * @param head
     * @return
     */
    private boolean palindrome(ListNode head) {
        if (head.getNext() == null) {
            return true;
        }
        ListNode fast = head;
        ListNode slow = head;
        /**
         * 计数，为了确定链表节点是奇数个还是偶数个
         */
        int count = 0;
        ListNode reverse = new ListNode(head.getVal(), null);
        ListNode temp;
        while (fast.getNext() != null) {
            count++;
            fast = fast.getNext();
            slow = slow.getNext();
            temp = new ListNode(slow.getVal(), reverse);
            reverse = temp;
            if (fast.getNext() != null) {
                fast = fast.getNext();
                count++;
            }
        }
        /**
         * 链表节点个数为偶数的时候，reverse会“多走一步”，debug调试看出来的
         * 所以回退一步
         */
        if ((count + 1) % 2 == 0) {
            reverse = reverse.getNext();
        }

        while (reverse != null && slow != null) {
            if (reverse.getVal() == slow.getVal()) {
                reverse = reverse.getNext();
                slow = slow.getNext();
            } else {
                return false;
            }
        }
        return slow == null && reverse == null;
    }

    /**
     * Search Insert Position
     */
    @Test
    public void testPosition() {
        int[] nums = {1, 2, 4, 6, 7};
        int target = 3;
        log.info("Position={}", searchPosition(nums, target));


    }

    private int searchPosition(int[] nums, int target) {

        final int length = nums.length;
        if (nums[0] >= target) {
            return 0;
        }
        if (nums[length - 1] == target) {
            return length - 1;
        }
        if (nums[length - 1] < target) {
            return length;
        }

        /**
         * 二分查找
         *
         [1,2,4,6,7] 3
         3 ->1
         */
        int start = 1;
        int end = length - 1;

        for (int i = 1; i < length - 1; i++) {
            int middle = (start + end) / 2;
            if (nums[middle] == target) {
                return middle;
            } else if (nums[middle] < target) {
                start = middle + 1;
            } else {
                end = middle - 1;
            }
            /**
             * 当start >= end时，注意可能有多种情况，注意下标的确定
             */
            if (start >= end) {
                if (nums[start] >= target) {
                    return start;
                } else {
                    return start + 1;
                }
            }

        }
        return 1;
    }

    /**
     * 判断是否是有效括号对
     * https://leetcode.com/problems/valid-parentheses/
     */
    @Test
    public void testValidParentheses() {
        String s = "(){[()]}{}";
        log.info("valid={}", valid(s));


    }


    /**
     * 注意，当Stack里没有元素时，调用peek方法会跑抛异常
     *
     * @param str
     * @return
     */
    private boolean valid(String str) {

        if ("".equals(str)) {
            return true;
        }
        final int length = str.length();
        if (length % 2 != 0) {
            return false;
        }
        final char start = str.charAt(0);
        if (start == '}' || start == ']' || start == ')') {
            return false;
        }
        final char end = str.charAt(length - 1);
        if (end == '{' || end == '[' || end == '(') {
            return false;
        }


        Map<Character, Character> config = new HashMap<>(8);
        config.put('}', '{');
        config.put(']', '[');
        config.put(')', '(');

        Stack<Character> container = new Stack();
        for (int i = 0; i < length; i++) {
//            if (str.charAt(i) == '}') {
//                if (container.empty() || !config.get('}').equals(container.peek())) {
//                    return false;
//                } else {
//                    container.pop();
//                }
//            } else if (str.charAt(i) == ']') {
//                if (container.empty() || !config.get(']').equals(container.peek())) {
//                    return false;
//                } else {
//                    container.pop();
//                }
//            } else if (str.charAt(i) == ')') {
//                if (container.empty() || !config.get(')').equals(container.peek())) {
//                    return false;
//                } else {
//                    container.pop();
//                }
//            } else {
//                container.push(str.charAt(i));
//            }
            /**
             * 优化下写法
             */
            char c = str.charAt(i);
            if (config.containsKey(c)) {
                char top = container.empty() ? '?' : container.pop();
                if (top != config.get(c)) {
                    return false;
                }
            } else {
                container.push(str.charAt(i));
            }
        }


        return container.empty();
    }


    /**
     * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
     * 输出：7 -> 0 -> 8
     * 原因：342 + 465 = 807
     * <p>
     * 24356
     * 566
     * ->7 0 8 5678
     * <p>
     * <p>
     * 开始的思路，判断两个链表A和B都不为空的时候，执行逻辑。然后后边得分别判断A不为空时，XX，B不为空时XX。
     * 问题1：同样的逻辑得执行两遍，代码冗余。
     * 2：当链表为空的时候，对值的处理很麻烦。
     */
    @Test
    public void testTwoAdd() {
        ListNode six2 = new ListNode(6, null);
        ListNode five2 = new ListNode(5, six2);
        ListNode three = new ListNode(3, five2);
        ListNode four = new ListNode(4, three);

        ListNode two = new ListNode(2, four);


        ListNode four2 = new ListNode(6, null);
        ListNode six = new ListNode(6, four2);

        ListNode five = new ListNode(5, six);
        // ListNode add = add(two, five);

        ListNode one = null;//new ListNode(1, null);

        ListNode nine1 = new ListNode(9, null);
        ListNode nine2 = new ListNode(9, nine1);


        ListNode add = add2(one, nine2);

        log.info("add2={}", add);
    }

    private ListNode add2(ListNode one, ListNode another) {
        int sum = 0;
        int first = 0;
        int second = 0;
        int flag = 0;
        ListNode head = null;
        ListNode temp = null;
        while (one != null || another != null) {
            first = one == null ? 0 : one.getVal();
            second = another == null ? 0 : another.getVal();

            sum = first + second + flag;
            ListNode n = new ListNode(sum % 10);
            if (head == null) {
                head = n;
            } else {
                temp.setNext(n);
            }

            temp = n;

            flag = sum / 10;
            one = one == null ? null : one.getNext();
            another = another == null ? null : another.getNext();

        }

        if (flag == 1) {
            temp.setNext(new ListNode(1));
        }

        return head;
    }

    private ListNode add(ListNode one, ListNode another) {

        int sum = 0;
        /**
         * 进位标志
         */
        boolean flag = (sum = one.getVal() + another.getVal()) >= 10 ? true : false;
        /**
         * 初始化第一个元素
         */
        ListNode head = new ListNode(flag ? sum - 10 : sum);
        ListNode first = head;
        one = one.getNext();
        another = another.getNext();
        ListNode temp;
        while (one != null && another != null) {
            sum = flag ? one.getVal() + another.getVal() + 1 : one.getVal() + another.getVal();
            if (sum >= 10) {
                sum = sum - 10;
                flag = true;
            } else {
                flag = false;
            }
            temp = new ListNode(sum);
            head.setNext(temp);
            head = temp;

            one = one.getNext();
            another = another.getNext();
        }

        if (one == null) {
            if (another == null) {
                return first;
            }

            if (!flag) {
                head.setNext(another);
            }

            /**
             * 9加1进位的情况
             */
            while (another.getVal() == 9 && another != null) {
                temp = new ListNode(0);
                head.setNext(temp);
                head = temp;
                another = another.getNext();
            }

            if (another != null) {
                another.setVal(another.getVal() + 1);
                head.setNext(another);
            } else {
                head.setNext(new ListNode(1));
            }
            return first;
        }


        /**
         *   2435678
         *   564
         *
         *
         *   ->7 0 8 5678
         */
        if (another == null) {
            if (!flag) {
                head.setNext(one);
            }
            while (flag && one.getVal() == 9 && one != null) {
                temp = new ListNode(0);
                head.setNext(temp);
                head = temp;
                one = one.getNext();
            }

            if (one != null) {
                one.setVal(one.getVal() + 1);
                head.setNext(one);
            } else {
                head.setNext(new ListNode(1));
            }

            return first;
        }
        return null;
    }


    @Test
    public void implementIndexOf() {
        String haystack = "hello";
        String needle = "ll";
        final int i = strStr(haystack, needle);
        log.info("implementIndexOf i ={}", i);
        needle.indexOf("d");
    }

    private int strStr(String haystack, String needle) {

        if ("".equals(needle)) {
            return 0;
        }
        int temp = 0;
        int needleLength = needle.length();
        int haystackLength = haystack.length();
        for (int i = 0; i < haystack.length(); i++) {
            int j = i;
            while (temp < needleLength && j < haystackLength && needle.charAt(temp) == haystack.charAt(j)) {
                temp++;
                j++;
            }
            if (needleLength == temp) {
                return i;
            } else {
                temp = 0;
            }

        }

        return -1;
    }

    @Test
    public void removeNthNodeFromEndofList() {
        Node n5 = new Node(5, null);
        Node n4 = new Node(4, n5);
        Node n3 = new Node(3, n4);
        Node n2 = new Node(2, n3);
        Node n1 = new Node(1, n2);

        final Node node = removeNthNodeFromEndofList(n1, 0);
        log.info("n1={}", node);
    }

    /**
     * 快慢指针
     *
     * @param head
     * @param n    移除倒数第N个节点
     *             <p>
     *             删除倒数第N个，那么慢的指针比快的指针慢N
     */
    private Node removeNthNodeFromEndofList(Node head, int n) {
        if (n <= 0) {
            return head;
        }
        Node fast = head;
        Node slow = head;
        /**
         * 先让快速指针向前移动N次
         */
        for (int i = 0; i < n; i++) {
            fast = fast.getNext();
        }

        /**
         * 移除的N等于链表的长度，则移除的是头节点
         */
        if (fast == null) {
            return head.getNext();
        }

        /**
         * 快慢指针同时向后移动
         */
        while (fast.getNext() != null) {
            fast = fast.getNext();
            slow = slow.getNext();
        }
        /**
         * 慢指针的下一节点指向快指针
         */
        slow.setNext(slow.getNext().getNext());
        return head;
    }


    @Test
    public void removeDuplicates() {
        int[] numbers = new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        final int length = removeDuplicates(numbers);
        log.info("length={},length={}", length, numbers);
    }

    private int removeDuplicates(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        /**
         * 默认替换下标位置
         */
        int index = 1;
        for (int i = 1; i < nums.length; i++) {
            /**
             * 不相等，则把当前位置的元素放到index位置
             */
            if (nums[i - 1] != nums[i]) {
                nums[index] = nums[i];
                index++;
            }
        }

        return index;
    }

    @Test
    public void longestPrefix() {
        String[] strList = {"flower", "flow", "flight"};
        //String[] strList = {"abab", "aba", ""};
        final int length = strList.length;
        log.info("length={}", length);

        final String longestPrefix = getLongestPrefix3(strList);
        log.info("longestPrefix={}", longestPrefix);


    }


    /**
     * 思路：每次用第一个元素和剩下的所有元素比，没有就用第一个元素删除最后一个字符在和剩下的每一个元素比较
     * 还没有，在减，以此类推。
     *
     * @param strList
     * @return
     */
    String getLongestPrefix(String[] strList) {

        final int length = strList.length;
        if (length == 0) {
            return "";
        }
        String s = strList[0];
        while (s.length() > 0) {
            for (int i = 1; i < length; i++) {
                if (!strList[i].startsWith(s)) {
                    s = s.substring(0, s.length() - 1);
                    break;
                }

                if (i == length - 1) {
                    return s;
                }
            }

        }

        return "";
    }


    /**
     * 思路：
     * LCP(S1…Sn)=LCP(LCP(LCP(S1,S2),S3),…Sn)
     * 前两个元素得到最长共同前缀a,在用a和第三个元素得到共同前缀b，在用b和第四个元素得到共同前缀c...
     */
    String getLongestPrefix2(String[] strList) {

        final int length = strList.length;
        if (length == 0) {
            return "";
        }
        String longestPrefix = strList[0];
        /**
         * 元素个数
         */
        for (int i = 1; i < length; i++) {
            final int currentLength = longestPrefix.length();
            /**
             * 当前最长前缀长度
             */
            for (int j = 0; j < currentLength; j++) {
                if (!strList[i].startsWith(longestPrefix)) {
                    longestPrefix = longestPrefix.substring(0, longestPrefix.length() - 1);
                } else {
                    break;
                }
            }

            if (longestPrefix.length() == 0) {
                return "";
            }

            /**
             * while (strs[i].indexOf(prefix) != 0) {
             prefix = prefix.substring(0, prefix.length() - 1);
             if (prefix.isEmpty()) return "";
             }
             */
        }

        return longestPrefix;
    }

    /**
     * 思路：
     * Vertical scanning
     */
    String getLongestPrefix3(String[] strList) {
        final int length = strList.length;
        if (length == 0) {
            return "";
        }
        if (length == 1) {
            return strList[0];
        }
        String longestPrefix = strList[0];
        for (int i = 0; i < longestPrefix.length(); i++) {
            final char c = longestPrefix.charAt(i);
            for (int j = 1; j < length; j++) {
                if (strList[j].length() == i || strList[j].charAt(i) != c) {
                    return longestPrefix.substring(0, i);
                }
            }

        }
        return longestPrefix;
    }


    /**
     * https://leetcode.com/problems/container-with-most-water/description/
     * 思路：先从两个端点开始算，然后y轴数值小的往里移动。
     */
    @Test
    public void maxCapacity() {
        int number = 100000;
        String s = "";
        s.startsWith("");
        int[] y = new int[number];
        for (int i = 0; i < number; i++) {
            y[i] = RandomUtils.nextInt(1, 100);
        }
        log.info("max capacity={}", capacity(y));

        log.info("max capacity={}", capacity2(y));
    }


    /**
     * 循环嵌套，效率低
     *
     * @param y
     * @return
     */
    int capacity(int[] y) {
        final long l = System.currentTimeMillis();
        final int length = y.length;
        if (length < 2) {
            throw new RuntimeException("数组异常");
        }
        int capacity = 0;
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                capacity = Math.max(capacity, Math.min(y[i], y[j]) * (j - i));
            }
        }
        log.info("capacity cost = {}", System.currentTimeMillis() - l);
        return capacity;
    }

    /**
     * 单层， 一次搞定
     *
     * @param y
     * @return
     */
    int capacity2(int[] y) {
        final long l = System.currentTimeMillis();
        final int length = y.length;
        if (length < 2) {
            throw new RuntimeException("数组异常");
        }
        int capacity = 0;
        int left = 0;
        int right = length - 1;
        while (left < right) {
            capacity = Math.max(capacity, Math.min(y[left], y[right]) * (right - left));

            if (y[left] < y[right]) {
                left++;
            } else {
                right--;
            }
        }
        log.info("capacity2 cost = {}", System.currentTimeMillis() - l);
        return capacity;
    }

    @Test
    public void testSpeed() {
        log.info("64 >> 6={}", 64 >> 6);

        final long start = System.currentTimeMillis();
        int number = Integer.MAX_VALUE;

        log.info("number={}", number);
        for (int i = 0; i < number; i++) {
            final int i1 = 64 >> 6;
//            if(i % n == 0){
//                log.info("move i={}",i);
//            }
        }
        log.info("move cost={}", System.currentTimeMillis() - start);

        final long now = System.currentTimeMillis();
        for (int i = 0; i < number; i++) {
            final int i1 = 64 / 64;
//            if(i % n == 0){
//                log.info("divide j={}",i);
//            }
        }

        log.info("divide cost={}", System.currentTimeMillis() - now);

    }


    @Test
    public void bitSet() {

        /**
         * 得到一个可以支持0~127的位图
         * 默认所有位都是0，即false
         */
        final BitSet bitSet = new BitSet(128);
        /**
         * 对位进行设置
         */
        bitSet.set(0);
        bitSet.set(1);
        bitSet.set(2);
        bitSet.set(3);
        bitSet.set(5);
        bitSet.set(32);
        bitSet.set(33);
        bitSet.set(35);
        bitSet.set(50);
        bitSet.set(51);
        bitSet.set(53);
        bitSet.set(99);
        bitSet.set(97);
        bitSet.set(98);
        bitSet.set(91);
        bitSet.set(111);
        bitSet.set(122);


        // 返回此 BitSet 中设置为 true 的位数。
        final int cardinality = bitSet.cardinality();

        log.info("cardinality={}", cardinality);
        log.info("0={}", bitSet.get(0));
        log.info("1={}", bitSet.get(1));
        log.info("2={}", bitSet.get(2));
        log.info("3={}", bitSet.get(3));
        log.info("4={}", bitSet.get(4));
        log.info("5={}", bitSet.get(5));

        log.info("30 >> 6={}", 30 >> 6);
        log.info("2 >> 6={}", 2 >> 6);
        log.info("63 >> 6={}", 63 >> 6);
        log.info("64 >> 6={}", 64 >> 6);
        log.info("65 >> 6={}", 65 >> 6);
    }


    /**
     * 假设需要排序或者查找的总数N=10000000，
     * 那么我们需要申请内存空间的大小为int a[1 + N/32]，其中：a[0]在内存中占32为可以对应十进制数0-31，依次类推：
     * bitmap表为：
     * a[0]--------->0-31
     * a[1]--------->32-63
     * a[2]--------->64-95
     * a[3]--------->96-127
     * <p>
     * 现在我们假设有100个不重复的数，范围是[0,127]
     * 用bitMap只要存四个元素就能搞定这个事
     * java 的 BitSet 就是这个原理，不过底层是用的long
     */
    @Test
    public void bitMap() {
        int[] bmap = new int[4];
        log.info("bmap={}", bmap);
        setVal(bmap, 0);
        setVal(bmap, 1);
        setVal(bmap, 2);
        setVal(bmap, 3);
        setVal(bmap, 5);
        setVal(bmap, 32);
        setVal(bmap, 33);
        setVal(bmap, 35);
        setVal(bmap, 50);
        setVal(bmap, 51);
        setVal(bmap, 53);
        setVal(bmap, 99);
        setVal(bmap, 97);
        setVal(bmap, 98);
        setVal(bmap, 91);
        setVal(bmap, 111);
        setVal(bmap, 122);

        // 47 ,262155 ,0,8
        log.info("bmap={}", bmap);

        final boolean b = testVal(bmap, 3);
        log.info("122={}", b);
        final boolean b2 = testVal(bmap, 4);
        log.info("121={}", b2);
    }


    @Test
    public void reverseInte2ger() {

        int number = 3;
        /**
         <<      :     左移运算符，num << 1,相当于num乘以2
         >>      :     右移运算符，num >> 1,相当于num除以2
         >>>    :     无符号右移，忽略符号位，空位都以0补齐
         M << N   == M 乘以 2的N次方
         */
        log.info("reverse={}", number << 4);

        int[] bmap = new int[3];

        log.info("bmap={}", bmap);
        setVal(bmap, 0);
        setVal(bmap, 1);
        setVal(bmap, 2);
        setVal(bmap, 3);
        setVal(bmap, 5);
        setVal(bmap, 32);
        setVal(bmap, 33);
        setVal(bmap, 35);
        // 47 ,3 ,0
        log.info("bmap={}", bmap);


        System.out.println("*******调用JDK中的库方法--开始********");
        int n = 100;
        BitSet bitArray = new BitSet(n);
        int ARRNUM = 20;
        int[] array = getArray(ARRNUM, n);
        for (int i = 0; i < ARRNUM; i++) {
            bitArray.set(array[i] - 1000);
        }
        int count = 0;
        for (int j = 0; j < bitArray.length(); j++) {
            if (bitArray.get(j)) {
                System.out.print(j + 1000 + " ");
                count++;
            }
        }
        System.out.println();
        System.out.println("排序后的数组大小为：" + count);
        System.out.println("*******调用JDK中的库方法--结束********");

    }

    public int[] getArray(int ARRNUM, int N) {

        @SuppressWarnings("unused")
        int array1[] = {1000, 1002, 1032, 1033, 6543, 9999, 1033, 1000};

        int array[] = new int[ARRNUM];
        System.out.println("数组大小：" + ARRNUM);
        Random r = new Random();
        for (int i = 0; i < ARRNUM; i++) {
            array[i] = r.nextInt(N) + 1000;
        }

        System.out.println(Arrays.toString(array));
        return array;
    }

    private void setVal(int[] bmap, int val) {
        bmap[val / 32] |= (1 << (val % 32));
        //bmap[val>>5] != (val&0x1F);//这个更快？
    }

    boolean testVal(int[] bmap, int val) {
//        final int i = bmap[val / 32] & (1 << (val % 32));
//        log.info("i={}",i);

        final int i1 = bmap[val >> 5] & (val & 0x1F);
        log.info("i={}", i1);
        return i1 == 1;
        //return bmap[val>>5] & (val&0x1F);
    }

    /**
     * Given a string, find the length of the longest substring without repeating characters.
     * Example 1:
     * Input: "abcabcbb"
     * Output: 3
     * Explanation: The answer is "abc", with the length of 3.
     * Example 2:
     * Input: "bbbbb"
     * Output: 1
     * Explanation: The answer is "b", with the length of 1.
     * Example 3:
     * Input: "pwwkew"
     * Output: 3
     * Explanation: The answer is "wke", with the length of 3.
     * Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
     * <p>
     * <p>
     * 思路：滑动窗口
     * 最长的不重复字符串长度 = Max (之前得到的最长的不重复字符串长度，(当前位置 - 起始位置))
     * 起始位置 = “上次出现的和当前元素相同的元素的下一个位置”，这是个逻辑上的概念。
     * 当前和之前出现过的一个元素（比如X）重复了，那么从X的下一个开始算就不重复了。
     */

    @Test
    public void getTheLengthOfLongestSubstring() {
        String s = "abcabdcbbenmkloi";
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
        byte[] b = new byte[10];
        final Optional<byte[]> b1 = Optional.ofNullable(b);
        if (b1.isPresent()) {
            log.info("isPresent = true");
        } else {
            log.info("isPresent = false");
        }
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
        int start = -1;
        Map<Character, Integer> map = new HashMap<>(length);
        for (int i = 0; i < length; i++) {
            if (map.containsKey(str.charAt(i))) {
                // 加一
                start = Math.max(start, map.get(str.charAt(i)));
            }

           /*
           这样也可以，总之是start只能往前，不能往后。
           if (map.containsKey(str.charAt(i)) && map.get(str.charAt(i)) > start) {
                // 加一
                start = map.get(str.charAt(i)) ;
            }
*/
            map.put(str.charAt(i), i);
            max = Math.max(max, i - start);

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

            log.info("{}->", next.getVal());
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


}

