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
    private String stockCode;
    /**
     * 如果业务参数不唯一，可以自己生成一个唯一值，用于查询条件和返回结果的绑定
     */
    private String uniqueId;
    private CompletableFuture<Stock> future;
}
