//package com.rp.largegarbage.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.socket.WebSocketSession;
//
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * @Description Socket管理器
// * @Author liulida <2979284403@qq.com>
// * @Version v1.0.0
// * @Since 1.0
// * @Date 2020/6/28 17:01
// */
//@Slf4j
//public class SocketManager {
//    private static ConcurrentHashMap<String, WebSocketSession> manager = new ConcurrentHashMap<String, WebSocketSession>();
//    /** logger */
//    private static final Logger LOGGER = LoggerFactory.getLogger(SocketManager.class);
//
//    public static void add(String key, WebSocketSession webSocketSession) {
//        log.info("新添加webSocket连接 {} ", key);
//        manager.put(key, webSocketSession);
//    }
//
//    public static void remove(String key) {
//        log.info("移除webSocket连接 {} ", key);
//        manager.remove(key);
//    }
//
//    public static WebSocketSession get(String key) {
//        log.info("获取webSocket连接 {}", key);
//        return manager.get(key);
//    }
//}
