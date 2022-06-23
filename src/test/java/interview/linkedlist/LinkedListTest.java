package interview.linkedlist;

import com.frank.model.leetcode.ListNode;
import org.junit.Before;
import org.springframework.context.annotation.Bean;

/**
 * @author: guojingfeng
 * @description:
 * @date: 2022/6/23
 **/
public class LinkedListTest {


    @Before
    public void init(){
        ListNode one = new ListNode(1);
        ListNode two = new ListNode(2);
        ListNode three = new ListNode(3);
        ListNode four = new ListNode(4);
        ListNode five = new ListNode(3);
        ListNode six = new ListNode(4);
        one.next = two;
        two.next = three;
        three.next = four;
        four.next = five;
        five.next = six;
    }
}
