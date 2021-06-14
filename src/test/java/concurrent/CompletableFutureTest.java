package concurrent;

import com.frank.service.IStockService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author frank
 * @version 1.0
 * @date 2021/6/14 0014 下午 11:08
 */
public class CompletableFutureTest {

    @Autowired
    private IStockService stockService;


    @Test
    public void completableFutureTest() {
        CompletableFuture<Map<String, Object>> future = new CompletableFuture<>();

    }

}
