package com.frank.service.impl;

import com.frank.entity.mysql.Stock;
import com.frank.enums.Common;
import com.frank.repository.mysql.StockRepository;
import com.frank.service.IStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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
        Stock s = stockRepository.findByStockCode(stockCode);
        cacheManager.getCache(Common.STOCK.getValue()).putIfAbsent(stockCode, s);
        log.info("findByStockCode stockCode={}放入缓存", stockCode);
        return s;
    }

    @Override
    public List<Stock> findByArea(String area) {
        return null;
    }
}
