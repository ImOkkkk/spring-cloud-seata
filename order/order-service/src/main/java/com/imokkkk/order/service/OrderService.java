package com.imokkkk.order.service;

import com.imokkkk.order.pojo.entity.Order;
import com.imokkkk.order.pojo.vo.UserOrderVO;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;
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
     * TCC的try方法：保存订单信息，状态为支付中
     *
     * <p>定义两阶段提交，在try阶段通过@TwoPhaseBusinessAction注解定义了分支事务的 resourceId，commit和 cancel 方法 name =
     * 该tcc的bean名称,全局唯一 commitMethod = commit 为二阶段确认方法 rollbackMethod = rollback 为二阶段取消方法
     * BusinessActionContextParameter注解 传递参数到二阶段中 useTCCFence
     * seata1.5.1的新特性，用于解决TCC幂等，悬挂，空回滚问题，需增加日志表tcc_fence_log
     */
    @TwoPhaseBusinessAction(
            name = "saveOrder",
            commitMethod = "commit",
            rollbackMethod = "rollback",
            useTCCFence = true)
    void saveOrder(
            @BusinessActionContextParameter(paramName = "orderId") String orderId,
            @BusinessActionContextParameter(paramName = "userId") String userId,
            @BusinessActionContextParameter(paramName = "commodityCode") String commodityCode,
            @BusinessActionContextParameter(paramName = "orderCount") int orderCount);

    /**
     * TCC的confirm方法：订单状态改为支付成功
     *
     * <p>二阶段确认方法可以另命名，但要保证与commitMethod一致 context可以传递try方法的参数
     *
     * @param actionContext
     * @return
     */
    boolean commit(BusinessActionContext actionContext);

    /**
     * TCC的cancel方法：订单状态改为支付失败 二阶段取消方法可以另命名，但要保证与rollbackMethod一致
     *
     * @param actionContext
     * @return
     */
    boolean rollback(BusinessActionContext actionContext);
}
