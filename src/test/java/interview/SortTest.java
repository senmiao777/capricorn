package interview;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author: guojingfeng
 * @description:
 * @date: 2022/7/5
 **/
public class SortTest {

    @Test
    public void testBubbleSort() {
        int[] num = {7, 4, 5, 6, 2, 3, 1};
        //bubbleSort(num);
        insertSort(num);
        System.out.println(Arrays.toString(num));
    }

    /**
     * 冒泡排序
     *
     * @param numbers
     */
    private void bubbleSort(int[] numbers) {
        int length = numbers.length;
        if (length > 1) {
            int temp;
            boolean change;
            for (int i = 0; i < length; i++) {
                change = false;
                for (int j = 0; j < length - i - 1; j++) {
                    if (numbers[j] > numbers[j + 1]) {
                        change = true;
                        temp = numbers[j];
                        numbers[j] = numbers[j + 1];
                        numbers[j + 1] = temp;
                    }
                }
                // 如果本轮没有发生数据交换，直接退出
                if (!change) {
                    break;
                }
            }
        }
    }

    private void insertSort(int[] numbers) {
        int length = numbers.length;
        if (length > 1) {
            int cur;
            for (int i = 1; i < length; i++) {
                cur = numbers[i];
                int j = i - 1;
                for (; j >= 0; j--) {
                    if (numbers[j] > cur) {
                        numbers[j + 1] = numbers[j];
                    } else {
                        break;
                    }
                }
                numbers[j + 1] = cur;
            }
        }
    }
}
