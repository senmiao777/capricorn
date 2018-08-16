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

    Stock findByStockName(String stockName);

    List<Stock> findByArea(String area);
}
