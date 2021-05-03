package com.yue.rpc05;

import com.yue.service.UserService;
import com.yue.bean.User;

public class Client {
    public static void main(String[] args) {
        Stub stub = new Stub();
        UserService userService = stub.getStub();

        User user = userService.getUserById(123);
        System.out.println(user);
    }
}
