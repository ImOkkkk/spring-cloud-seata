package com.imokkkk.storage.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ImOkkkk
 * @date 2023/1/9 9:30
 * @since 1.0
 */
public interface StorageApi {

    @GetMapping("/storage/deduct")
    void deduct(
            @RequestParam("commodityCode") String commodityCode, @RequestParam("count") int count);
}
