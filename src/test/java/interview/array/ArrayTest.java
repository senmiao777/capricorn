package interview.array;

import org.junit.Test;

import java.util.*;

//@Slf4j
public class ArrayTest {

    @Test
    public void testBinaryAdd() {
        String a = "1111";
        String b = "1111";
        System.out.println(binaryAdd(a, b));

    }

    private String binaryAdd(String a, String b) {
        // 进位数值
        int high = 0;
        int aLength = a.length();
        int bLength = b.length();
        char[] res = new char[Math.max(aLength, bLength)];
        int cur = 0;
        int i = aLength - 1;
        int j = bLength - 1;
        int t = Math.max(i, j);
        /**
         * 顺序搞反了，下标0是在最前边
         */
        while (i >= 0 && j >= 0) {
            /**
             * 0的ASCII码是48
             * 1的ASCII码是49
             */
            cur = a.charAt(i) + b.charAt(j) + high - 96;
            res[t] = (cur % 2 == 1 ? '1' : '0');
            high = cur / 2;
            i--;
            j--;
            t--;
        }
        // a 字符串比较短
        if (i == -1 && j >= 0) {
            if (high == 1) {
                for (int k = j ; k >= 0; k--) {
                    cur = b.charAt(k) + high - 48;
                    res[k] = (cur % 2 == 1 ? '1' : '0');
                    high = cur / 2;
                }
            } else {
                for (int k = j; k >= 0; k--) {
                    res[k] = b.charAt(k);
                }
            }
        } else if (j == -1 && i >= 0) {
            if (high == 1) {
                for (int k = i ; k >= 0; k--) {
                    cur = a.charAt(k) + high - 48;
                    res[k] = (cur % 2 == 1 ? '1' : '0');
                    high = cur / 2;
                }
            } else {
                for (int k = i ; k >= 0; k--) {
                    res[k] = a.charAt(k);
                }
            }
        }

        if (high == 1) {
            return "1" + new String(res);
        }
        return new String(res);
    }

    /*
     *给定一个由 整数 组成的 非空 数组所表示的非负整数，在该数的基础上加一。
     *最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
     *你可以假设除了整数 0 之外，这个整数不会以零开头。
     *输入：digits = [1,2,3]
     *输出：[1,2,4]
     *解释：输入数组表示数字 123。
     */
    @Test
    public void testAddOne() {
        int[] digits = {4, 3, 2, 1};
        int[] ints = plusOne(digits);
        for (int i = 0; i < ints.length; i++) {
            System.out.println(ints[i]);
        }


    }

    private int[] plusOne(int[] digits) {
        int cur;
        for (int i = digits.length - 1; i >= 0; i--) {
            cur = digits[i];
            if (cur == 9) {
                digits[i] = 0;
            } else {
                digits[i] = cur + 1;
                break;
            }
        }
        // 第一位是0 ，说明当前数组容量不够
        if (digits[0] == 0) {
            int[] res = new int[digits.length + 1];
            res[0] = 1;
            return res;
        }
        return digits;
    }


    /**
     * 翻转没对括号里的字符串
     * 从里向外翻转
     * (u(love)i) -> (uevoli) -> iloveu
     */
    @Test
    public void testStackReverse() {
        String s = "a(bcdefghijkl(mno)p)q";

        StringBuilder stringBuilder = new StringBuilder("abc");
        StringBuilder reverse = stringBuilder.reverse();
        System.out.println(reverse);
        System.out.println(stringBuilder);

        stringBuilder.insert(1, "defg");
        System.out.println(stringBuilder);

        System.out.println("翻转之后=" + stackReverse(s));
    }

