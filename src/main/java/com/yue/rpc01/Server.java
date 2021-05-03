package com.yue.rpc01;

import com.yue.bean.User;
import com.yue.service.UserService;
import com.yue.service.impl.UserServiceImpl;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static boolean running = true;

    public static void main(String[] args) {
        try {
            // 创建服务器socket
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
        // 创建输入流，用于接收客户端发送来的消息
        InputStream inputStream = socket.getInputStream();
        DataInputStream dis = new DataInputStream(inputStream);

        // 创建输出流，用户向客户端发送消息
        OutputStream outputStream = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(outputStream);

        // 接收客户端发送来的用户ID
        int id = dis.readInt();

        // 根据ID获取用户信息
        UserService userService = new UserServiceImpl();
        User user = userService.getUserById(id);

        // 向客户端发送用户信息
        dos.writeInt(user.getId());
        dos.writeUTF(user.getName());

        dos.flush();
    }
}
