package com.imokkkk.user.api;

import com.imokkkk.user.pojo.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ImOkkkk
 * @date 2023/1/3 13:05
 * @since 1.0
 */
public interface UserApi {

    @GetMapping("/user/detail")
    User detail(@RequestParam("userId") String userId);
}
