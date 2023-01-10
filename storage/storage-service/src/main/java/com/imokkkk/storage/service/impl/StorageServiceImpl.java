package com.imokkkk.storage.service.impl;

import com.imokkkk.storage.entity.Stock;
import com.imokkkk.storage.mapper.StorageMapper;
import com.imokkkk.storage.service.StorageService;
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
public class StorageServiceImpl implements StorageService {

    @Autowired private StorageMapper storageMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deduct(String commodityCode, int count) {
        if ("product-2".equals(commodityCode)) {
            throw new RuntimeException("异常:模拟业务异常:stock branch exception");
        }
        Example example =
                Example.builder(Stock.class)
                        .where(
                                WeekendSqls.<Stock>custom()
                                        .andEqualTo(Stock::getCommodityCode, commodityCode))
                        .build();
        Stock stock = storageMapper.selectOneByExample(example);
        stock.setCount(stock.getCount() - count);
        storageMapper.updateByPrimaryKey(stock);
    }
}
