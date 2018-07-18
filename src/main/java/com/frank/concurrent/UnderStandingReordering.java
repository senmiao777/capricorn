package com.frank.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: sb
 * @time: 2018-07-09 16:41
 */
public class UnderStandingReordering {
    static int[] data = {9, 9, 9, 9, 9};
    static boolean is_ready = false;

    static void init_data() {
        for (int i = 0; i < 5; ++i) {
            data[i] = i;
        }
        is_ready = true;
    }

    static int sum_data() {
        if (!is_ready) {
            return -1;
        }
        int sum = 0;
        for (int i = 0; i < 5; ++i) {
            sum += data[i];
        }
        return sum;
    }

    public static void main(String[] args) throws Exception{
        ExecutorService executor1 = Executors.newSingleThreadExecutor();
        ExecutorService executor2 = Executors.newSingleThreadExecutor();

        for(int t =0;t<1000;t++){
            executor1.submit(() -> {
                try {
                    int sum = -1;
                    while (sum < 0) {
                      //  TimeUnit.MILLISECONDS.sleep(1);
                        sum = sum_data();
                    }
                    if(sum !=10){
                        System.out.println(sum);
                    }
                } catch (Exception ignored) {}
            });

           // TimeUnit.SECONDS.sleep(2);
            executor2.submit(UnderStandingReordering::init_data);
        }


    }
}
