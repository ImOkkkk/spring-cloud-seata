package com.imokkkk.storage.mapper;

import com.imokkkk.storage.entity.Stock;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author ImOkkkk
 * @date 2023/1/9 9:47
 * @since 1.0
 */
@Repository
public interface StorageMapper extends Mapper<Stock> {

    /**
     * 获取库存
     *
     * @param commodityCode 商品编号
     * @return
     */
    @Select("SELECT count FROM storage_tbl WHERE commodity_code = #{commodityCode}")
    Integer findCountByCommodityCode(@Param("commodityCode") String commodityCode);

    /**
     * 扣减库存
     *
     * @param commodityCode 商品编号
     * @param count 扣减数量
     * @return
     */
    @Update(
            "UPDATE storage_tbl SET count = count - #{count} WHERE commodity_code = #{commodityCode}")
    int reduceStorage(@Param("commodityCode") String commodityCode, @Param("count") Integer count);

    /**
     * 冻结库存 try: 库存-扣减数量，冻结库存+扣减数量
     *
     * @param commodityCode 商品编号
     * @param count 扣减数量
     * @return
     */
    @Update(
            "UPDATE storage_tbl SET count = count - #{count},freeze_count=freeze_count+#{count} WHERE commodity_code = #{commodityCode}")
    int freezeStorage(@Param("commodityCode") String commodityCode, @Param("count") Integer count);

    /**
     * 扣减冻结的库存（真正的扣减库存） confirm： 冻结库存-扣减数量
     *
     * @param commodityCode 商品编号
     * @param count 扣减数量
     * @return
     */
    @Update(
            "UPDATE storage_tbl SET freeze_count=freeze_count-#{count} WHERE commodity_code = #{commodityCode}")
    int reduceFreezeStorage(
            @Param("commodityCode") String commodityCode, @Param("count") Integer count);

    /**
     * 解冻库存 cancel: 库存+扣减数量，冻结库存-扣减数量
     *
     * @param commodityCode 商品编号
     * @param count 扣减数量
     * @return
     */
    @Update(
            "UPDATE storage_tbl SET count = count + #{count},freeze_count=freeze_count-#{count} WHERE commodity_code = #{commodityCode}")
    int unfreezeStorage(
            @Param("commodityCode") String commodityCode, @Param("count") Integer count);
}
