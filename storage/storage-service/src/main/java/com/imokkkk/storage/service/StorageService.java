package com.imokkkk.storage.service;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * @author ImOkkkk
 * @date 2023/1/9 9:24
 * @since 1.0
 */
public interface StorageService {

    /** 扣除存储数量 */
    @TwoPhaseBusinessAction(
            name = "deduct",
            commitMethod = "commit",
            rollbackMethod = "rollback",
            useTCCFence = true)
    void deduct(
            @BusinessActionContextParameter(paramName = "commodityCode") String commodityCode,
            @BusinessActionContextParameter(paramName = "count") int count);

    boolean commit(BusinessActionContext actionContext);

    boolean rollback(BusinessActionContext actionContext);
}
