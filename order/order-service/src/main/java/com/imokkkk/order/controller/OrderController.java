package com.imokkkk.order.controller;

import com.imokkkk.order.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ImOkkkk
 * @date 2023/1/3 14:00
 * @since 1.0
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired private BusinessService businessService;


    /**
     * 下单：插入订单表、扣减库存，模拟提交成功
     *
     * @return
     */
    @GetMapping("/placeOrder/commit")
    public Boolean placeOrderCommit() {

        businessService.createOrder("product-1", 1);
        return true;
    }

    /**
     * 下单：插入订单表、扣减库存，模拟回滚
     *
     * @return
     */
    @GetMapping("/placeOrder/rollback")
    public Boolean placeOrderRollback() {
        // product-2 扣库存时模拟了一个业务异常,
        businessService.createOrder("product-2", 1);
        return true;
    }
}
