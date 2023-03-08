package com.imokkkk.order.service.impl;

import com.google.common.collect.Lists;
import com.imokkkk.order.client.StorageClient;
import com.imokkkk.order.client.UserClient;
import com.imokkkk.order.mapper.OrderMapper;
import com.imokkkk.order.pojo.entity.Order;
import com.imokkkk.order.pojo.vo.UserOrderVO;
import com.imokkkk.order.service.OrderService;
import com.imokkkk.user.pojo.entity.User;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import java.math.BigDecimal;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ImOkkkk
 * @date 2023/1/3 14:02
 * @since 1.0
 */
@Service
@Slf4j
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrder(
            @BusinessActionContextParameter(paramName = "orderId") String orderId,
            @BusinessActionContextParameter(paramName = "userId") String userId,
            @BusinessActionContextParameter(paramName = "commodityCode") String commodityCode,
            @BusinessActionContextParameter(paramName = "orderCount") int orderCount) {
        BigDecimal orderMoney = new BigDecimal(orderCount).multiply(new BigDecimal(5));
        Order order =
                new Order()
                        .setUserId(userId)
                        .setCommodityCode(commodityCode)
                        .setCount(orderCount)
                        .setMoney(orderMoney);
        int saveOrderRecord = orderMapper.insert(order);
        log.info("保存订单{}", saveOrderRecord > 0 ? "成功" : "失败");
    }

    @Override
    public boolean commit(BusinessActionContext actionContext) {
        String orderId = actionContext.getActionContext("orderId").toString();
        // 更新订单状态为支付成功
        Integer updateOrderRecord = orderMapper.updateOrderStatus(orderId, "SUCCESS");
        log.info("更新订单id:{} {}", orderId, updateOrderRecord > 0 ? "成功" : "失败");
        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        String orderId = actionContext.getActionContext("orderId").toString();
        // 更新订单状态为支付失败
        Integer updateOrderRecord = orderMapper.updateOrderStatus(orderId, "FAIL");
        log.info("更新订单id:{} {}", orderId, updateOrderRecord > 0 ? "成功" : "失败");
        return true;
    }
}
