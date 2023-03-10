package com.imokkkk.order.pojo.entity;

import java.math.BigDecimal;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author ImOkkkk
 * @date 2023/1/3 14:13
 * @since 1.0
 */
@Data
@Accessors(chain = true)
@Table(name = "`order`")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String commodityCode;
    private Integer count;
    private BigDecimal money;
}
