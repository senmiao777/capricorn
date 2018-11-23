package interview;


import com.frank.enums.Common;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.util.*;

/**
 * @author frank
 * @version 1.0
 * @date 2018/3/9 0004 下午 18:50
 */
@Slf4j
public class TopInterviewQuestions {

    @Test
    public void longestPrefix() {
        String[] strList = {"flower", "flow", "flight"};
        final int length = strList.length;
        log.info("length={}", length);

        final String longestPrefix = getLongestPrefix(strList);
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

