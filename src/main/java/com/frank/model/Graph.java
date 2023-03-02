package com.frank.model;

import java.util.LinkedList;
import java.util.List;

/**
 * 拓扑对象
 **/
public class Graph {

    /**
     * 顶点个数
     */
    private int n;

    /**
     * 邻接表
     */
    private List<Integer>[] adjacency;

    public Graph(int n) {
        this.n = n;
        adjacency = new LinkedList[n];
        for (int i = 0; i < n; i++) {
            adjacency[i] = new LinkedList<>();
        }
    }

    /**
     * a先于b(b依赖a) a->b
     *
     * @param a
     * @param b
     */
    public void addEdge(int a, int b) {
        adjacency[a].add(b);
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public List<Integer>[] getAdjacency() {
        return adjacency;
    }

    public void setAdjacency(List<Integer>[] adjacency) {
        this.adjacency = adjacency;
    }
}
