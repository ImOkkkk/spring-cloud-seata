package com.imokkkk.order.controller;

import com.imokkkk.order.pojo.vo.UserOrderVO;
import com.imokkkk.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ImOkkkk
 * @date 2023/1/3 14:00
 * @since 1.0
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired private OrderService orderService;

    @GetMapping("/userOrderByUserId")
    public UserOrderVO userOrderByUserId(@RequestParam("userId") String userId) {
        return orderService.userOrderByUserId(userId);
    }

    /**
     * 下单：插入订单表、扣减库存，模拟提交成功
     *
     * @return
     */
    @RequestMapping("/placeOrder/commit")
    public Boolean placeOrderCommit() {

        orderService.purchase("1", "product-1", 1);
        return true;
    }

    /**
     * 下单：插入订单表、扣减库存，模拟回滚
     *
     * @return
     */
    @RequestMapping("/placeOrder/rollback")
    public Boolean placeOrderRollback() {
        // product-2 扣库存时模拟了一个业务异常,
        orderService.purchase("1", "product-2", 1);
        return true;
    }

    @RequestMapping("/placeOrder")
    public Boolean placeOrder(String userId, String commodityCode, Integer count) {
        orderService.purchase(userId, commodityCode, count);
        return true;
    }
}
