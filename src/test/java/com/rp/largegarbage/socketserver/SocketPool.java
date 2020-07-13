package com.rp.largegarbage.socketserver;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/29 13:44
 */

import com.rp.largegarbage.socketserver.ClientSocket;

import java.util.concurrent.ConcurrentHashMap;

public class SocketPool {

    private static final ConcurrentHashMap<String, ClientSocket> ONLINE_SOCKET_MAP = new ConcurrentHashMap<>();


    public static void add(ClientSocket clientSocket){
        if (clientSocket != null && !clientSocket.getKey().isEmpty()) {
            ONLINE_SOCKET_MAP.put(clientSocket.getKey(), clientSocket);
        }
    }

    public static void remove(String key){
        if (!key.isEmpty()) {
            ONLINE_SOCKET_MAP.remove(key);
        }
    }
}
