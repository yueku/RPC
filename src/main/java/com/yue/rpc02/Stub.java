package com.yue.rpc02;

import com.yue.bean.User;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 将用户向客户端读取用户信息的代码封装起来
 */
public class Stub {

    private Socket socket;

    public User getUserById(int id) {
        User user = new User();
        try {
            socket = new Socket("127.0.0.1", 8888);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeInt(id);

            socket.getOutputStream().write(baos.toByteArray());
            socket.getOutputStream().flush();

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            int userId = dis.readInt();
            String name = dis.readUTF();

            user.setId(userId);
            user.setName(name);

            dos.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return user;
    }
}
