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

    public ListNode head;

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
    }

    @Test
    public void testReverse() {
        ListNode reverse = reverse(head);
        while (reverse != null) {
            System.out.println(reverse.val);
            reverse = reverse.next;
        }
    }

    /**
     * 单向链表翻转
     * 最后指向null 不会处理
     * @param head
     */
    private ListNode reverse(ListNode head) {


        if (head == null) {
            return null;
        }
        ListNode temp = head;
        ListNode tail = null;
        while (head != null) {
            temp = head;
            head = head.next;
            temp.next = tail;
            tail = temp;
        }

        return temp;
    }
}
