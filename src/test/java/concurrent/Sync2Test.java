package concurrent;

import org.apache.commons.lang3.RandomUtils;

/**
 * @author frank
 * @version 1.0
 * @date 2021/7/10 0010 上午 10:40
 */
public class Sync2Test {
    public static int j = 0;

    /**
     * 自增方法
     */
    public synchronized void incr() {
        //临界区代码--start
        for (int i = 0; i < 10000; i++) {
            if (i % 1000 == 0 && Thread.currentThread().getName().equals("t1")) {
                try {
                    System.out.println("sleep j=" + j);
                    Thread.sleep(RandomUtils.nextInt(1, 10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            j++;
        }
        //临界区代码--end
    }

    public int getJ() {
        return j;
    }
}
