package com.frank.service;

import com.frank.entity.mysql.Stock;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author frank
 * @version 1.0
 * @date 2018/8/15 0015 下午 10:41
 */
public interface IStockService {
    Stock findByStockCode(String stockCode);

    Stock findByStockName(String stockName);

    Stock save(Stock stock);

    Stock saveWithRuntimeException(Stock stock);

    List<Stock> findByArea(String area);

    /**
     * 模拟从第三方批量获取数据
     *
     * @param stockCodes
     * @return
     */
    List<Stock> findByStockCodesReomte(List<String> stockCodes);

    /**
     * 模拟从第三方获取数据
     *
     * @param code
     * @return
     */
    Stock findStockByCodeRemote(String code) throws ExecutionException, InterruptedException;
}
