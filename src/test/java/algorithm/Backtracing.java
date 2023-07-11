package algorithm;

import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author: somebody
 * @time: 2019-03-21 17:03
 */
@Slf4j
public class Backtracing {

    /**
     * 八皇后二维数组
     * 下标代表行数，数组元素值代表列数
     */
    public int[] queens = new int[8];
    public int[][] mem = new int[5][200];
    // 物品重量
    int[] items = {2, 2, 4, 6, 3};
    // 物品价值
    int[] value = {3, 4, 8, 9, 6};
    // 物品个数
    int n = 5;
    // 背包负重
    int load = 9;

    // 棋盘行数
    int chessboardRow = 4;
    // 棋盘列数
    int chessboardColumn = 4;
    int[][] chessboard = new int[chessboardRow][chessboardColumn];

    // 存放到各个坐标的路径值
    int[] coordinate = new int[(chessboardRow + 1) * 10];
    Map<String, Integer> cache = new HashMap<>(32);
    int minPathValue = 100000;
    int minDist = 1000;


    private static final Map<String, String> PHONE_MAP = new HashMap<String, String>() {
        {
            put("2", "abc");
            put("3", "def");
            put("4", "ghi");
            put("5", "jkl");
            put("6", "mno");
            put("7", "pqrs");
            put("8", "tuv");
            put("9", "wxyz");
        }
    };


    /**
     * 最短路径回溯
     */
    @Test
    public void minPath() {
        chessboard[0][0] = 1;
        chessboard[0][1] = 3;
        chessboard[0][2] = 5;
        chessboard[0][3] = 9;

        chessboard[1][0] = 2;
        chessboard[1][1] = 1;
        chessboard[1][2] = 3;
        chessboard[1][3] = 4;

        chessboard[2][0] = 5;
        chessboard[2][1] = 2;
        chessboard[2][2] = 6;
        chessboard[2][3] = 7;

        chessboard[3][0] = 6;
        chessboard[3][1] = 8;
        chessboard[3][2] = 4;
        chessboard[3][3] = 3;

        getMinPath(0, 0, 0);
        System.out.println("minPathValue=" + minPathValue);
        getMinPath2();
       // minDistBT(0,0,0,chessboard,4);
    }

    public void minDistBT(int i, int j, int dist, int[][] w, int n) {
        // 到达了n-1, n-1这个位置了，这里看着有点奇怪哈，你自己举个例子看下
        if (i == n && j == n) {
            if (dist < minDist) minDist = dist;
            System.out.println("minDist=" + minDist);
            return;
        }
        if (i < n) { // 往下走，更新i=i+1, j=j
            minDistBT(i + 1, j, dist+w[i][j], w, n);
        }
        if (j < n) { // 往右走，更新i=i, j=j+1
            if(i == n-1){
                return;
            }
            minDistBT(i, j+1, dist+w[i][j], w, n);
        }
    }


    private void getMinPath2() {
        int[][] res = new int[chessboardRow][chessboardColumn];
        // 初始化第一行
        int sum = 0;
        for (int i = 0; i < chessboardRow; i++) {
            sum = sum + chessboard[0][i];
            res[0][i] = sum;
        }

        sum = 0;
        // 初始化第一列
        for (int i = 0; i < chessboardColumn; i++) {
            sum = sum + chessboard[i][0];
            res[i][0] = sum;
        }

        for (int i = 1; i < chessboardRow; i++) {
            for (int j = 1; j < chessboardColumn; j++) {
                res[i][j] = chessboard[i][j] + Math.min(res[i - 1][j], res[i][j - 1]);
            }
        }
        System.out.println("getMinPath2 最短路径=" + res[chessboardRow - 1][chessboardColumn - 1]);
    }

    /**
     * 回溯算法 + 结果缓存
     * 缓存重复值，怎么缓存？
     * 重复的是什么？ 当前的坐标（row,column）,虽然到这个坐标的路径长度不同，但是这个点是固定的，只要保留到这个坐标的最短路径即可
     *
     * @param curPathValue 当前已走过的路径长度
     * @param row          当前行
     * @param column       当前列
     */
    private void getMinPath(int curPathValue, int row, int column) {
        // System.out.println("row =" + row + ", column" + column);
        //   System.out.println(Arrays.toString(coordinate));
        if (row == chessboardRow && column == chessboardColumn) {
            System.out.println("走到末尾路径长度=" + curPathValue+chessboard[row][column]);
            minPathValue = Math.min(minPathValue, curPathValue);
        }

        // int old = coordinate[row * 10 + column];
        String key = getKey(row, column);
        Integer old = cache.get(key);
        // 之前已经到达过这个坐标，且比当前路径短
   /*     if (old > 0 && old < curPathValue) {
            System.out.println("之前已找到更短路径 old= " + old + " ,cur=" + curPathValue);
            return;
        } else {
            System.out.println("找到更短路径 old= " + old + " ,cur=" + curPathValue);

        }*/

        if (old != null && old < curPathValue) {
            System.out.println("之前已找到更短路径key=" + key + " ,old= " + old + " ,cur=" + curPathValue);
            return;
        }
        cache.put(key, curPathValue);
        // 往下走
        if (row < chessboardRow) {
            // 行 * 10 + 列，确保不会有重复复值
            // coordinate[(row + 1) * 10 + column] = curPathValue + chessboard[row + 1][column];


            // 这里是加chessboard[row][column] ，不是加chessboard[ row +1 ][column]
            getMinPath(curPathValue + chessboard[row][column], row + 1, column);
        }
        // 往右走
        if (column < chessboardColumn && row < chessboardRow) {
            // coordinate[row * 10 + column + 1] = curPathValue + chessboard[row][column + 1];
            getMinPath(curPathValue + chessboard[row][column], row, column + 1);
        }
    }

