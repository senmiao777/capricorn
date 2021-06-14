package com.frank.service.impl;

import com.frank.entity.mysql.Stock;
import com.frank.enums.Common;
import com.frank.model.CompletableFutureRequest;
import com.frank.repository.mysql.StockRepository;
import com.frank.service.IStockService;
import lombok.extern.slf4j.Slf4j;
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
import java.util.stream.Collectors;
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

            System.out.println("查询条数=" + size);
            List<Stock> stockResult = this.findByStockCodes(param);
//          temporary.stream()
//                  .filter(t -> stockResult.stream().map(Stock::getCode).collect(Collectors.toList()).contains(t))
//                  .findAny().ifPresent(r->r.future.complete());
            for (CompletableFutureRequest request : temporary) {
                for (Stock stock : stockResult) {
                    if (request.getStockCode().equals(stock.getCode())) {
                        request.getFuture().complete(stock);
                    }
                }
            }
        }, 0, 5, TimeUnit.SECONDS);
    }


    @Override
    public Stock findStockByRemote(String code) throws ExecutionException, InterruptedException {
        CompletableFutureRequest request = new CompletableFutureRequest();
        CompletableFuture<Stock> future = new CompletableFuture<>();
        request.setFuture(future);
        request.setStockCode(code);
        queue.add(request);
        return future.get();
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
    public List<Stock> findByStockCodes(List<String> stockCodes) {
        if (CollectionUtils.isEmpty(stockCodes)) {
            return null;
        }
        return stockRepository.findByStockCodes(stockCodes.stream().map(String::valueOf).collect(Collectors.joining(",")));
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
