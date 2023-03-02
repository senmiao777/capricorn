package com.frank.model;

import java.util.LinkedList;

/**
 * 根据vertex的distance构建小顶堆
 **/
public class VertexPriorityQueue {

    private DirectionGraph graph;
    private Vertex[] nodes;
    private int count;

    public VertexPriorityQueue(DirectionGraph graph) {
        this.graph = graph;
        this.count = graph.getN();
        nodes = new Vertex[count + 1];
    }

    public Vertex poll() {
        // TODO
        return null;
    }

    public void add(Vertex vertex) {
        // TODO
    }

    public boolean isEmpty() {
        // TODO
        return false;
    }

    public void update(Vertex vertex) {

    }

    public DirectionGraph getGraph() {
        return graph;
    }

    public void setGraph(DirectionGraph graph) {
        this.graph = graph;
    }

    public void dijkstra(int start, int end) {
        /**
         predecessor
         英[ˈpriːdəsesə(r)]
         美[ˈpredəsesər]
         n.	前任; 原先的东西; 被替代的事物;

         用来还原最短路径
         数组里存的是当前顶点的前驱顶点
         */
        int[] predecessor = new int[this.count];

        Vertex[] vertices = new Vertex[this.count];

        for (int i = 0; i < this.count; i++) {
            vertices[i] = new Vertex(i, Integer.MAX_VALUE);
        }
        // TODO 构建 DirectionGraph
        DirectionGraph graph = new DirectionGraph(this.count);

        VertexPriorityQueue queue = new VertexPriorityQueue(graph);
        // 标记是否进入过队列
        boolean[] inQueue = new boolean[this.count];
        vertices[start].setDistance(0);
        queue.add(vertices[start]);
        inQueue[start] = true;
        while (!queue.isEmpty()) {
            // 取出最小顶点并删除
            Vertex minVertex = queue.poll();
            // 找到最短路径，退出
            if (minVertex.getId() == end) {
                break;
            }
            LinkedList<DirectionEdge>[] adjacency = graph.getAdjacency();
            for (int i = 0; i < adjacency[minVertex.getId()].size(); i++) {
                // 取出一条minVertex相连的边
                DirectionEdge edge = adjacency[minVertex.getId()].get(i);

                // minVertex -> nextVertex
                /**
                 * 取出与minVertex相连的节点
                 *
                 */
                Vertex nextVertex = vertices[edge.getEndId()];
                // 更新nextVertex 的 distance
                /**
                 * 如果minVertex 的距离加上当前边的权重小于nextVertex的距离，那么说明有一条更短的路径能到到nextVertex
                 * 更新nextVertex的距离
                 */
                if (minVertex.getDistance() + edge.getWeight() < nextVertex.getDistance()) {
                    nextVertex.setDistance(minVertex.getDistance() + edge.getWeight());
                    predecessor[nextVertex.getId()] = minVertex.getId();
                    if (inQueue[nextVertex.getId()]) {
                        queue.update(nextVertex);
                    } else {
                        queue.add(nextVertex);
                        // 将当期那顶点ID添加到入队状态队列中，防止重复入队
                        inQueue[nextVertex.getId()] = true;
                    }
                }
            }
        }
        System.out.println(start);
        print(start, end, predecessor);
    }

    private void print(int start, int end, int[] predecessor) {
        if (start == end) {
            return;
        }
        print(start, predecessor[end], predecessor);
        System.out.println("->" + end);
    }

    public Vertex[] getNodes() {
        return nodes;
    }

    public void setNodes(Vertex[] nodes) {
        this.nodes = nodes;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public VertexPriorityQueue() {
    }
}
