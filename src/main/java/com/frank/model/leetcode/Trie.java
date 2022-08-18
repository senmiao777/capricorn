package com.frank.model.leetcode;

import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * @author frank
 * @version 1.0
 * @date 2021/4/14 0014 下午 11:01
 */
public class Trie {
    private Trie[] children;
    private boolean isEnd;

    // 根节点存储一个无意义字符
    private final TrieNode root = new TrieNode('/');

    public Trie() {
        children = new Trie[26];
        isEnd = false;
    }

    public static void main(String[] args) {
        Trie trie = new Trie();

    }

    public void insert2(String word) {
        if (StringUtils.isEmpty(word)) {
            return;
        }
        TrieNode cur = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            int index = c - 'a';
            if (cur.getChildren()[index] == null) {
                cur.getChildren()[index] = new TrieNode(c);
            }
            cur = cur.getChildren()[index];

        }
        cur.setEnd(true);
    }

    private boolean search2(String word) {
        if (StringUtils.isEmpty(word)) {
            return false;
        }
        TrieNode cur = root;
        for (int i = 0; i < word.length(); i++) {
            int c = word.charAt(i);
            int index = c - 'a';
            TrieNode child = cur.getChildren()[index];
            if (child != null) {
                cur = child;
            } else {
                return false;
            }
        }
        // 非最终节点，只是前缀
        return cur.isEnd();
    }

    public void insert(String word) {
        Trie node = this;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            /**
             * 计算下标，0~25 ，确定字母位置
             */
            int index = ch - 'a';
            if (node.children[index] == null) {
                node.children[index] = new Trie();
            }
            node = node.children[index];
        }
        node.isEnd = true;
    }

    public boolean search(String word) {
        Trie node = searchPrefix(word);
        return node != null && node.isEnd;
    }

    public boolean startsWith(String prefix) {
        return searchPrefix(prefix) != null;
    }

    private Trie searchPrefix(String prefix) {
        Trie node = this;
        for (int i = 0; i < prefix.length(); i++) {
            char ch = prefix.charAt(i);
            int index = ch - 'a';
            if (node.children[index] == null) {
                return null;
            }
            node = node.children[index];
        }
        return node;
    }

    @Override
    public String toString() {
        return "Trie{" +
                "children=" + Arrays.toString(children) +
                ", isEnd=" + isEnd +
                '}';
    }
}
