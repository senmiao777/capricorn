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

}
