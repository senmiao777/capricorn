package com.frank.controller;

import com.frank.entity.mysql.Stock;
import com.frank.entity.mysql.User;
import com.frank.model.JsonResult;
import com.frank.model.Order;
import com.frank.service.IOrderService;
import com.frank.service.IStockService;
import com.frank.service.IUserService;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Slf4j
@RestController
@RequestMapping(value = "/demo")
public class DemoController {

    @Autowired
    private IStockService stockService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    @Qualifier("testTaskPoolExecutor")
    private Executor taskPoolExecutor;

    /**
     * 每秒发十个令牌
     */
    RateLimiter rateLimiter = RateLimiter.create(10);


    @RequestMapping(value = "/info/async", method = RequestMethod.GET)
    public JsonResult infoasync(Long userId) {
        long begin = System.currentTimeMillis();
        User user = userService.findByUserIdFake(userId);
        Order order = orderService.getByOrderIdFake("test123123");
        Stock stock = stockService.findStockByCodeFake("000002");
        Map<String, Object> res = new HashMap<>(8);
        res.put("user", user);
        res.put("order", order);
        res.put("stock", stock);
        long cost = System.currentTimeMillis() - begin;
        res.put("cost", cost);
        log.info("res={},耗时 {} 毫秒", res, cost);
        return JsonResult.buildSuccessResult(res);
    }


    @RequestMapping(value = "/info/async2", method = RequestMethod.GET)
    public JsonResult infoasync2(Long userId) {
        long begin = System.currentTimeMillis();
        CompletableFuture<User> userCompletableFuture = CompletableFuture.supplyAsync(() -> userService.findByUserIdFake(userId), taskPoolExecutor);
        CompletableFuture<Order> orderCompletableFuture = CompletableFuture.supplyAsync(() -> orderService.getByOrderIdFake("test123123"), taskPoolExecutor);
        CompletableFuture<Stock> stockCompletableFuture = CompletableFuture.supplyAsync(() -> stockService.findStockByCodeFake("000002"), taskPoolExecutor);

        Map<String, Object> res = new HashMap<>(8);
        //  try {
        userCompletableFuture.whenComplete((v, e) -> {

            if (e != null) {
                log.error("查询用户信息异常，e={}",e);
                User u = new User();
                u.setUserName("兜底数据");
                res.put("user", u);
            } else {
                res.put("user", v);
            }
        });
        orderCompletableFuture.whenComplete((v, e) -> res.put("order", v));
        stockCompletableFuture.whenComplete((v, e) -> res.put("stock", v));

        CompletableFuture.allOf(userCompletableFuture, orderCompletableFuture, stockCompletableFuture)
                .thenAccept((a) -> {
                    long cost = System.currentTimeMillis() - begin;
                    log.info("res={},耗时 {} 毫秒", res, cost);
                    res.put("cost", cost);
                })
                .exceptionally((e) -> {
                    log.error("查询异常e={}", e.getMessage());
                    return null;
                    // return JsonResult.buildErrorResult(e.getMessage());
                });
        return JsonResult.buildSuccessResult(res);
    }


    @RequestMapping(value = "/info/async3", method = RequestMethod.GET)
    public JsonResult infoasync3(Long userId) {
        long begin = System.currentTimeMillis();
        CompletableFuture<User> userCompletableFuture = CompletableFuture.supplyAsync(() -> userService.findByUserIdFake(userId), taskPoolExecutor);
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
                long cost = System.currentTimeMillis() - begin;
                a.put("cost", cost);
                log.info("res={},\n耗时 {} 毫秒", a, (System.currentTimeMillis() - begin));
                return JsonResult.buildSuccessResult(a);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }).whenComplete((r, e) -> {
            JsonResult.buildSuccessResult(r);
        });

        return null;
    }
}
