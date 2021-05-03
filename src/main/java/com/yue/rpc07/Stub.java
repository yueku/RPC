package com.yue.rpc07;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

public class Stub {
    /**
     * 动态代理生成对象
     * @param clazz
     * @return
     */
    public Object getStub(Class clazz) {
        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket socket = new Socket("127.0.0.1", 8888);

                // 构建字节输出流，封装向服务器发送的数据
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                // 类名
                String clazzName = clazz.getName();
                System.out.println("类名：" + clazzName);
                // 方法名
                String methodName = method.getName();
                System.out.println("方法名：" + methodName);
                // 参数类型
                Class[] paramterTypes = method.getParameterTypes();
                // 封装数据值输出流
                objectOutputStream.writeUTF(clazzName);
                objectOutputStream.writeUTF(methodName);
                objectOutputStream.writeObject(paramterTypes);
                objectOutputStream.writeObject(args);
                objectOutputStream.flush();

                // 通过socket将数据发送到服务器端
                socket.getOutputStream().write(byteArrayOutputStream.toByteArray());
                socket.getOutputStream().flush();

                // 根据socket获取输入流，获取返回结果
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Object object = objectInputStream.readObject();

                return object;
            }
        };

        Object object = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, invocationHandler);
        return object;
    }
}
