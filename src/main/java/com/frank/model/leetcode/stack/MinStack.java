package com.frank.model.leetcode.stack;

/**
 * @author: step
 * @description:
 * @date: 2022/7/1
 * <p>
 * <p>
 * Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.
 * <p>
 * Implement the MinStack class:
 * <p>
 * MinStack() initializes the stack object.
 * void push(int val) pushes the element val onto the stack.
 * void pop() removes the element on the top of the stack.
 * int top() gets the top element of the stack.
 * int getMin() retrieves the minimum element in the stack.
 * 开始没想到怎么能每次在 O（1）时间内取出最小值。
 * 想的是有个一变量专门存储这个最小值，每次入栈时，更新一下最小值，这样一旦出栈就不知道下一个最小值是多少了。想的是用排序，但是排序取值做不到 O(1)
 * 解决方案是，每次元素入栈时，都记录一下该元素入库时，栈元素的最小值
 * You must implement a solution with O(1) time complexity for each function.
 */
public class MinStack {

    /**
     * 注意这里不能用static ,否则会导致所有MinStack对象共用最小值
     */
    public Node head;

    public MinStack() {

    }

    /**
     * pushes the element val onto the stack.
     *
     * @param val
     */
    public void push(int val) {
        if (head == null) {
            head = new Node(val, val, null);
        } else {
            head = new Node(val, head.minVal < val ? head.minVal : val, head);
        }
    }

    /**
     * removes the element on the top of the stack.
     */
    public void pop() {
        head = head.next;
    }

    /**
     * gets the top element of the stack.
     *
     * @return
     */
    public int top() {
        return head.val;
    }

    /**
     * retrieves the minimum element in the stack.
     *
     * @return
     */
    public int getMin() {
        return head.minVal;
    }

    static class Node {
        public int val;
        public int minVal;
        public Node next;

        public Node(int val, int minVal, Node next) {
            this.val = val;
            this.minVal = minVal;
            this.next = next;
        }
    }
}
