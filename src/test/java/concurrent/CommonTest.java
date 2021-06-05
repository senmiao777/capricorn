package concurrent;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author frank
 * @version 1.0
 * @date 2021/5/22 0022 上午 10:48
 */
public class CommonTest {

    @Test
    public void testCheckSubarraySum(){
        int[] nums = {23,2,4,6,7};
        System.out.println(checkSubarraySum(nums,6));
    }

    public boolean checkSubarraySum(int[] nums, int k) {
        int length = nums.length;
        if (length < 2) {
            return false;
        }
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int remainder = 0;
        for (int i = 0; i < length; i++) {
            remainder = (remainder + nums[i]) % k;
            if (map.containsKey(remainder)) {
                int prevIndex = map.get(remainder);
                if (i - prevIndex >= 2) {
                    return true;
                }
            } else {
                map.put(remainder, i);
            }
        }
        return false;
    }






    /**
     * newScheduledThreadPool
     * 指定延迟多久后，定时执行
     *
     * @throws InterruptedException
     */
    @Test
    public void testExecuotrService() throws InterruptedException {
        System.out.println("当前时间:" + LocalDateTime.now().getSecond());
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            System.out.println("间隔执行:" + LocalDateTime.now().getSecond());
        }, 2, 1, TimeUnit.SECONDS);
        Thread.sleep(5000L);


        System.out.println("当前时间2:" + LocalDateTime.now().getSecond());


        // 构建一个二叉堆, 内部存放int数据
        Integer[] data = {3, 5, 9, 1, 10, 8, 2, 12, 7, 4};

        PriorityQueue<Integer> queue = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.valueOf(o2.toString()) - Integer.valueOf(o1.toString());
            }
        });


        /**
         * 正序最小堆
         * 倒序最大堆
         *
         */
        PriorityQueue<Integer> queue2 = new PriorityQueue<>((o1, o2) -> o2 - o1);
        for (int i = 0; i < data.length; i++) {
            queue2.offer(data[i]);
        }


        System.out.println("============== 最小堆示例 ==============");
        for (int i = 0; i < data.length; i++) {
            System.out.println(queue2.poll());
        }


    }

}
