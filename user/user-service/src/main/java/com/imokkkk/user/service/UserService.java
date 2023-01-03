package com.imokkkk.user.service;

import com.imokkkk.user.pojo.entity.User;

/**
 * @author ImOkkkk
 * @date 2023/1/3 13:29
 * @since 1.0
 */
public interface UserService {
    User getByUserId(String userId);
}
