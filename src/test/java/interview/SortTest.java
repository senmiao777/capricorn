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

    @Test
    public void testPartition() {
        int[] num = {8, 10, 2, 3, 6, 1, 5};
        //bubbleSort(num);
        int partition = partition(num, 0, 6);
        System.out.println("partition=" + partition + "num=" + Arrays.toString(num));
    }

    @Test
    public void testQuickSort() {
        int[] num = {8, 10, 2, 3, 8, 8, 6, 1, 5};
        quicksort(num);
        System.out.println(Arrays.toString(num));
    }


    @Test
    public void testFindKthLargestNumber() {
        //  int[] num = {3,2,1,5,6,4};
        int[] num = {8, 10, 2, 3, 8, 8, 6, 9, 5};
        int k = 6;
        int kthLargestNumber = getKthLargestNumber(num, k);
        System.out.println("第" + k + "大的数为：" + kthLargestNumber);
    }

    @Test
    public void testCountSort() {
        int[] scores = {2, 5, 3, 0, 2, 3, 0, 3};
        int[] numberCountArray = getNumberCountArray(scores);
        System.out.println(Arrays.toString(numberCountArray));
    }

    /**
     * @param nums 待排序数组 [2, 5, 3, 0, 2, 3, 0, 3]
     * @return 不同值元素值对应计数（出现个数）数组 得到 [2, 0, 2, 3, 0, 1]
     */
    private int[] getNumberCountArray(int[] nums) {

        // 获取数据范围
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > max) {
                max = nums[i];
            }
        }
        // 不考虑入参有误情况
        int[] assist = new int[max + 1];
        // 构造不同值元素值对应计数（出现个数）数组 A 比如
        for (int num : nums) {
            /*
              注意：这里写res[nums[i]] =  res[nums[i]] + 1 或者 ++ res[nums[i]] 都行，但是写res[nums[i]] ++ 不行，
              因为a ++ 是先赋值，在进行加一操作
             */
            assist[num] = assist[num] + 1;
        }

        // 构造数组A 元素求和数组 得到 [2, 2, 4, 7, 7, 8]
        for (int i = 1; i < assist.length; i++) {
            assist[i] = assist[i] + assist[i - 1];
        }

        // 构造结果数组，存放各个元素的排序
        int[] result = new int[nums.length];
        int count;
        int value;
        // 从尾部开始取，可以保证是稳定排序，从头取数据就不是稳定排序了
        for (int i = nums.length - 1; i >= 0; i--) {
            value = nums[i];
            // 小于等于当前值value的元素个数count
            count = assist[value];
            result[count - 1] = value;

            // 小于等于当前值value的元素个数count 减1
            assist[value] = count - 1;
        }
        return result;
    }

    /**
     * 在数组中找到第K大的元素
     * 思路一：先对数组进行排序，然后遍历一下，找到第K大的元素，时间复杂度O( n * log n )
     * 思路二：利用快排思想，不需要让数组全部有序就可以找到第K大的元素 ，时间复杂度O( n )
     *
     * @param num
     * @param k
     * @return
     */
    private int getKthLargestNumber(int[] num, int k) {
        int length = num.length;
        if (length < k) {
            throw new RuntimeException("数组长度小于" + k);
        }
        // length - k 是关键
        return getKthLargestNumberHandle(num, 0, length - 1, length - k);
    }

    private int getKthLargestNumberHandle(int[] num, int start, int end, int k) {
        int pivot = partition(num, 0, end);
        if (pivot == k) {
            return num[pivot];
        } else if (pivot > k) {
            return getKthLargestNumberHandle(num, 0, pivot - 1, k);
        } else {
            return getKthLargestNumberHandle(num, pivot + 1, end, k);
        }
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

    /**
     * 归并排序
     *
     * @param num
     */
    private int[] mergeSort(int[] num, int begin, int end) {
        return null;
    }

    /**
     * 快速排序
     * 思想：从数组中找一个数（通常直接用最后一个数就行），比这个数字大的调整到数组左边，比这个数字小的调整到数组右边
     * 重复这个过程
     *
     * @param num 待排序数组
     */
    private void quicksort(int[] num) {
        quicksortHandle(num, 0, num.length - 1);
    }

    // 选取元素作为分割点的时候，可以从头，中、尾选三个，然后用中间值作为分割元素，这样左右两遍元素个数差别小的概率大一些
    private void quicksortHandle(int[] num, int start, int end) {
        if (start >= end) {
            return;
        }
        /**
         *
         枢轴 [ˈpɪvət]
         n.	枢轴; 中心; 支点; 核心; 中心点; 最重要的人(或事物);
         v.	(使)在枢轴上旋转(或转动);

         */
        int pivot = partition(num, start, end);
        quicksortHandle(num, start, pivot - 1);
        quicksortHandle(num, pivot + 1, end);
    }

    /**
     * 以数组中num[end]元素值A作为中间值进行元素排列，比A小的元素值放在左边，比A大的元素值放在右边
     *
     * @param num   数组
     * @param start 起始下标
     * @param end   截止下标
     * @return 排序后A元素下标值
     */
    private int partition(int[] num, int start, int end) {
        int pivotValue = num[end];
        int left = start;
        /**
         * j = start
         * j < end
         */
        for (int j = start; j < end; j++) {
            /**
             * 只要小于选出来的轴值，左下标就要++
             */
            if (num[j] <= pivotValue) {
                // 左下标和当前不相等，交换
                if (left != j) {
                    swap(num, left, j);
                }
                left++;
            }
        }
        // 最后有一步交换，返回左下标
        swap(num, left, end);
        return left;
    }

    /**
     * 交换数组中下标i和下标j元素值
     *
     * @param num 数据
     * @param i   下标i
     * @param j   下标j
     */
    private void swap(int[] num, int i, int j) {
        int temp = num[i];
        num[i] = num[j];
        num[j] = temp;
    }
}
