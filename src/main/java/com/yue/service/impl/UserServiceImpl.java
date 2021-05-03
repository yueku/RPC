package com.yue.service.impl;

import com.yue.bean.User;
import com.yue.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public User getUserById(int id) {
        return new User(id, "李四");
    }
}
