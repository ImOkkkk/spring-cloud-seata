package com.imokkkk.order.service.impl;

import com.google.common.collect.Lists;
import com.imokkkk.order.client.UserClient;
import com.imokkkk.order.pojo.entity.Order;
import com.imokkkk.order.pojo.vo.UserOrderVO;
import com.imokkkk.order.service.OrderService;
import com.imokkkk.user.pojo.entity.User;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ImOkkkk
 * @date 2023/1/3 14:02
 * @since 1.0
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired private UserClient userClient;

    public UserOrderVO userOrderByUserId(String userId) {
        User user = userClient.detail(userId);
        UserOrderVO userOrderVO = new UserOrderVO();
        if (user != null) {
            userOrderVO.setUserId(userId);
            userOrderVO.setName(user.getName());
            userOrderVO.setOrders(this.listByUserId(userId));
        }
        return userOrderVO;
    }

    public List<Order> listByUserId(String userId) {
        List<Order> orders = Lists.newArrayList();

        Order order = new Order();
        order.setOrderId("o1");
        order.setMoney(new BigDecimal("500"));
        orders.add(order);

        return orders;
    }
}
