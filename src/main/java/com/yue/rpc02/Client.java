package com.yue.rpc02;

import com.yue.bean.User;

/**
 * 客户端
 */
public class Client {
    public static void main(String[] args) {
        Stub stub = new Stub();
        User user = stub.getUserById(1);

        System.out.println(user.toString());
    }
}
