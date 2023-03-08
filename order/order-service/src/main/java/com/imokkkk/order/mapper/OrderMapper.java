package com.imokkkk.order.mapper;

import com.imokkkk.order.pojo.entity.Order;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author ImOkkkk
 * @date 2023/1/9 9:59
 * @since 1.0
 */
@Repository
public interface OrderMapper extends Mapper<Order> {

    @Update("UPDATE order_tbl SET status = #{status} WHERE id = #{id}")
    Integer updateOrderStatus(String orderId, String success);
}