    private String stackReverse(String str) {

        LinkedList<StringBuilder> stack = new LinkedList<>();

        StringBuilder tempStr = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            char currentChar = str.charAt(i);
            if ('(' == currentChar) {
                stack.push(tempStr);
                tempStr = new StringBuilder();
                /**
                 * 也可以这样栈里边的元素是String类型的
                 * LinkedList<String> stack = new LinkedList<>();
                 *  stack.push(sb.toString());
                 *  sb.setLength(0);
                 */
            } else if (')' == currentChar) {
                tempStr.reverse();
                // 把外层的字符串拿出来，放在前边，后边拼接当前字符串反转之后的值
                tempStr.insert(0, stack.pop());
            } else {
                tempStr.append(currentChar);
            }
        }
        return tempStr.toString();
    }

    @Test
    public void testXor() {
        int n = 10;
        int start = 5;
        System.out.println("result=" + xorOperation(n, start));
    }

    /**
     * 给你两个整数，n 和 start 。
     * <p>
     * 数组 nums 定义为：nums[i] = start + 2*i（下标从 0 开始）且 n == nums.length 。
     * 请返回 nums 中所有元素按位异或（XOR）后得到的结果。
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/xor-operation-in-an-array
     * <p>
     * 输入：n = 4, start = 3
     * 输出：8
     * 解释：数组 nums 为 [3, 5, 7, 9]，其中 (3 ^ 5 ^ 7 ^ 9) = 8.
     */
    int xorOperation(int n, int start) {
        int result = start;
        if (n == 1) {
            return result;
        }

        for (int i = 1; i < n; i++) {
            result = result ^ (start + 2 * i);
        }

        return result;
    }

    @Test
    public void testStrStr() {
        String haystack = "";
        String needle = "a";
        int i = strStr(haystack, needle);
        System.out.println("position=" + i);
    }

    public int strStr(String haystack, String needle) {
        int result = 0;
        if ("".equals(needle)) {
            return result;
        }

        int index = 0;

        for (int i = 0; i < haystack.length(); i++) {
            if (haystack.charAt(i) == needle.charAt(index)) {
                int j = i;
                result = i;
                while (index < needle.length() && j < haystack.length()) {
                    if (haystack.charAt(j) == needle.charAt(index)) {
                        j++;
                        index++;
                    } else {
                        break;
                    }
                }
                /**
                 * 如果长度相等，说明needl走到了最后，找到了子串
                 */
                if (index == needle.length()) {
                    return result;
                }

                /**
                 * 如果因为haystack结束了而退出，说明needle还没有最后，没找到子串
                 */
                if (j == haystack.length()) {
                    return -1;
                }
                /**
                 * 其他情况i下移，继续找
                 */
                index = 0;
                continue;
            }

        }
        return -1;
    }

    @Test
    public void testRepeatOnce() {
        int[] nums = {0, 1, 2, 2, 3, 0, 4, 2};
        int count = repeatOnce(nums, 2);
        for (int i = 0; i < count; i++) {
            System.out.println(nums[i]);
        }
    }

    /**
     * 给你一个有序数组 nums ，请你 原地 删除重复出现的元素，使每个元素 只出现一次 ，返回删除后数组的新长度。
     * <p>
     * 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/remove-duplicates-from-sorted-array
     */
    public int repeatOnce(int[] nums, int n) {
        return 0;
    }


    @Test
    public void testRemoveElement() {
        int[] nums = {0, 1, 2, 2, 3, 0, 4, 2};
        int count = removeElement(nums, 2);
        for (int i = 0; i < count; i++) {
            System.out.println(nums[i]);
        }
    }

    public int removeElement(int[] nums, int val) {
        int length = 0;
        if (nums == null || (length = nums.length) == 0) {
            return length;
        }

        int index = 0;
        for (int i = 0; i < length; i++) {
            if (nums[i] != val) {
                nums[index] = nums[i];
                index++;
            }
        }
        return index;
    }


    @Test
    public void testStringSort() {
        int[] strings = {3, 30, 34, 5, 9};
        Integer[] array = {3, 30, 34, 5, 9};

        Arrays.sort(array, (x, y) -> {

            return y - x;
        });

        for (Integer i : array) {
            System.out.println(i);
        }

        Arrays.sort(strings);
        System.out.println("结果=" + largestNumber(strings));


//        System.out.println(strings);

    }

    /**
     * 给定一组非负整数 nums，重新排列每个数的顺序（每个数不可拆分）使之组成一个最大的整数。
     * <p>
     * 注意：输出结果可能非常大，所以你需要返回一个字符串而不是整数
     * 输入：nums = [3,30,34,5,9]
     * 输出："9534330"
     *
     * @param nums
     * @return
     */
    private String largestNumber(int[] nums) {

        Integer[] array = new Integer[nums.length];
        for (int i = 0; i < nums.length; i++) {
            array[i] = nums[i];
        }

        /**
         * 先记住结论，前 - 后 是升序
         * 调用compare方法，当方法的返回值大于0的时候就将数组的前一个数和后一个数做交换
         *  x - y ，是前一个元素 减 下一个元素，如果大于零，说明前一个元素大，需要交换位置，升序
         *  y - x ，是后一个元素减 减 前一个元素，如果大于零，说明后一个元素大，需要交换位置，降序
         */
        Arrays.sort(array, (x, y) -> {
            long xx = 10;
            long yy = 10;
            /**
             * 小于等于
             *
             * 这里是为了判断需要补位的位数
             */
            while (xx <= x) {
                xx = xx * 10;
            }
            while (yy <= y) {
                yy = yy * 10;
            }
            return (int) (-(x * yy + y) + (y * xx + x));
        });


        if (array[0] == 0) {
            return "0";
        }
        StringBuilder result = new StringBuilder();
        for (Integer i : array) {
            result.append(i.toString());
        }
        return result.toString();

    }

    /**
     * 已知一个长度为 n 的数组，预先按照升序排列，经由 1 到 n 次 旋转 后，得到输入数组。例如，原数组 nums = [0,1,2,4,5,6,7] 在变化后可能得到：
     * 若旋转 4 次，则可以得到 [4,5,6,7,0,1,2]
     * 若旋转 4 次，则可以得到 [0,1,2,4,5,6,7]
     * 注意，数组 [a[0], a[1], a[2], ..., a[n-1]] 旋转一次 的结果为数组 [a[n-1], a[0], a[1], a[2], ..., a[n-2]] 。
     * 给你一个元素值 互不相同 的数组 nums ，它原来是一个升序排列的数组，并按上述情形进行了多次旋转。请你找出并返回数组中的 最小元素
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/find-minimum-in-rotated-sorted-array
     */
    @Test
    public void testFindMinValue() {
        int[] nums = {9, 1, 2, 3, 4, 5, 6, 7, 8};
        int minValue = findMinValue2(nums);
        System.out.println("minValue=" + minValue);

    }

    @Test
    public void testFindNthValue() {

        int nthValue = findNthValue(1);
        System.out.println("nthValue=" + nthValue);

    }

    /**
     * 找到第N个丑数，最小堆实现方式
     * 每次都从堆（由丑数构成的队列里）里取出一个元素，取N次，即为第N个丑数
     * 每次取数后，都由当前元素分别乘以 2,3,5得到新的丑数放到队列中（这一步需要用的Set去重）
     *
     * @param n
     * @return
     */
    private int findNthValue(int n) {
        Set<Long> values = new HashSet<>(n);

        int[] factors = {2, 3, 5};
        PriorityQueue<Long> heap = new PriorityQueue();
        values.add(1L);
        heap.offer(1L);

        long temp;
        long result = 1L;
        /**
         * 时间复杂度 o ( n * log(n))
         * 空间复杂度 n (Set<3n, PriorityQueue<3n)
         */
        for (int i = 0; i < n; i++) {
            result = heap.poll();
            for (int f : factors) {
                temp = result * f;
                if (values.add(temp)) {
                    heap.offer(temp);
                }
            }
        }
        return (int) result;
    }

    /**
     * 遍历法
     *
     * @return
     */
    private int findMinValue(int[] nums) {
        int length = nums.length;
        if (length == 1) {
            return nums[0];
        }
        if (nums[0] < nums[length - 1]) {
            return nums[0];
        }
        /**
         * 当前元素比上一个元素小，即找到了最小值
         */
        for (int i = 1; i < length; i++) {
            if (nums[i - 1] > nums[i]) {
                return nums[i];
            }
        }
        return 0;
    }

    /**
     * 二分思想查找
     *
     * @return
     */
    private int findMinValue2(int[] nums) {

        return 0;
    }


    /**
     * 给你一个有序数组 nums ，请你 原地 删除重复出现的元素，使每个元素 最多出现两次 ，返回删除后数组的新长度。
     * <p>
     * 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/remove-duplicates-from-sorted-array-ii
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * <p>
     * 示例 1：
     * <p>
     * 输入：nums = [1,1,1,2,2,3]
     * 输出：5, nums = [1,1,2,2,3]
     * 解释：函数应返回新长度 length = 5, 并且原数组的前五个元素被修改为 1, 1, 2, 2, 3 。 不需要考虑数组中超出新长度后面的元素。
     * <p>
     * 示例 2：
     * <p>
     * 输入：nums = [0,0,1,1,1,1,2,3,3]
     * 输出：7, nums = [0,0,1,1,2,3,3]
     * 解释：函数应返回新长度 length = 7, 并且原数组的前五个元素被修改为 0, 0, 1, 1, 2, 3, 3 。 不需要考虑数组中超出新长度后面的元素。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/remove-duplicates-from-sorted-array-ii
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    @Test
    public void testRemoveDuplicates() {


    }

    private int removeDuplicates(int[] nums) {

        if (nums == null) {
            return 0;
        }
        int length = nums.length;
        if (length < 3) {
            return length;
        }
        // 待替换位置下标
        int index = 0;
        int t = nums[0];
        int repeat = 0;
        // 发生替换的次数
        int count = 0;
        // 重复出现超过两次，则该位置需要替换为新的元素
        for (int i = 1; i < length; i++) {
            if (nums[i] == t) {
                repeat++;
            }
            if (repeat > 2) {
                index = i;
                count++;
            }

            // 当第一次出现不重复的数字，替换
            if (nums[i] != t) {
                t = nums[i];
                nums[index] = t;
                index++;
            }
        }
        return length - count;
    }
}
