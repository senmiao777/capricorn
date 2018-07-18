package concurrent;

import com.frank.concurrent.CountFileTask;
import com.frank.concurrent.CountTask;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.Future;

/**
 * @author frank
 * @version 1.0
 * @date 2018/7/6
 */

@Slf4j
public class ForkJoinTest {


    @Test
    public void t() {

        final CountTask countTask = new CountTask(1, 100);
        ForkJoinPool forkJoinPool = new ForkJoinPool(2);
        final ForkJoinTask<Integer> submit = forkJoinPool.submit(countTask);
        try {
            final Integer sum = submit.get();
            log.info("sum ={}", sum);

            if (countTask.isCompletedAbnormally()) {
                log.info("e={}", countTask.getException());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testFileCount(){
        ForkJoinPool    forkJoinPool = new ForkJoinPool();
        CountFileTask task         = new CountFileTask(new File("G:\\Adobe CS6"));
        long            start        = System.currentTimeMillis();
        Future<Integer> result       = forkJoinPool.submit(task);
        try {
            System.out.println(result.get());

            long duration = System.currentTimeMillis() - start;
            log.info("time cost:{} ms",duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}
