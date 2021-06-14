package com.frank.service;

import com.frank.entity.mysql.Stock;

import java.util.List;

/**
 * @author frank
 * @version 1.0
 * @date 2018/8/15 0015 下午 10:41
 */
public interface IStockService {
    Stock findByStockCode(String stockCode);

    /**
     * 根据股票代码集合查询
     *
     * @param stockCodes
     * @return
     */
    List<Stock> findByStockCodes(List<String> stockCodes);

    Stock findByStockName(String stockName);

    Stock save(Stock stock);

    Stock saveWithRuntimeException(Stock stock);

    List<Stock> findByArea(String area);
}
