package com.rp.largegarbage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @Description TCP 通讯方式，实现了用户登录、权限验证、各种数据传输等功能
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/28 15:56
 */
@Service
public class MyClientSocket {

    public static final String HOST = "119.57.53.254";

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(MyClientSocket.class);

    public static String executeSocket(String json) {
        try {
            Socket socket_client = new Socket(HOST, 1502);//端口号可随意编写，只需和服务器端口号一致即可。
            //发送给服务器消息
            OutputStream os = socket_client.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            dos.writeUTF(json);
            //接受服务器消息
            InputStream is = socket_client.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            String content = dis.readUTF();
            socket_client.close();
            LOGGER.info(content);
            return content;
        } catch (Exception e) {
            LOGGER.info("SocketActivity", e.getMessage());
            //e.printStackTrace();
        }
        return null;

    }


}

