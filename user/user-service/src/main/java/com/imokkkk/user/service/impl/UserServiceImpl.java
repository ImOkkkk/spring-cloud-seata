package com.imokkkk.user.service.impl;

import com.imokkkk.user.pojo.entity.User;
import com.imokkkk.user.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author ImOkkkk
 * @date 2023/1/3 13:28
 * @since 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public User getByUserId(String userId) {
        User user = new User();
        user.setUserId(userId);
        user.setName("张三");
        return user;
    }
}
