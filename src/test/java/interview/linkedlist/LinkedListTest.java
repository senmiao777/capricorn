package interview.linkedlist;

import com.frank.model.leetcode.ListNode;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * @author: guojingfeng
 * @description:
 * @date: 2022/6/23
 **/
public class LinkedListTest {

    /**
     * 链表头节点
     */
    public ListNode head;

    /**
     * 链表尾节点
     */
    public ListNode tail;


    /**
     * 第二个链表头节点
     */
    public ListNode anotherHead;

    /**
     * 第二个链表尾节点
     */
    public ListNode anotherTail;

    /**
     * 初始化一个可用链表，方便测试
     */
    @Before
    public void init() {
        ListNode one = new ListNode(10);
        ListNode two = new ListNode(22);
        ListNode three = new ListNode(32);
        ListNode four = new ListNode(42);
        ListNode five = new ListNode(52);
        ListNode six = new ListNode(62);
        one.next = two;
        two.next = three;
        three.next = four;
        four.next = five;
        five.next = six;
        head = one;
        tail = six;

        ListNode a = new ListNode(2);
        ListNode a1 = new ListNode(2);
        ListNode a2 = new ListNode(2);

        ListNode b = new ListNode(57);
        ListNode c = new ListNode(395);
        a.next = a1;
        a1.next = a2;
        a2.next = b;
        b.next = c;

        anotherHead = a;
        anotherTail = c;
    }

    @Test
    public void testReverse() {
        //ListNode reverse = reverse(head);
        ListNode reverse = reverse(new ListNode(11));
        while (reverse != null) {
            System.out.println(reverse.val);
            reverse = reverse.next;
        }
    }

    @Test
    public void testHasCycle() {
        tail.next = head;

        boolean res = hasCycle(head);

        System.out.println("hasCycle=" + res);
    }


    @Test
    public void testMergeSortedList() {
        ListNode listNode = mergeSort(head, anotherHead);
        System.out.println(listNode);
    }

    private boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
            if (fast != null) {
                fast = fast.next;
            }
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param one     第一个有序链表
     * @param another 第二个有序链表
     * @return
     */
    private ListNode mergeSort(ListNode one, ListNode another) {
        // 两个链表全为空，直接返回
        if (one == null && another == null) {
            return null;
        }
        if (one == null) {
            return another;
        }
        if (another == null) {
            return one;
        }
        // 两个链表都不为空，找到最小值作为头节点
        ListNode head = one.val < another.val ? one : another;
        ListNode firstCur = one;
        ListNode secondCur = another;
        ListNode secondPrev = null;
        ListNode firstPrev = one;
        ListNode firstFind = null;
        boolean secondLtFirst = false;
        while (firstCur != null) {
            while (secondCur != null && (firstCur.val >= secondCur.val)) {
                if (!secondLtFirst) {
                    secondLtFirst = true;
                    firstFind = secondCur;
                }
                secondPrev = secondCur;
                secondCur = secondCur.next;
            }

            if (secondLtFirst) {
                secondLtFirst = false;
                // 在第二个链表中找到比第一个链表的当前节点大的节点
                secondPrev.next = firstCur;
                if (firstPrev.val <= secondPrev.val) {
                    firstPrev.next = firstFind;
                }

                // 第二个链表已找到尾节点，退出
                if (secondCur == null) {
                    return head;
                }
            }
            firstPrev = firstCur;
            firstCur = firstCur.next;
        }
        firstPrev.next = secondCur;
        return head;
    }


    /**
     * 根据传入数组创建链表
     *
     * @param numbers
     * @return
     */
    private ListNode createListNode(int[] numbers) {
        if (numbers.length == 0) {
            return null;
        }
        return null;
    }


    /**
     * 单向链表翻转
     *
     * @param head
     */
    private ListNode reverse(ListNode head) {
        ListNode current = head;
        ListNode prev = null;
        ListNode next;
        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        return prev;
    }
}
