package com.imokkkk.order.pojo.entity;

import java.math.BigDecimal;
import lombok.Data;

/**
 * @author ImOkkkk
 * @date 2023/1/3 14:13
 * @since 1.0
 */
@Data
public class Order {

    private Long id;

    private String orderId;

    private BigDecimal money;
}
