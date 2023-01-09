package com.imokkkk.order.service;

import com.imokkkk.order.pojo.entity.Order;
import com.imokkkk.order.pojo.vo.UserOrderVO;
import java.util.List;

/**
 * @author ImOkkkk
 * @date 2023/1/3 14:02
 * @since 1.0
 */
public interface OrderService {

    UserOrderVO userOrderByUserId(String userId);

    List<Order> listByUserId(String userId);

    /**
     * 采购
     * @param userId
     * @param commodityCode
     * @param orderCount
     */
    void purchase(String userId, String commodityCode, int orderCount);

}
