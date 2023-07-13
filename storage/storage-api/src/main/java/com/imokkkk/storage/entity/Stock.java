package com.imokkkk.storage.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author ImOkkkk
 * @date 2023/1/9 9:48
 * @since 1.0
 */
@Data
@Accessors(chain = true)
@Table(name = "storage_tbl")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String commodityCode;
    private Long count;

}
