package com.frank.model;

/**
 * 为Dijkstra(迪杰斯特拉)算法准备的
 **/
public class Vertex {
    // 顶点编号
    private int id;

    // 从 起始顶点 到 这个顶点的距离
    private int distance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Vertex(int id, int distance) {
        this.id = id;
        this.distance = distance;
    }
}
