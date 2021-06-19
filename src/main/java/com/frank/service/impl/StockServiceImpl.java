package com.frank.service.impl;

import com.frank.entity.mysql.Stock;
import com.frank.enums.Common;
import com.frank.model.StockRequest;
import com.frank.repository.mysql.StockRepository;
import com.frank.service.IStockService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * @author frank
 * @version 1.0
 * @date 2018/8/15 0015 下午 10:41
 */
@Service
@Slf4j
public class StockServiceImpl implements IStockService {

    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private StockRepository stockRepository;

    /**
     * 暂存请求的队列
     */
    BlockingQueue<StockRequest> queue = new LinkedBlockingQueue<>();

    private static ThreadLocal<Long> diff = ThreadLocal.withInitial(() -> System.currentTimeMillis());

    /**
     * ScheduledExecutorService
     * 定时任务阻塞，超过下下次请求时间，会导致请求时间顺延
     */
    @PostConstruct
    public void init() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(() -> {

            long l = System.currentTimeMillis() - diff.get();
            log.info("时间差={}", l);
            diff.set(System.currentTimeMillis());
            int size;
            if ((size = queue.size()) <= 0) {
                log.info("请求缓存队列数据为空");
                return;
            }

            /**
             * 存放请求对象的集合
             */
            List<StockRequest> requests = new ArrayList<>();

            /**
             * 用于批量查询的请求参数
             */
            List<String> realRequestParam = new ArrayList<>();

            IntStream.range(0, size).forEach(m -> {
                StockRequest request = queue.poll();
                requests.add(request);
                realRequestParam.add(request.getStockCode());
            });


            int random2 = RandomUtils.nextInt(200, 1000);
            log.info("模拟延迟随机毫秒数={}", random2);
            try {
                Thread.sleep(random2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("查询条数={},realRequestParam={}", size, realRequestParam);
            List<Stock> stockResult = this.findByStockCodesReomte(realRequestParam);
            log.info("查询结果={}", stockResult);
            for (StockRequest request : requests) {
                boolean find = false;
                for (Stock stock : stockResult) {
                    if (request.getStockCode().equals(stock.getCode())) {
                        request.getFuture().complete(stock);
                        find = true;
                        break;
                    }
                }
                if (!find) {
                    request.getFuture().complete(null);
                    log.info("未找到数据stockCode={}", request.getStockCode());
                }
            }


        }, 0, 1000, TimeUnit.MILLISECONDS);
    }


    @Override
    public Stock findStockByCodeRemote(String code) throws ExecutionException, InterruptedException, TimeoutException {
        StockRequest request = new StockRequest();
        CompletableFuture<Stock> future = new CompletableFuture<>();
        request.setFuture(future);
        request.setStockCode(code);
        queue.add(request);
        /**
         * 设置等待时间，1200毫秒
         */
        return future.get(1200, TimeUnit.MILLISECONDS);
    }

    @Override
    @Cacheable(value = "stock", key = "#stockCode")
    //   @CachePut(value = "stock", key = "#stockCode")
    public Stock findByStockCode(String stockCode) {
        log.info("findByStockCode缓存未命中,stockCode={}", stockCode);
        Stock stock = stockRepository.findByStockCode(stockCode);
        Cache cache = cacheManager.getCache(Common.STOCK.getValue());
        cache.putIfAbsent(stockCode, stock);
        cache.putIfAbsent(stock.getName(), stock);
        log.info("findByStockCode stockCode={}放入缓存", stockCode);
        return stock;
    }

    @Override
    @Cacheable(value = "stock", key = "#stockName")
    public Stock findByStockName(String stockName) {
        return stockRepository.findByStockName(stockName);
    }


    @Override
    public List<Stock> findByStockCodesReomte(List<String> stockCodes) {
        if (CollectionUtils.isEmpty(stockCodes)) {
            return null;
        }
        return stockRepository.findByStockCodes(stockCodes);
    }

    @Override
    public List<Stock> findByArea(String area) {
        return null;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Stock save(Stock stock) {
        return stockRepository.save(stock);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Stock saveWithRuntimeException(Stock stock) {
        Stock save = stockRepository.save(stock);
        if ("test00".equals(stock.getCode())) {
            throw new RuntimeException();
        }
        return save;
    }
}
