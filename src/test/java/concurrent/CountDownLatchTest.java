package concurrent;

import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.IntStream;

/**
 * @author frank
 * @version 1.0
 * @date 2021/6/14 0014 下午 12:05
 * 模拟高并发
 */
public class CountDownLatchTest {
    final int NUMBER = 30;

    /**
     * 确保在countdownlatch没有到0之前，实际业务不会执行
     */
    CountDownLatch count = new CountDownLatch(NUMBER);
    /**
     * 就绪判断依据
     * 新建线程数N + main线程数1
     * <p>
     * 人齐了才开始 XX
     */
    CyclicBarrier ready = new CyclicBarrier(NUMBER + 1);
    int stock = 200;

    class StockTask implements Runnable {

        @SneakyThrows
        @Override
        public void run() {
            try {
                count.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (stock >= 3) {
                int temp = stock;
                Thread.sleep(RandomUtils.nextInt(1, 30));
                stock -= 3;
                System.out.println("当前库存为" + temp + "扣减之后为" + stock);
            } else {
                System.out.println("库存不足，为" + stock);
            }
            // 表示已就绪，完成
            ready.await();
        }
    }

    @Test
    public void subStockTest() throws InterruptedException, BrokenBarrierException {
        final long begin = System.currentTimeMillis();
        /**
         * 启动30个线程
         */
        for (int i = 0; i < NUMBER; i++) {
            new Thread(new StockTask()).start();
            count.countDown();
        }
        ready.await();
        System.out.println("最终库存为" + stock + "耗时=" + (System.currentTimeMillis() - begin));

        // lambda 写法
        IntStream.range(0, 30).forEach(s -> {
            System.out.println("第" + (s + 1) + "次执行");
            new Thread(new StockTask()).start();
            count.countDown();
        });

    }
}
