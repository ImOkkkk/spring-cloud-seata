package com.imokkkk.order.client;

import com.imokkkk.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author ImOkkkk
 * @date 2023/1/3 14:03
 * @since 1.0
 */
@FeignClient(name = "user")
public interface UserClient extends UserApi {

}
