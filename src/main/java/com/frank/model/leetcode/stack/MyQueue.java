package com.frank.model.leetcode.stack;

/**
 * @author: step
 * @description:
 * @date: 2022/7/1
 **/
public class MyQueue {

    public Node input;
    public Node output;


    public MyQueue() {

    }

    public void push(int x) {
        input = new Node(x, input);
    }

    public int peek() {
        if (empty()) {
            throw new RuntimeException("队列为空");
        }
        if (output != null) {
            return output.val;
        }
        reverse();
        return output.val;
    }

    private void reverse() {
        // 翻转链表1
        Node prev = null;
        Node cur = input;
        Node next;
        while (cur != null) {
            next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        output = prev;
        input = null;
    }

    public int pop() {
        int res = peek();
        output = output.next;
        return res;
    }

    public boolean empty() {
        return input == null && output == null;
    }


    static class Node {
        public int val;
        public Node next;

        public Node(int val, Node next) {
            this.val = val;
            this.next = next;
        }
    }
}
