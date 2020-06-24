package com.frank.model.leetcode;

/**
 * @author jingfeng.guo
 * @since 2020-06-24
 */
public class LinkedOneWayList {

    ListNode head = null;
    ListNode tail = null;

    public ListNode add(int num) {

        if (head == null) {
            ListNode node = new ListNode(num, null);
            head = node;
            tail = node;
        } else {
            ListNode node = new ListNode(num, null);
            tail.setNext(node);
            tail = node;
        }
        return head;
    }

    public ListNode addTop(int num){
        if (head == null) {
            ListNode node = new ListNode(num, null);
            head = node;
            tail = node;
        } else {
            ListNode node = new ListNode(num, null);
            tail.setNext(node);
        }
        return head;
    }
}
