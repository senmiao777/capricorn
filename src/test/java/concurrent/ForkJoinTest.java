package concurrent;

import com.frank.concurrent.CountTask;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * @author frank
 * @version 1.0
 * @date 2018/7/6
 */

@Slf4j
public class ForkJoinTest {


    @Test
    public void t() {

        final CountTask countTask = new CountTask(1, 1000);
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

}
