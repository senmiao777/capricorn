package interview.linkedlist;

import com.frank.model.leetcode.ListNode;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.Bean;

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
     * 初始化一个可用链表，方便测试
     */
    @Before
    public void init() {
        ListNode one = new ListNode(1);
        ListNode two = new ListNode(2);
        ListNode three = new ListNode(3);
        ListNode four = new ListNode(4);
        ListNode five = new ListNode(5);
        ListNode six = new ListNode(6);
        one.next = two;
        two.next = three;
        three.next = four;
        four.next = five;
        five.next = six;
        six.next = null;
        head = one;
        tail = six;
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
