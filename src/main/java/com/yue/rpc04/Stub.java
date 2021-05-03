package com.yue.rpc04;

import com.yue.service.UserService;
import com.yue.bean.User;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;
import java.util.Arrays;

/**
 * 根据动态代理创建需要的对象
 */
public class Stub {
    public UserService getStub() {
        InvocationHandler invocationHandler = new InvocationHandler() {
            /**
             * 方法实际处理的地方
             * @param proxy
             * @param method
             * @param args
             * @return
             * @throws Exception
             */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
                Arrays.stream(args).forEach(arg -> {
                    System.out.print("args=");
                    System.out.print(arg + ",");
                    System.out.println();
                });
                System.out.println("args=" + args.toString());
                Socket socket = new Socket("127.0.0.1", 8888);

                // 创建输出流，向服务器发送数据
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);

                // 获取方法名和参数类型
                String methodName = method.getName();
                System.out.println("methodName=" + methodName);
                Class[] paramterType = method.getParameterTypes();
                Arrays.stream(paramterType).forEach(type -> {
                    System.out.print("paramterType=");
                    System.out.print(type + ",");
                    System.out.println();
                });

                // 将参数、参数类型、方法名封装进输出流
                oos.writeUTF(methodName);
                oos.writeObject(paramterType);
                oos.writeObject(args);
                oos.flush();

                // 向服务器发送请求
                socket.getOutputStream().write(baos.toByteArray());
                socket.getOutputStream().flush();

                // 接收服务器返回数据并解析
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                int id = dis.readInt();
                String name = dis.readUTF();

                User user = new User(id, name);
                oos.close();
                socket.close();

                return user;
            }
        };

        // 创建代理对象
        Object obj = Proxy.newProxyInstance(UserService.class.getClassLoader(), new Class[]{UserService.class}, invocationHandler);
        return (UserService) obj;
    }
}
