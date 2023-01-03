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
}
