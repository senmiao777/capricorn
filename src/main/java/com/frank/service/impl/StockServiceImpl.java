package com.frank.service.impl;

import com.frank.entity.mysql.Stock;
import com.frank.repository.mysql.StockRepository;
import com.frank.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author frank
 * @version 1.0
 * @date 2018/8/15 0015 下午 10:41
 */
@Service
public class StockServiceImpl implements IStockService {
    @Autowired
    private StockRepository stockRepository;

    @Override
    @Cacheable(value = "stock", key = "#stockCode")
    public Stock findByStockCode(String stockCode) {
        return stockRepository.findByStockCode(stockCode);
    }

    @Override
    public List<Stock> findByArea(String area) {
        return null;
    }
}
