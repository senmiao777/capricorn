package algorithm;

import org.junit.Test;

import java.util.Arrays;

/**
 * @description: 动态规划/状态缓存
 * 为什么叫动态规划？
 * 是因为下一步的结果，是以上一步的结果为依据得到的，一环套一环
 * <p>
 * 动态规划，说的直白点叫状态缓存或者结果缓存，缓存的就是上一步的状态或者说结果
 * <p>
 * 思路：把问题分为多个阶段，得到每个阶段的可能结果（去重后），然后用当前阶段的结果，推导出下一阶段的结果
 **/
public class DynamicProgramming {

    /**
     * 找到单源（一个顶点到一个顶点）最短路径
     * Dijkstra算法
     */
    @Test
    public void testOneDirectionShortedDistance() {

    }

    /**
     * 有一个数字序列包含n个不同的数字，求出这个序列中的最长递增子序列长度
     * 比如2, 9, 3, 6, 5, 1, 7这样一组数字序列，它的最长递增子序列就是2, 3, 5, 7，所以最长递增子序列的长度是4。
     */
    @Test
    public void testLongestRise() {

        int[] numbers = {2, 9, 3, 6, 5, 1, 7};
        System.out.println("最长递增子序列= " + getLongestRise(numbers));
    }

    private int getLongestRise(int[] numbers) {
        // 数组长度
        final int length = numbers.length;
        int[] status = new int[length];

        status[0] = 1;
        int commonMax = 0;
        for (int i = 1; i < length; i++) {
            int max = 0;
            for (int j = 0; j < i; j++) {
                if (numbers[j] < numbers[i]) {
                    if (status[j] > max) {
                        max = status[j];
                    }
                }
            }
            status[i] = max + 1;

            if (status[i] > commonMax) {
                commonMax = status[i];
            }

        }

        int maxComp = commonMax;
        System.out.println("递增:" + Arrays.toString(status));
        for (int i = length - 1; i >= 0; i--) {
            if (status[i] == maxComp) {
                System.out.print("-->" + numbers[i]);
                maxComp = maxComp - 1;
            }
        }
        return commonMax;
    }

    @Test
    public void testDynamicProgrammingPackage() {
        int[] weight = {2, 2, 4, 6, 3};
        int n = 5;
        int load = 9;
        // knapsack(weight, n, load);
        knapsack2(weight, n, load);
    }

    /**
     * 背包问题升级
     * 不同物品有不同的价值，在不超过背包负重的前提下，让装入背包的物品价值最大
     *
     * @param weight 物品重量数组
     * @param value  物品价值数组
     * @param n      物品个数
     * @param load   背包可承载重量
     * @return 最大可装物品重量
     */
    public int knapsack3(int[] weight, int[] value, int n, int load) {
        // 横坐标代表物品个数，纵坐标代表物品重量，元素值代表物品价值
        int[][] result = new int[n][load + 1];
        result[0][0] = 0;
        // 放第一件物品
        if (weight[0] <= load) {
            result[0][weight[0]] = value[0];
        }
        for (int i = 1; i < n; i++) {
            result[i][0] = 0;
            // 当前物品放入背包
            if (weight[i] <= load) {
                result[i][weight[i]] = value[i];
            }
            for (int k = 0; k <= load; k++) {
                // 找到上个价值大于零的
                if (result[i - 1][k] > 0) {
                    // 不选择当前物品，重量、价值和之前一样
                    result[i][k] = result[i - 1][k];
                    // 选择当前物品
                    if (k + weight[i] < load) {
                        // 加上当前物品之后的价值
                        int cur = result[i - 1][k] + value[i];
                        // 原有值,看看之前有没有组合成这个重量的物品
                        int origin = result[i][k + weight[i]];
                        if (cur > origin) {
                            result[i][k + weight[i]] = cur;
                        }
                    }
                }
            }
        }
        int maxValue = 0;
        for (int k = 0; k <= load; k++) {
            /**
             * 找到价值大于零的
             * 这里不能从后往前找，找到第一个大于零的就直接返回
             * 因为从后往前找只表示重量大，并不一定是价值大
             */
            if (result[n - 1][k] > maxValue) {
                maxValue = result[n - 1][k];
            }
        }
        /**
         * 选中了哪些物品
         * 找到result[i-1][currentLoad - items[i]] 不为零的
         *
         * 因为想达到result[i][currentLoad]只有两种情况
         * 第一：不选择当前物品，从result[i-1][currentLoad]到result[i][currentLoad]
         * 第二：选择当前物品，从result[i-1][currentLoad-items[i]]到result[i][currentLoad]
         */
        return maxValue;
    }

    /**
     * @param weight 物品重量数组
     * @param n      物品个数
     * @param load   背包可承载重量
     * @return 最大可装物品重量
     */
    public int knapsack(int[] weight, int n, int load) {
        if (n == 1) {
            return weight[1] < load ? weight[1] : 0;
        }
        // load + 1列，是因为要多存一列 零
        boolean[][] result = new boolean[n][load + 1];
        result[0][0] = true;
        result[0][weight[0]] = true;
        // 从第二个物品（行） 开始遍历
        for (int row = 1; row < n; row++) {
            // 完全不放，重量是零一定是true
            result[row][0] = true;
            // 当前物品的重量小于背包负载的情况下，列（物品重量）为true
            if (weight[row] <= load) {
                result[row][weight[row]] = true;
            }
            // 遍历列
            for (int column = 1; column < (load + 1); column++) {
                // 找到上一层有负重的列
                if (result[row - 1][column]) {
                    //这一同样有该重量的负重，因为不把当前物品放入背包就行了
                    result[row][column] = true;
                    // 判断当前物品是否能装进去，没有超出负载
                    // 注意，这里是加 weight[row] 不是加 weight[column]
                    if (column + weight[row] <= load) {
                        result[row][column + weight[row]] = true;
                    }
                }
            }
        }
        printLoad(result, load);

        // 所有的可能负重情况都在保存在最后一行
        for (int column = load; column >= 0; column--) {
            if (result[n - 1][column]) {
                return column;
            }
        }
        return 0;
    }


    /**
     * 一维数组实现背包问题
     *
     * @param items 物品重量
     * @param n     物品个数
     * @param load  背包可负载重量
     * @return
     */
    public int knapsack2(int[] items, int n, int load) {
        // 0到load，共load + 1 个值
        boolean[] result = new boolean[load + 1];
        result[0] = true;
        result[items[0]] = true;
        for (int i = 1; i < n; i++) {
            // k从大到小，否则有重复计算问题
            for (int k = load - items[i]; k >= 0; k--) {
                // 有总重量为当前重量的
                if (result[k]) {
                    // k的值是从剩余负重（由背包负重减去当前物品重量得到）开始的，所以不用判断加了当前物品后是否会超重
                    result[k + items[i]] = true;
                }
            }
        }

        for (int i = 0; i <= load; i++) {
            if (result[i]) {
                System.out.print(" 1 ");
            } else {
                System.out.print(" 0 ");
            }
        }
        for (int i = load; i > 0; i--) {
            if (result[i - 1]) {
                return i;
            }
        }
        return 0;
    }

    private void printLoad(boolean[][] result, int load) {
        for (boolean[] booleans : result) {
            for (int column = 0; column < (load + 1); column++) {
                if (booleans[column]) {
                    System.out.print(" 1 ");
                } else {
                    System.out.print(" 0 ");
                }
            }
            System.out.println();
        }
    }
}
