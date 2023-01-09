package com.imokkkk.storage.service;

/**
 * @author ImOkkkk
 * @date 2023/1/9 9:24
 * @since 1.0
 */
public interface StorageService {

    /**
     * 扣除存储数量
     */
    void deduct(String commodityCode, int count);
}
