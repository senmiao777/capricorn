package com.frank.service.impl;

import com.frank.entity.mysql.Stock;
import com.frank.enums.Common;
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

import java.util.List;

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
