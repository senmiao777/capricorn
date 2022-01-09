package com.frank.model;

import com.frank.entity.mysql.Stock;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.CompletableFuture;

/**
 * @author frank
 * @version 1.0
 * @date 2021/6/15 0015 上午 12:07
 */
@Data
@NoArgsConstructor
public class StockRequest {

    /**
     * 查询参数
     */
    private String stockCode;

    private CompletableFuture<Stock> future;
}
