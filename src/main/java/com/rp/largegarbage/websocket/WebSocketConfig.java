package com.rp.largegarbage.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
/**
 * @Description 开启WebSocket支持
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/7/10 13:35
 */
@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
