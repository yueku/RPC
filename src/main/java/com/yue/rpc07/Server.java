package com.yue.rpc07;

import java.io.*;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static boolean RUNNING = true;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            while (RUNNING) {
                Socket socket = serverSocket.accept();
                process(socket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void process(Socket socket) throws Exception {
        // 从socket里面获取输入流
        InputStream inputStream = socket.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

        // 从输入流里面获取各种数据
        // 类名
        String clazzName = objectInputStream.readUTF();
        System.out.println("类名：" + clazzName);
        // 方法名
        String methodName = objectInputStream.readUTF();
        System.out.println("方法名：" + methodName);
        // 参数类型
        Class[] pramaterType = (Class[]) objectInputStream.readObject();
        // 参数
        Object[] paramter = (Object[]) objectInputStream.readObject();

        // 根据反射获取对象（Spring可以注入）
        Class clazz = Class.forName(clazzName);
        Object object = clazz.newInstance();
        // 获取方法
        Method method = object.getClass().getMethod(methodName, pramaterType);
        // 调用方法获取结果
        Object obj = method.invoke(object, paramter);

        // 将结果封装到输出流返回给客户端
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(obj);
        objectOutputStream.flush();
    }
}
