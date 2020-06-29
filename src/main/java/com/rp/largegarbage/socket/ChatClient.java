package com.rp.largegarbage.socket;

import lombok.extern.slf4j.Slf4j;
import java.io.*;
import java.net.Socket;
import java.util.UUID;
/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/29 13:49
 */
@Slf4j
public class ChatClient {

    public static void main(String[] args) throws IOException {
        /*String host = "119.57.53.254";
        int port = 1502;*/
        String host = "127.0.0.1";
        int port = 8068;
        //与服务端建立连接
        Socket socket = new Socket(host, port);
        socket.setOOBInline(true);

        //建立连接后获取输出流
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());

        /*String jsonLogin ="{\n" +
                "    \"header\": {\"cmd\": \"1000\"},\n" +
                "    \"body\": {\n" +
                "        \"name\": \"GPS\",\n" +
                "        \"pass\": \"000000\",\n" +
                "        \"ver\": \"\",\n" +
                "        \"type\": \"\",\n" +
                "        \"mode\": \"\"\n" +
                "    }\n" +
                "}";
        outputStream.writeUTF(jsonLogin);*/
        String uuid = UUID.randomUUID().toString();
        log.info("uuid: {}", uuid);
        outputStream.write(uuid.getBytes());
        log.info(socket.getInputStream().toString());
        DataInputStream inputStream1 = new DataInputStream(socket.getInputStream());
        String content = "";
        while (true){
            byte[] buff = new byte[1024];
            inputStream1.read(buff);
            String buffer = new String(buff, "utf-8");
            content += buffer;
            log.info("info: {}", buff);
            File file = new File("json.json");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.flush();
        }
    }
}
