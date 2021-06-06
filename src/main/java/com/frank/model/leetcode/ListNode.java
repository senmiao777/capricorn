package com.frank.model.leetcode;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ListNode {
    int val;
    ListNode next;

    public ListNode(int x) {
        val = x;
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    /**
     * [1,2,6,3,4,5,6
     *
     * @param args
     */
    public static void main(String[] args) {
        ListNode one = new ListNode(1);
        ListNode two = new ListNode(2);
        one.next = two;
        ListNode three = new ListNode(6);
        two.next = three;

        ListNode four = new ListNode(3);
        three.next = four;

        ListNode five = new ListNode(3);
        four.next = five;

        ListNode six = new ListNode(4);
        five.next = six;

        ListNode listNode = removeVal(one, 3);
        System.out.println(listNode);

    }

    /**
     * 删除指定value的节点
     * @param node
     * @param num
     * @return
     */
    public static ListNode removeVal(ListNode node, int num) {
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = node;
        ListNode temp = dummyHead;

        while (temp.next != null) {
            if (temp.next.val == num) {
                temp.next = temp.next.next;
            } else {
                temp = temp.next;
            }
        }
        return dummyHead.next;
    }

}
