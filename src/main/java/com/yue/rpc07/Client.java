package com.yue.rpc07;

import com.yue.bean.Product;
import com.yue.bean.User;
import com.yue.service.ProductService;
import com.yue.service.UserService;

public class Client {
    public static void main(String[] args) {
        Stub stub = new Stub();
        UserService userService = (UserService) stub.getStub(UserService.class);
        User user = userService.getUserById(123);
        System.out.println(user.toString());

        ProductService productService = (ProductService) stub.getStub(ProductService.class);
        Product product = productService.getProdById(123);
        System.out.println(product);
    }
}
