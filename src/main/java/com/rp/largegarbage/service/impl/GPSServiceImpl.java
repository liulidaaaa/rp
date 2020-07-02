package com.rp.largegarbage.service.impl;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/7/1 17:18
 */
public class GPSServiceImpl {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GPSServiceImpl.class);

    private static final ThreadLocal<Socket> threadConnect = new ThreadLocal<Socket>();

    private static final String HOST = "119.57.53.254";

    private static final int PORT = 1502;

    private static Socket client;

    private static OutputStream outStr = null;

    private static InputStream inStr = null;

    private static Thread tRecv = new Thread(new RecvThread());
    private static Thread tKeep = new Thread(new KeepThread());

    public static void connect() throws UnknownHostException, IOException {
        client = threadConnect.get();
        if (client == null) {
            client = new Socket(HOST, PORT);
            threadConnect.set(client);
            tKeep.start();
            System.out.println("========链接开始！========");
        }
        outStr = client.getOutputStream();
        inStr = client.getInputStream();
    }

    private static class RecvThread implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println("==============开始接收数据===============");
                while (true) {
                    byte[] b = new byte[1024];
                    int r = inStr.read(b);
                    if(r>-1){
                        String str = new String(b);
                        System.out.println( str );
                        Object parse = JSON.parse(str);
                        //解析 header body
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    // 心跳包
    private static class KeepThread implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println("=====================开始发送心跳包==============");
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("发送心跳数据包");
                    outStr.write("send heart beat data package !".getBytes());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    public static void disconnect() {
        try {
            outStr.close();
            inStr.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        try {
            GPSServiceImpl.connect();
            tRecv.start();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

