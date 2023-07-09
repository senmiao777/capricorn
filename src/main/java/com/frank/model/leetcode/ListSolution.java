package com.frank.model.leetcode;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author frank
 * @version 1.0
 * @date 2021/5/22 0022 下午 9:51
 * <p>
 * <p>
 * 我们有一个由平面上的点组成的列表 points。需要从中找出 K 个距离原点 (0, 0) 最近的点。
 * <p>
 * （这里，平面上两点之间的距离是欧几里德距离。）
 * <p>
 * 你可以按任何顺序返回答案。除了点坐标的顺序之外，答案确保是唯一的。
 * <p>
 * 来源：力扣（LeetCode）
 * 输入：points = [[1,3],[-2,2]], K = 1
 * 输出：[[-2,2]]
 * 解释：
 * (1, 3) 和原点之间的距离为 sqrt(10)，
 * (-2, 2) 和原点之间的距离为 sqrt(8)，
 * 由于 sqrt(8) < sqrt(10)，(-2, 2) 离原点更近。
 * 我们只需要距离原点最近的 K = 1 个点，所以答案就是 [[-2,2]]。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/k-closest-points-to-origin
 */
public class ListSolution {

    public static void main(String[] args) {
        int[][] points = new int[2][];
        int[] first = new int[]{1, 3};
        int[] second = new int[]{-2, 2};
        points[0] = first;
        points[1] = second;
        int k = 1;
        int[][] ints = kClosest(points, k);
        for (int i = 0; i < ints.length; i++) {
            System.out.println(ints[i][0] + "," + ints[i][1]);
        }

    }


    /**
     * 构建一个由K个元素组成的最大堆，实现方式PriorityQueue
     * 当元素个数超过K的时候，弹出堆顶元素，即保留在堆里的，就是最小的K个元素（大的都被弹出去了）
     *
     * @param points
     * @param k
     * @return
     */
    public static int[][] kClosest(int[][] points, int k) {

        PriorityQueue<Point> heap2 = new PriorityQueue<>(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return (o2.x * o2.x + o2.y * o2.y) - (o1.x * o1.x + o1.y * o1.y);
            }
        });

        /**
         * lambda形式，简洁
         */
        PriorityQueue<Point> heap = new PriorityQueue<>((o1, o2) -> (o2.x * o2.x + o2.y * o2.y) - (o1.x * o1.x + o1.y * o1.y));


        for (int i = 0; i < points.length; i++) {
            heap.offer(new Point(points[i][0], points[i][1]));
            if (heap.size() > k) {
                heap.poll();
            }
        }

        int[][] res = new int[k][2];

        for (int i = 0; i < k; i++) {
            Point poll = heap.poll();
            res[i][0] = poll.getX();
            res[i][1] = poll.getY();
        }
        return res;

    }


    public static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}
