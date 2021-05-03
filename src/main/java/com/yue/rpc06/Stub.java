package com.yue.rpc06;

import com.yue.bean.User;

import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

public class Stub {
    public Object getStub(Class clazz) {
        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket socket = new Socket("127.0.0.1", 8888);

                // 类名
                String clazzName = clazz.getName();
                // 方法名
                String methodName = method.getName();
                // 参数类型
                Class[] paramterTypes = method.getParameterTypes();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeUTF(clazzName);
                oos.writeUTF(methodName);
                oos.writeObject(paramterTypes);
                // 封装参数
                oos.writeObject(args);
                oos.flush();

                socket.getOutputStream().write(baos.toByteArray());
                socket.getOutputStream().flush();

                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                User user = (User) ois.readObject();

                oos.close();
                socket.close();

                return user;
            }
        };

        Object object = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, invocationHandler);
        return object;
    }
}
