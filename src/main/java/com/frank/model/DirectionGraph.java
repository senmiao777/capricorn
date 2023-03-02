package com.frank.model;

import java.util.LinkedList;

/**
 * 有向图
 */
public class DirectionGraph {
    // 邻接表
    private LinkedList<DirectionEdge>[] adjacency;

    // 顶点个数
    private int n;

    public DirectionGraph(int n) {
        this.n = n;
        this.adjacency = new LinkedList[n];
        for (int i = 0; i < n; i++) {
            adjacency[i] = new LinkedList<>();
        }
    }

    // 添加边
    public void addEdge(int startId, int endId, int weight) {
        this.adjacency[startId].add(new DirectionEdge(startId, endId, weight));
    }

    public LinkedList<DirectionEdge>[] getAdjacency() {
        return adjacency;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
}
