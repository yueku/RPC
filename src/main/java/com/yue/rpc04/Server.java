package com.yue.rpc04;

import com.yue.service.UserService;
import com.yue.bean.User;
import com.yue.service.impl.UserServiceImpl;

import java.io.*;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 使用动态代理的服务器端
 */
public class Server {
    private static boolean running = true;
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            while (running) {
                Socket socket = serverSocket.accept();
                process(socket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void process(Socket socket) throws Exception {
        // 创建输入流，接收客户端发送来的消息
        InputStream is = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(is);

        String methonName = ois.readUTF();
        Class[] paramaterType = (Class[])ois.readObject();
        Object[] args = (Object[]) ois.readObject();

        UserService userService = new UserServiceImpl();
        Method method = userService.getClass().getMethod(methonName, paramaterType);
        User user = (User)method.invoke(userService, args);
        System.out.println(user.toString());
        // 创建输出流，向客户端发送结果
        OutputStream os = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);

        dos.writeInt(user.getId());
        dos.writeUTF(user.getName());
        dos.flush();
    }
}
