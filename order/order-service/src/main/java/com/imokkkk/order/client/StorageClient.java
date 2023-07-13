package com.imokkkk.order.client;

import com.imokkkk.order.discovery.client.config.StorageServiceFeignClientConfiguration;
import com.imokkkk.storage.api.StorageApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author ImOkkkk
 * @date 2023/1/3 14:03
 * @since 1.0
 */
@FeignClient(name = "storage", configuration = StorageServiceFeignClientConfiguration.class)
public interface StorageClient extends StorageApi {}
