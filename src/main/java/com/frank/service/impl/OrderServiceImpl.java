package com.frank.service.impl;

import com.frank.model.Order;
import com.frank.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author frank
 * @version 1.0
 * @date 2021/6/20 0020 上午 10:39
 */
@Slf4j
@Service
public class OrderServiceImpl implements IOrderService {
    @Override
    public Order getByOrderIdFake(String orderId) {
        Order order = new Order();
        order.setRemark("测试订单");
        order.setAmount(1);
        order.setOrderId(orderId);
        order.setPurchaseAt(System.currentTimeMillis());
        try {
            log.info("getByOrderIdFake线程id={}", Thread.currentThread().getId());
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return order;
    }
}
