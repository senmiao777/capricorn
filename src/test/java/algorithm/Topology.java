package algorithm;


import com.frank.model.Graph;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Topology {

    /**
     * 拓扑排序
     * 如何确定代码源文件的编译依赖关系？
     * 我们知道，一个完整的项目往往会包含很多代码源文件。编译器在编译整个项目的时候，需要按照依赖关系，依次编译每个源文件。比如，A.cpp依赖B.cpp，那在
     * 编译的时候，编译器需要先编译B.cpp，才能编译A.cpp。
     * 编译器通过分析源文件或者程序员事先写好的编译配置文件（比如Makefile文件），来获取这种局部的依赖关系。那编译器又该如何通过源文件两两之间的局部依
     * 赖关系，确定一个全局的编译顺序呢？
     * <p>
     * 就好比穿衣服，是先穿秋裤，在穿裤子，再穿鞋
     * 先穿衬衣，在打领带，而领带和鞋之间没有依赖关系
     * <p>
     * 如果a先于b执行，也就是说b依赖于a，那么就在顶点a和顶点b之间，构建一条从a指向b的边。而且，这个图不仅要是有向图，还要是一个有向无环图，也就是不能
     * 存在像a->b->c->a这样的循环依赖关系。因为图中一旦出现环，拓扑排序就无法工作了。实际上，拓扑排序本身就是基于有向无环图的一个算法。
     */
    @Test
    public void testTopology() {

    }

    private void topoSortByDFS(Graph graph) {
        // 构建逆邻接表 a->b 表示a依赖于b,b先于a
        int count = graph.getN();
        LinkedList<Integer>[] reverseAdjacency = new LinkedList[count];
        // 初始化，申请空间
        for (int i = 0; i < count; i++) {
            reverseAdjacency[i] = new LinkedList<>();
        }
        // 通过邻接表构建逆邻接表
        for (int i = 0; i < count; i++) {
            LinkedList<Integer> apexs = reverseAdjacency[i];

            for (int j = 0; j < apexs.size(); j++) {
                // 从reverseAdjacency[i] 中取出的链表apexs中的各个元素temp，都是依赖 i 的
                Integer temp = apexs.get(j);
                reverseAdjacency[temp].add(i);
            }
        }
        boolean[] visited = new boolean[count];

        for (int i = 0; i < count; i++) {
            if (!visited[i]) {
                visited[i] = true;
                dfs(i, reverseAdjacency, visited);
            }
        }
    }

    private void dfs(int apex, LinkedList<Integer>[] reverseAdjacency, boolean[] visited) {
        // 遍历当前，以apex为顶点的这条链表，所有的元素都是apex所依赖的
        for (int i = 0; i < reverseAdjacency[apex].size(); i++) {
            Integer temp = reverseAdjacency[apex].get(i);
            if(visited[temp]){
                continue;
            }
            visited[temp] = true;
            dfs(temp,reverseAdjacency,visited);
        }
        // 先把apex这个顶点可达的所有顶点都打印出来之后，再打印它自己
        System.out.print("->" + apex);
    }

    /**
     * kahn实现拓扑排序
     * 如果b依赖a,则a->b.如果一个顶点没有任何入度（即没有任何依赖）则这个顶点可以先执行
     */
    private void topoSortByKahn(Graph graph) {
        int count = graph.getN();

        // 记录每个顶点入度的数组
        int[] inDegree = new int[count];
        List<Integer>[] adjacency = graph.getAdjacency();
        for (int i = 0; i < count; i++) {
            // 一条邻接链表
            List<Integer> apexs = adjacency[i];
            // 在inDegree[5] 这条链表里的元素，都是依赖5的
            for (int k = 0; k < apexs.size(); k++) {
                inDegree[apexs.get(k)]++;
            }
        }

        LinkedList<Integer> queue = new LinkedList<>();
        // 入度为0的，可以直接用
        for (int i = 0; i < count; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
                // System.out.println(inDegree[i] + " -> ");
            }
        }
        while (!queue.isEmpty()) {
            // 获取入度为0，即没有依赖的顶点
            Integer apex = queue.remove();
            System.out.println(apex + " -> ");
            List<Integer> apexs = adjacency[apex];
            for (int i = 0; i < apexs.size(); i++) {
                // 这条链表上的顶点
                Integer k = apexs.get(i);

                /**
                 * 以apex为顶点的这条链表里的所有元素，入度减1
                 * 比如有条链表是 a->b->e->d ,b、d、e的入度分别是3，5，2，则a出去之后
                 * b、d、e的入度分别减一（依赖的顶点少了），变成2，4，1
                 */
                inDegree[k]--;
                if (inDegree[i] == 0) {
                    queue.add(i);
                }
            }
        }
    }
}
