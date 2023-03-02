package com.frank.model;

/**
 * 有向边
 **/
public class DirectionEdge {
    // 边的起始顶点编号
    private int startId;

    // 边的终止顶点编号
    private int endId;

    // 权重
    private int weight;

    public DirectionEdge(int startId, int endId, int weight) {
        this.startId = startId;
        this.endId = endId;
        this.weight = weight;
    }

    public int getStartId() {
        return startId;
    }

    public void setStartId(int startId) {
        this.startId = startId;
    }

    public int getEndId() {
        return endId;
    }

    public void setEndId(int endId) {
        this.endId = endId;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
