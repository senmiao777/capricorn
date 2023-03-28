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

    /**
     * 合并两个升序链表
     *
     * @param a
     * @param b
     * @return
     */
    private ListNode mergeTwoSortedList(ListNode a, ListNode b) {
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        // 哨兵节点
        ListNode dummy = new ListNode(0);
        ListNode head = dummy;
        while (a != null && b != null) {

            if (a.val < b.val) {
                dummy.next = a;
                a = a.next;
            } else {
                dummy.next = b;
                b = b.next;
            }
            dummy = dummy.next;
        }
        dummy.next = (a == null) ? b : a;
        return head.next;
    }

    @Test
    public void testRemoveCountBackwardsNth() {
        ListNode a = new ListNode(1);


        ListNode listNode = removeCountBackwardsNth(a, 1);
        System.out.println(listNode);
    }

    @Test
    public void testMiddleNode() {
        ListNode middle = getMiddle(head);
        System.out.println(middle);
    }

    /**
     * 获取中间节点 https://leetcode.com/problems/middle-of-the-linked-list/
     *
     * @param head
     * @return
     */
    private ListNode getMiddle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null) {
            fast = fast.next;
            if (fast == null) {
                return slow;
            }
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

    private ListNode removeCountBackwardsNth(ListNode head, int n) {
        if (head == null || n == 0) {
            return head;
        }
        if (n < 0) {
            throw new RuntimeException("参数异常");
        }
        ListNode fast = head;
        // 要删除节点的前驱节点
        ListNode slow = head;
        int i = 0;
        // 往前走n+1步
        while (fast != null && i < n + 1) {
            fast = fast.next;
            i++;
        }
        // fast == null退出 ,要删除的节点为头节点
        if (i == n) {
            return head.next;
        }

        if (i < n) {
            throw new RuntimeException("链表长度小于" + n);
        }

        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return head;
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
     *                时间超长
     * @return
     */
    private ListNode mergeSort(ListNode one, ListNode another) {
        if (one == null) {
            return another;
        }
        if (another == null) {
            return one;
        }
        // 两个链表都不为空，找到最小值作为头节点
        ListNode dummy = new ListNode(0);
        ListNode head = dummy;
        while (one != null && another != null) {
            if (one.val < another.val) {
                dummy.next = one;
                one = one.next;
            } else {
                dummy.next = another;
                another = another.next;
            }
            dummy = dummy.next;
        }
        dummy.next = one == null ? another : one;
        return head.next;
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
