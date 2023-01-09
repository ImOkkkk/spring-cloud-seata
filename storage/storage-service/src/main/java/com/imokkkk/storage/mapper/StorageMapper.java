package com.imokkkk.storage.mapper;

import com.imokkkk.storage.entity.Stock;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author ImOkkkk
 * @date 2023/1/9 9:47
 * @since 1.0
 */
@Repository
public interface StorageMapper extends Mapper<Stock> {

}
