package com.frank.service;

import com.frank.model.Order;

/**
 * @author frank
 * @version 1.0
 * @date 2021/6/14 0014 下午 11:15
 */
public interface IOrderService {
    Order getByOrderIdFake(String orderId);
}
