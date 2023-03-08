package com.imokkkk.order.service.impl;

import cn.hutool.core.lang.UUID;
import com.imokkkk.order.client.StorageClient;
import com.imokkkk.order.service.BusinessService;
import com.imokkkk.order.service.OrderService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liuwy
 * @date 2023/3/8 14:42
 * @since 1.0
 */
@Service
@Slf4j
public class BusinessServiceImpl implements BusinessService {
    @Autowired private OrderService orderService;
    @Autowired private StorageClient storageClient;

    @Override
    @GlobalTransactional(name = "createOrder", rollbackFor = Exception.class)
    public void createOrder(String commodityCode, int orderCount) {
        log.info("=============用户下单=================");
        log.info("当前 XID: {}", RootContext.getXID());

        String orderId = UUID.fastUUID().toString();
        // 创建订单
        orderService.saveOrder(orderId, "1", commodityCode, orderCount);
        // 扣减库存
        storageClient.deduct(commodityCode, orderCount);
    }
}
