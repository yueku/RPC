package com.yue.rpc06;

import com.yue.bean.User;
import com.yue.service.UserService;
import com.yue.service.impl.UserServiceImpl;

import java.io.*;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static boolean RUNNING = true;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            proccess(serverSocket.accept());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void proccess(Socket socket) throws Exception{
        // 从socket获取输入流
        InputStream ip = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(ip);

        String methodName = ois.readUTF();
        Class[] paramterTypes = (Class[]) ois.readObject();
        Object[] args = (Object[]) ois.readObject();

        UserService userService = new UserServiceImpl();
        Method method = userService.getClass().getMethod(methodName, paramterTypes);
        User user = (User) method.invoke(userService, args);

        // 创建输出流，向客户端发送结果
        OutputStream os = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);

        oos.writeObject(user);
        oos.flush();
    }
}
