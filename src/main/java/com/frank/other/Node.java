package com.frank.other;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * @author frank
 * @version 1.0
 * @date 2018/7/11 0011 下午 3:37
 * 单向链表
 * 单向链表的最后一个元素的next是null，用好这个
 */
@Slf4j
@ToString
public class Node {

    private int data;
    private Node next;

    public Node(int data, Node node) {
        this.data = data;
        this.next = node;
    }

    public Node() {
    }

    public Node(Node next) {
        this.next = next;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
