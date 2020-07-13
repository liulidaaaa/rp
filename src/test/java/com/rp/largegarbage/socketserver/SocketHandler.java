package com.rp.largegarbage.socketserver;

/**
 * @Description Socket操作处理类
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/29 13:45
 */
import com.rp.largegarbage.socketserver.ClientSocket;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;

import static com.rp.largegarbage.socketserver.SocketPool.add;
import static com.rp.largegarbage.socketserver.SocketPool.remove;

@Slf4j
public class SocketHandler{

    /**
     * 将连接的Socket注册到Socket池中
     * @param socket
     * @return
     */
    public static ClientSocket register(Socket socket){
        ClientSocket clientSocket = new ClientSocket();
        clientSocket.setSocket(socket);
        try {
            clientSocket.setInputStream(new DataInputStream(socket.getInputStream()));
            clientSocket.setOutputStream(new DataOutputStream(socket.getOutputStream()));
            byte[] bytes = new byte[1024];
            clientSocket.getInputStream().read(bytes);
            clientSocket.setKey(new String(bytes, "utf-8"));
            add(clientSocket);
            return clientSocket;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 向指定客户端发送信息
     * @param clientSocket
     * @param message
     */
    public static void sendMessage(ClientSocket clientSocket, String message){
        try {
            //clientSocket.getOutputStream().write(message.getBytes("utf-8"));
            //clientSocket.getOutputStream().writeUTF(message);
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"));
            printWriter.print(message);
            printWriter.flush();
        } catch (IOException e) {
            log.error("发送信息异常：{}", e);
            close(clientSocket);
        }
    }

    /**
     * 获取指定客户端的上传信息
     * @param clientSocket
     * @return
     */
    public static String onMessage(ClientSocket clientSocket){
        //byte[] bytes = new byte[1024];
        try {
            //clientSocket.getInputStream().read(bytes);
            //String msg = new String(bytes, "utf-8");
            // 读Sock里面的数据
            InputStream inputStream = clientSocket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);//缓冲区
            String info = "";
            String temp = null;//临时变量
            while (true) {
                if (!((temp = bufferedReader.readLine()) != null)) break;
                info += temp;
                System.out.println("客户端接收服务端发送信息：" + info);
            }
            return info;
        } catch (IOException e) {
            e.printStackTrace();
            close(clientSocket);
        }
        return null;
    }

    /**
     * 指定Socket资源回收
     * @param clientSocket
     */
    public static void close(ClientSocket clientSocket){
        log.info("进行资源回收");
        if (clientSocket != null){
            log.info("开始回收socket相关资源，其Key为{}", clientSocket.getKey());
            remove(clientSocket.getKey());
            Socket socket = clientSocket.getSocket();
            try {
                socket.shutdownInput();
                socket.shutdownOutput();
            } catch (IOException e) {
                log.error("关闭输入输出流异常，{}", e);
            }finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    log.error("关闭socket异常{}", e);
                }
            }
        }
    }


    /**
     * 发送数据包，判断数据连接状态
     * @param clientSocket
     * @return
     */
    public static boolean isSocketClosed(ClientSocket clientSocket){
        try {
            clientSocket.getSocket().sendUrgentData(1);
            return false;
        } catch (IOException e) {
            return true;
        }
    }

}
