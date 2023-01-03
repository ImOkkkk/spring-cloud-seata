package com.imokkkk.order.pojo.vo;

import java.util.List;
import lombok.Data;
import com.imokkkk.order.pojo.entity.Order;

/**
 * @author ImOkkkk
 * @date 2023/1/3 14:13
 * @since 1.0
 */
@Data
public class UserOrderVO {

    private String userId;

    private String name;

    private List<Order> orders;
}
