package concurrent;

import com.frank.entity.mysql.Stock;
import com.frank.entity.mysql.User;
import com.frank.model.Order;
import com.frank.service.IOrderService;
import com.frank.service.IStockService;
import com.frank.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * @author frank
 * @version 1.0
 * @date 2021/6/14 0014 下午 11:08
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootApplication
@ComponentScan(basePackages = "com.frank")
@SpringBootTest(classes = CompletableFutureTest.class)
@Slf4j
public class CompletableFutureTest {

    @Autowired
    private IStockService stockService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    @Qualifier("testTaskPoolExecutor")
    private Executor taskPoolExecutor;

    @Test
    public void completableFutureTest() {
        long begin = System.currentTimeMillis();
        User user = userService.findByUserIdFake(123L);
        Order order = orderService.getByOrderIdFake("test123123");
        Stock stock = stockService.findStockByCodeFake("000002");
        Map<String, Object> res = new HashMap<>(8);
        res.put("user", user);
        res.put("order", order);
        res.put("stock", stock);
        log.info("res={},\n耗时 {} 毫秒", res, (System.currentTimeMillis() - begin));
    }

    @Test
    public void completableFutureTest2() {
        long begin = System.currentTimeMillis();
        CompletableFuture<User> userCompletableFuture = CompletableFuture.supplyAsync(() -> userService.findByUserIdFake(123L), taskPoolExecutor);
        CompletableFuture<Order> orderCompletableFuture = CompletableFuture.supplyAsync(() -> orderService.getByOrderIdFake("test123123"), taskPoolExecutor);
        CompletableFuture<Stock> stockCompletableFuture = CompletableFuture.supplyAsync(() -> stockService.findStockByCodeFake("000002"), taskPoolExecutor);

        Map<String, Object> res = new HashMap<>(8);
        try {
            res.put("user", userCompletableFuture.get());
            res.put("order", orderCompletableFuture.get());
            res.put("stock", stockCompletableFuture.get());
            log.info("res={},\n耗时 {} 毫秒", res, (System.currentTimeMillis() - begin));
        } catch (Exception e) {
            if (userCompletableFuture != null) {
                userCompletableFuture.cancel(true);
            }
            if (orderCompletableFuture != null) {
                orderCompletableFuture.cancel(true);
            }
            if (stockCompletableFuture != null) {
                stockCompletableFuture.cancel(true);
            }
            e.printStackTrace();
        }
    }


    @Test
    public void completableFutureTest3() {
        long begin = System.currentTimeMillis();
        CompletableFuture<User> userCompletableFuture = CompletableFuture.supplyAsync(() -> userService.findByUserIdFake(123L), taskPoolExecutor);
        CompletableFuture<Order> orderCompletableFuture = CompletableFuture.supplyAsync(() -> orderService.getByOrderIdFake("test123123"), taskPoolExecutor);
        CompletableFuture.allOf(userCompletableFuture, orderCompletableFuture).thenApply((Void) -> {
            Map<String, Object> res = new HashMap<>(8);
            try {
                res.put("user", userCompletableFuture.get());
                res.put("order", orderCompletableFuture.get());
                log.info("res={},\n耗时 {} 毫秒", res, (System.currentTimeMillis() - begin));
                return res;
            } catch (Exception e) {
                log.info("发生异常,e={}", e.getMessage());
                if (userCompletableFuture != null) {
                    userCompletableFuture.cancel(true);
                }
                if (orderCompletableFuture != null) {
                    orderCompletableFuture.cancel(true);
                }
                throw new RuntimeException("异常111");
            }
        }).thenApply(a -> {
            CompletableFuture<Stock> stockCompletableFuture = CompletableFuture.supplyAsync(() -> stockService.findStockByCodeFake("000002"), taskPoolExecutor);
            try {
                a.put("stock", stockCompletableFuture.get());
                log.info("res={},\n耗时 {} 毫秒", a, (System.currentTimeMillis() - begin));

                return a;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).exceptionally((s)-> {
            return null;
        });

    }
}
