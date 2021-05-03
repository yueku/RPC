package com.yue.rpc04;

import com.yue.bean.User;
import com.yue.service.UserService;

public class Client {
    public static void main(String[] args) {
        Stub stub = new Stub();
        UserService userService = stub.getStub();

        User user = userService.getUserById(123);
        System.out.println(user);
    }
}
