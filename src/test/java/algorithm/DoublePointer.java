package algorithm;


import com.frank.model.leetcode.ListNode;
import org.junit.Test;

import javax.print.DocFlavor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoublePointer {

    /**
     * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
     * 输入: s = "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     */
    @Test
    public void testLengthOfLongestSubstring() {

        String s = "pwwkew";
        System.out.println("length = " + getLengthOfLongestSubString(s));
    }

    /**
     * 两个关键点：
     * 1：start = Math.max(position + 1, start);
     * 2：res = Math.max(res, i - start + 1);
     * @param s
     * @return
     */
    private int getLengthOfLongestSubString(String s) {
        if (s.length() == 0) {
            return 0;
        }
        Map<Character, Integer> index = new HashMap<>(64);
        int res = 0;
        int start = 0;
        Integer position;

        for (int i = 0; i < s.length(); i++) {
            position = index.get(s.charAt(i));
            if (position != null) {
                start = Math.max(position + 1, start);
            }
            res = Math.max(res, i - start + 1);
            index.put(s.charAt(i), i);
        }
        return res;
    }


    /**
     * 19. 删除链表的倒数第 N 个结点
     * 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点
     * 输入：head = [1,2,3,4,5], n = 2
     * 输出：[1,2,3,5]
     * <p>
     * 输入：head = [1], n = 1
     * 输出：[]
     */
    @Test
    public void testRemoveNthFromEnd() {
        ListNode five = new ListNode(5, null);
        ListNode four = new ListNode(4, five);
        ListNode three = new ListNode(3, four);
        ListNode two = new ListNode(2, three);
        ListNode one = new ListNode(1, two);
        ListNode head = removeNthFromEnd(one, 5);
        System.out.println(head);

        ListNode only = new ListNode(11, null);
        ListNode head2 = removeNthFromEnd(only, 1);
        System.out.println(head2);

    }

    /**
     * 两个要点：
     * 1、新建一个哑节点，dummy，可以简化很多问题
     * 2、判断条件是fast不为空
     *
     * @param head 头节点
     * @param n    要删除的倒数第几个节点
     * @return 删除的倒数第n个节点之后的头节点
     */
    private ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode fast = head;
        // 哑节点
        ListNode dummy = new ListNode(-1, head);
        ListNode slow = dummy;
        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }

        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return dummy.next;
    }


    /**
     * 给你单链表的头结点 head ，请你找出并返回链表的中间结点。
     * 如果有两个中间结点，则返回第二个中间结点。
     * 输入：head = [1,2,3,4,5,6]
     * 输出：[4,5,6]
     * 解释：该链表有两个中间结点，值分别为 3 和 4 ，返回第二个结点。
     */
    @Test
    public void testFindMiddle() {
        // ListNode six = new ListNode(6, null);
        ListNode five = new ListNode(5, null);
        ListNode four = new ListNode(4, five);
        ListNode three = new ListNode(3, four);
        ListNode two = new ListNode(2, three);
        ListNode one = new ListNode(1, two);
        ListNode middle = findMiddle(one);
        System.out.println(middle);

    }

    private ListNode findMiddle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
            if (fast.next != null) {
                fast = fast.next;
            }
        }
        return slow;
    }

    /**
     * 557. 反转字符串中的单词 III
     * 给定一个字符串 s ，你需要反转字符串中每个单词的字符顺序，同时仍保留空格和单词的初始顺序。
     * 输入：s = "Let's take LeetCode contest"
     * 输出："s'teL ekat edoCteeL tsetnoc"
     */
    @Test
    public void testReverseWord() {
        char[] sentence = "Let's take LeetCode contest".toCharArray();
        reverseSentence(sentence);
        printArray(sentence);
    }

    private void reverseSentence(char[] sentence) {
        int head = 0;
        int tail;

        for (int i = 0; i < sentence.length; i++) {
            if (sentence[i] == ' ') {
                tail = i - 1;
                change(head, tail, sentence);
                if (i + 1 < sentence.length) {
                    head = i + 1;
                }
            }
        }
        change(head, sentence.length - 1, sentence);
    }

    private void change(int head, int tail, char[] sentence) {
        char temp;
        while (tail > head) {
            if (sentence[head] != sentence[tail]) {
                temp = sentence[head];
                sentence[head] = sentence[tail];
                sentence[tail] = temp;
            }
            head++;
            tail--;
        }
    }

    /**
     * 344. 反转字符串
     * 不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。
     * 输入：s = ["h","e","l","l","o"]
     * 输出：["o","l","l","e","h"]
     */
    @Test
    public void testReverseCode() {
        char[] code = "hello".toCharArray();
        reverseCode(code);
        for (int i = 0; i < code.length; i++) {
            System.out.println(code[i]);
        }
    }

    private void reverseCode(char[] code) {
        int head = 0;
        int tail = code.length - 1;
        char temp;
        while (tail > head) {
            if (code[head] != code[tail]) {
                temp = code[head];
                code[head] = code[tail];
                code[tail] = temp;
            }
            head++;
            tail--;
        }
    }

    /**
     * 167. 两数之和 II - 输入有序数组
     * 给你一个下标从 1 开始的整数数组 numbers ，该数组已按 非递减顺序排列  ，请你从数组中找出满足相加之和等于目标数 target 的两个数。
     * 如果设这两个数分别是 numbers[index1] 和 numbers[index2] ，则 1 <= index1 < index2 <= numbers.length 。
     * 以长度为 2 的整数数组 [index1, index2] 的形式返回这两个整数的下标 index1 和 index2。
     * 你可以假设每个输入 只对应唯一的答案 ，而且你 不可以 重复使用相同的元素。
     * <p>
     * 你所设计的解决方案必须只使用常量级的额外空间。
     * 输入：numbers = [2,7,11,15], target = 9
     * 输出：[1,2]
     * 解释：2 与 7 之和等于目标数 9 。因此 index1 = 1, index2 = 2 。返回 [1, 2] 。
     */
    @Test
    public void testTwoSum2() {
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        int[] twoSum2 = getTwoSum2(nums, target);
        for (int i = 0; i < 2; i++) {
            System.out.println(twoSum2[i]);
        }

    }

    private void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }

    private void printArray(char[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
        }
    }

    public int[] getTwoSum2(int[] nums, int target) {
        int[] res = new int[2];
        int little = 0;
        int big = nums.length - 1;
        int value;
        for (int i = 0; i < nums.length; i++) {
            value = nums[little] + nums[big];
            if (value == target) {
                res[0] = little + 1;
                res[1] = big + 1;
                return res;
            } else if (value < target) {
                little++;
            } else {
                big--;
            }
        }
        return res;
    }

    /**
     * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
     * 请注意，必须在不复制数组的情况下原地对数组进行操作。
     * 输入: nums = [0,1,0,3,12]
     * 输出: [1,3,12,0,0]
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/move-zeroes
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    @Test
    public void testZeroMove() {
        // int[] nums = {0, 1, 0, 3, 12};
        int[] nums = {1, 12};
        zeroMove(nums);
        for (int i = 0; i < nums.length; i++) {
            System.out.println(nums[i]);
        }
    }

    private void zeroMove(int[] nums) {
        int left = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                left = Math.min(left, i);
            } else {
                if (i != left) {
                    nums[left] = nums[i];
                    nums[i] = 0;
                }
                left++;
            }
        }
    }

    private void zeroMove2(int[] nums) {
        int left = 0;
        for (int i = 0; i < nums.length; i++) {
            // 不等于零，左指针左移，赋值
            if (nums[i] != 0) {
                // 下标相同，不用交换了，左指针直接左移就行
                if (i != left) {
                    nums[left] = nums[i];
                }
                left++;
            }
        }

        // 之前没有交换位置，后边的全赋值0
        for (int i = left; i < nums.length; i++) {
            nums[i] = 0;
        }
    }


    /**
     * https://leetcode.cn/problems/squares-of-a-sorted-array/?envType=study-plan&id=suan-fa-ru-men&plan=algorithms&plan_progress=fqntkk3
     * 给你一个按 非递减顺序 排序的整数数组 nums，返回 每个数字的平方 组成的新数组，要求也按 非递减顺序 排序
     * 输入：nums = [-4,-1,0,3,10]
     * 输出：[0,1,9,16,100]
     * 解释：平方后，数组变为 [16,1,0,9,100]
     * 排序后，数组变为 [0,1,9,16,100]
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/squares-of-a-sorted-array
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    @Test
    public void testSortSquare() {
        int[] nums = {-4, -1, 0, 3, 10};
        final int[] res = sortSquare(nums);
        for (int i = 0; i < res.length; i++) {
            System.out.println(res[i]);
        }
    }


    private int[] sortSquare(int[] nums) {
        int head = 0;
        int tail = nums.length - 1;
        int[] res = new int[nums.length];
        int right = 0;
        int left = 0;
        for (int i = tail; i >= 0; i--) {
            if ((right = nums[tail] * nums[tail]) > (left = nums[head] * nums[head])) {
                res[i] = right;
                tail--;
            } else {
                res[i] = left;
                head++;
            }

        }
        return res;
    }
}