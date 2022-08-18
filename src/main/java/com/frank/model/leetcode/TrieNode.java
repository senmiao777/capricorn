package com.frank.model.leetcode;


/**
 * @author: guojingfeng
 * @description:
 * @date: 2022/8/17
 **/
public class TrieNode {
    private char data;
    private boolean isEnd = false;
    private TrieNode[] children = new TrieNode[26];
    public TrieNode(char data){
        this.data = data;
    }

    public char getData() {
        return data;
    }

    public void setData(char data) {
        this.data = data;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public TrieNode[] getChildren() {
        return children;
    }

    public void setChildren(TrieNode[] children) {
        this.children = children;
    }
}
