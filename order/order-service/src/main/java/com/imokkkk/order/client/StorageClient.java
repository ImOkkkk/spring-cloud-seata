package com.imokkkk.order.client;

import com.imokkkk.storage.api.StorageApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author ImOkkkk
 * @date 2023/1/3 14:03
 * @since 1.0
 */
@FeignClient(name = "storage")
public interface StorageClient extends StorageApi {

}
