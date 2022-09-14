package algorithm;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
