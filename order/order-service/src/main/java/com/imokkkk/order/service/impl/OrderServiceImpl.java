package com.imokkkk.order.service.impl;

import com.google.common.collect.Lists;
import com.imokkkk.order.client.StorageClient;
import com.imokkkk.order.client.UserClient;
import com.imokkkk.order.mapper.OrderMapper;
import com.imokkkk.order.pojo.entity.Order;
import com.imokkkk.order.pojo.vo.UserOrderVO;
import com.imokkkk.order.service.OrderService;
import com.imokkkk.user.pojo.entity.User;
import io.seata.spring.annotation.GlobalTransactional;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ImOkkkk
 * @date 2023/1/3 14:02
 * @since 1.0
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired private UserClient userClient;

    @Autowired private StorageClient storageClient;

    @Autowired private OrderMapper orderMapper;

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
        order.setUserId("u1").setCommodityCode("c1").setCount(99).setMoney(new BigDecimal("999"));
        orders.add(order);
        return orders;
    }

    /**
     * 下单：创建订单、减库存，涉及到两个服务
     *
     * @param userId
     * @param commodityCode
     * @param count
     */
    @Override
    @GlobalTransactional
    @Transactional(rollbackFor = Exception.class)
    public void purchase(String userId, String commodityCode, int count) {
        BigDecimal orderMoney = new BigDecimal(count).multiply(new BigDecimal(5));
        Order order =
                new Order()
                        .setUserId(userId)
                        .setCommodityCode(commodityCode)
                        .setCount(count)
                        .setMoney(orderMoney);
        orderMapper.insert(order);
        storageClient.deduct(commodityCode, count);
    }
}
