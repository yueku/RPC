package com.yue.rpc03;

import com.yue.bean.User;
import com.yue.service.UserService;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

public class Stub {
    public static UserService getStub() {
        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket socket = new Socket("127.0.0.1", 8888);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(baos);
                dos.writeInt(123);

                socket.getOutputStream().write(baos.toByteArray());
                socket.getOutputStream().flush();

                DataInputStream dis = new DataInputStream(socket.getInputStream());
                int id = dis.readInt();
                String name = dis.readUTF();

                User user = new User(id, name);

                dos.close();
                socket.close();

                return user;
            }
        };
        Object object = Proxy.newProxyInstance(UserService.class.getClassLoader(), new Class[]{UserService.class},  invocationHandler);
        return (UserService) object;
    }
}
