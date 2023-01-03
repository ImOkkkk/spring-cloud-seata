package com.imokkkk.user.controller;

import com.imokkkk.user.pojo.entity.User;
import com.imokkkk.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ImOkkkk
 * @date 2023/1/3 13:17
 * @since 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired private UserService userService;

    @GetMapping("/detail")
    public User detail(@RequestParam("userId") String userId) {
        return userService.getByUserId(userId);
    }
}
