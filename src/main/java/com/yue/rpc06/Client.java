package com.yue.rpc06;

import com.yue.bean.Product;
import com.yue.bean.User;
import com.yue.service.ProductService;
import com.yue.service.UserService;

public class Client {
    public static void main(String[] args) {
        Stub stub = new Stub();

        UserService userService = (UserService) stub.getStub(UserService.class);
        User user = userService.getUserById(111);

        System.out.println(user.toString());
    }
}
