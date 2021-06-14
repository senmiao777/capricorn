package concurrent;

import com.frank.entity.mysql.Stock;
import com.frank.service.IStockService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * @author frank
 * @version 1.0
 * @date 2021/6/14 0014 下午 11:08
 */
public class CompletableFutureTest {

    @Autowired
    private IStockService stockService;

    BlockingQueue<CompletableFutureRequest> queue = new LinkedBlockingQueue<>();

    @PostConstruct
    public void init() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            List<CompletableFutureRequest> temporary = new ArrayList<>();
            List<String> param = new ArrayList<>();

            int size;
            if ((size = queue.size()) <= 0) {
                return;
            }

            IntStream.range(0, size).forEach((m) -> {
                CompletableFutureRequest request = queue.poll();
                temporary.add(request);
                param.add(request.getStockCode());
            });

            stockService.findByStockCodes(param);
        }, 0, 5, TimeUnit.SECONDS);
    }

    @Data
    @NoArgsConstructor
    class CompletableFutureRequest {
        private String stockCode;
        /**
         * 如果业务参数不唯一，可以自己生成一个唯一值，用于查询条件和返回结果的绑定
         */
        private String uniqueId;
        private CompletableFuture<Stock> future;
    }


    @Test
    public void completableFutureTest() {
        CompletableFuture<Map<String, Object>> future = new CompletableFuture<>();

    }

}
