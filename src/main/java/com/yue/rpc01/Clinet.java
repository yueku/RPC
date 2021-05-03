package com.yue.rpc01;

import com.yue.bean.User;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 * 模拟RPC客户端，根据用户ID获取用户信息
 */
public class Clinet {
    public static void main(String[] args) {
        try {
            // 创建socket连接
            Socket socket = new Socket("127.0.0.1", 8888);
            // 创建字节输出流
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // 将字节输出流包装在数据输出流里面
            DataOutputStream dos = new DataOutputStream(baos);
            // 将用户ID包装在输出流里面
            dos.writeInt(1);

            // 将输出流发送给服务器端
            socket.getOutputStream().write(baos.toByteArray());
            socket.getOutputStream().flush();

            // 获取服务器返回信息
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            int id = dis.readInt();
            String name = dis.readUTF();

            User user = new User(id, name);

            System.out.println(user.toString());
            dos.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
