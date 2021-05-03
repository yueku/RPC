package com.yue.rpc03;

import com.yue.bean.User;
import com.yue.service.UserService;

public class Client {
    public static void main(String[] args) {
        UserService userService = Stub.getStub();
        User user = userService.getUserById(12);
        System.out.println(user);
    }
}
