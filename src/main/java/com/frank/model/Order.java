package com.frank.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author frank
 * @version 1.0
 * @date 2021/6/20 0020 上午 10:36
 */
@NoArgsConstructor
@Data
@ToString
public class Order extends AbstractOrder{

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 购买时间戳
     */
    private Long purchaseAt;

    /**
     * 购买数量
     */
    private Integer amount;


    @Override
    void commonPrintOrder() {
        super.commonPrintOrder();
    }
}