    private String getKey(int row, int column) {
        return String.valueOf(row).concat("#").concat(String.valueOf(column));
    }

    /**
     * 0-1背包问题
     * 将不同重量，不同价值的物品，在不超过背包容量的前提下，尽可能多的放入背包
     */
    @Test
    public void test01Knapsack2() {
        selectItems2(0, 0, 0);
    }


    /**
     * 挑选价值不同物品放入背包
     *
     * @param curLoadBearing 背包当前承重
     * @param curValue       背包当前物品价值
     * @param i              当前选择过的物品个数
     */
    private void selectItems2(int curLoadBearing, int curValue, int i) {
        // 负重满了，或者说已经选择到最后一个物品了退出
        if (curLoadBearing == load || n == i) {
            System.out.println("已挑选完成，挑选物品 " + i + " 件，curLoadBearing=" + curLoadBearing + " ,curValue= " + curValue);
            return;
        }
        // 当前物品不放入背包
        selectItems2(curLoadBearing, curValue, i + 1);
        // 当前物品放入背包
        if (curLoadBearing + items[i] <= load) {
            selectItems2(curLoadBearing + items[i], curValue + value[i], i + 1);
        }
    }

    /**
     * 0-1背包问题
     */
    @Test
    public void test01Knapsack() {
        // 背包承重量
        int knapsackLoadBearing = 100;
        // 当前承重
        int curLoadBearing;

        // 物品总件数
        int n = 5;
        // 每件物品的重量
        int[] weights = {45, 12, 6, 89, 23};

        selectItems(knapsackLoadBearing, 0, n, weights, 0);
    }

    /**
     * 挑选物品放入背包
     *
     * @param knapsackLoadBearing 背包极限承重量
     * @param curLoadBearing      背包当前承重
     * @param n                   物品件数
     * @param weights             物品重量
     * @param i                   当前选择过的物品个数
     */
    private void selectItems(int knapsackLoadBearing, int curLoadBearing, int n, int[] weights, int i) {
        // 背包满了或者物品已经放完了
        if (knapsackLoadBearing == curLoadBearing || n == i) {
            System.out.println("当前物品重量:" + curLoadBearing);
            return;
        }
        if (mem[i][curLoadBearing] == 1) {
            return;
        }
        mem[i][curLoadBearing] = 1;
        // 不选择当前物品，直接选择下一件物品，所以当前背包负载没有变化
        selectItems(knapsackLoadBearing, curLoadBearing, n, weights, i + 1);
        if (curLoadBearing + weights[i] <= knapsackLoadBearing) {
            // 选择当前物品，当前背包负载加上当前物品重量
            selectItems(knapsackLoadBearing, (curLoadBearing + weights[i]), n, weights, i + 1);
        }
    }


    @Test
    public void testNQueens() {
        put8queens(0);
    }


    @Test
    public void testEightQueens() {
        put8queens(0);
    }

    /**
     * 设置该行棋子位置
     *
     * @param row 行号
     */
    private void put8queens(int row) {
        if (row == 8) {
            showQueens();
            return;
        }
        for (int column = 0; column < 8; column++) {
            if (ok(row, column)) {
                queens[row] = column;
                put8queens(row + 1);
            }
        }
    }

    private void showQueens() {
        for (int i = 0; i < 8; i++) {
            System.out.println("第 " + (i + 1) + " 行，第 " + (queens[i] + 1) + " 列");
        }
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (queens[row] == column) {
                    System.out.print(" Q ");
                } else {
                    System.out.print(" - ");
                }
            }
            System.out.println();
        }
        System.out.println("===============================");
    }

    /**
     * 判断该位置是否可以方棋子
     *
     * @param row    棋子所在行
     * @param column 旗子所在列
     * @return 该坐标是否冲突 true:冲突；false:未冲突
     */
    private boolean ok(int row, int column) {
        int leftUp = column - 1;
        int rightUp = column + 1;

        int cur;
        // 逐行往上判断该位置是否有冲突
        for (int i = row - 1; i >= 0; --i) {
            cur = queens[i];
            // 这一列已经有棋子了
            if (cur == column) {
                return false;
            }
            // 注意，左上的判断是大于等于零，不是大于零
            if (leftUp >= 0) {
                if (cur == leftUp) {
                    return false;
                }
            }
            if (rightUp < 8) {
                if (cur == rightUp) {
                    return false;
                }
            }
            leftUp--;
            rightUp++;
        }
        return true;
    }

    @Test
    public void backtracing() {
        String str = "234";

        List<String> result = new ArrayList<>();
        bt("", str, result);
        log.info("result={}", result);
    }


    /**
     * @param combination 结果集中的一个元素？
     * @param nextNumbers 输入的数字
     * @param result
     */
    private void bt(String combination, String nextNumbers, List<String> result) {
        if (nextNumbers.length() == 0) {
            result.add(combination);
        } else {
            String currentNumber = nextNumbers.substring(0, 1);
            String currentStr = PHONE_MAP.get(currentNumber);
            for (int i = 0; i < currentStr.length(); i++) {
                String substring = PHONE_MAP.get(currentNumber).substring(i, i + 1);
                bt(combination + substring, nextNumbers.substring(1), result);
            }
        }
    }
}
