package concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author: sb
 * @time: 2018-07-09 14:24
 */
@Slf4j
public class MemoryMode {


    @Test
    public void testreorder() {

        for (int i = 1; i < 100; i++) {
            int[] data = {9, 9, 9, 9, 9};
            boolean isReady = false;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    init_data(data, isReady);
                }
            }).start();


            new Thread(new Runnable() {
                @Override
                public void run() {
                    sum_data(data, isReady);
                }
            }).start();
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("finish");

    }

    void init_data(int[] data, boolean isReady) {
        for (int i = 0; i < 5; ++i) {
            data[i] = i;
        }
        isReady = true;
        log.info("isReady");
    }

    void sum_data(int[] data, boolean isReady) {
        if (!isReady) {
            return;
        }
        int sum = 0;
        for (int i = 0; i < 5; ++i) {
            sum += data[i];
        }
        if (sum != 15) {
            log.info("get !!! sum={},data={}", sum, data);
        }else {
            log.info("sum={}", sum);
        }


    }
}
