package com.imokkkk.storage.service.impl;

import com.imokkkk.storage.entity.Stock;
import com.imokkkk.storage.mapper.StorageMapper;
import com.imokkkk.storage.service.StorageService;
import io.seata.core.context.RootContext;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.WeekendSqls;

/**
 * @author ImOkkkk
 * @date 2023/1/9 9:32
 * @since 1.0
 */
@Service
@Slf4j
public class StorageServiceImpl implements StorageService {

    @Autowired private StorageMapper storageMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deduct(String commodityCode, int orderCount) {
        log.info("=============冻结库存=================");
        log.info("当前 XID: {}", RootContext.getXID());

        // 检查库存
        checkStock(commodityCode,orderCount);

        log.info("开始冻结 {} 库存", commodityCode);
        //冻结库存
        Integer record = storageMapper.freezeStorage(commodityCode, orderCount);
        log.info("冻结 {} 库存结果:{}", commodityCode, record > 0 ? "操作成功" : "扣减库存失败");
    }

    @Override
    public boolean commit(BusinessActionContext actionContext) {
        log.info("=============扣减冻结库存=================");

        String commodityCode = actionContext.getActionContext("commodityCode").toString();
        int count = (int) actionContext.getActionContext("count");
        //扣减冻结库存
        storageMapper.reduceFreezeStorage(commodityCode,count);

        return true;
    }

    @Override
    public boolean rollback(BusinessActionContext actionContext) {
        log.info("=============解冻库存=================");

        String commodityCode = actionContext.getActionContext("commodityCode").toString();
        int count = (int) actionContext.getActionContext("count");
        //扣减冻结库存
        storageMapper.unfreezeStorage(commodityCode,count);

        return true;
    }

    private void checkStock(String commodityCode, int orderCount){
        log.info("检查 {} 库存", commodityCode);
        int count = storageMapper.findCountByCommodityCode(commodityCode);
        if (count < orderCount) {
            log.warn("{} 库存不足，当前库存:{}", commodityCode, count);
            throw new RuntimeException("库存不足");
        }

    }
}
