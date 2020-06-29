//package com.rp.largegarbage.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.socket.config.annotation.*;
//
///**
// * @Description WebSocketConfig配置
// * registerStompEndpoints(StompEndpointRegistry registry)
// * configureMessageBroker(MessageBrokerRegistry config)
// * 这个方法的作用是定义消息代理，通俗一点讲就是设置消息连接请求的各种规范信息。
// *
// * registry.enableSimpleBroker("/topic")表示客户端订阅地址的前缀信息，也就是客户端接收服务端消息的地址的前缀信息（比较绕，看完整个例子，大概就能明白了）
// * registry.setApplicationDestinationPrefixes("/app")指服务端接收地址的前缀，意思就是说客户端给服务端发消息的地址的前缀 * @Author:hui.yunfei@qq.com
//
// * @Author liulida <2979284403@qq.com>
// * @Version v1.0.0
// * @Since 1.0
// * @Date 2020/6/28 17:11
// */
//@Configuration
//@EnableWebSocketMessageBroker
//public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
//
//    @Autowired
//    private WebSocketDecoratorFactory webSocketDecoratorFactory;
//    @Autowired
//    private PrincipalHandshakeHandler principalHandshakeHandler;
//
//    /**
//     * 注册stomp端点
//     * @param registry
//     */
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        /**
//         * myUrl表示 你前端到时要对应url映射
//         * registry.addEndpoint("/socket")表示添加了一个/socket端点，客户端就可以通过这个端点来进行连接。
//         * withSockJS()的作用是开启SockJS支持，即可通过http://IP:PORT/ws来和服务端websocket连接
//         */
//        registry.addEndpoint("/myUrl")
//                .setAllowedOrigins("*")
//                .setHandshakeHandler(principalHandshakeHandler)
//                .withSockJS();
//    }
//
//
//    /**
//     * 配置信息代理
//     * @param registry
//     * queue 点对点
//     * topic 广播
//     * user 点对点前缀
//     */
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        //表示客户端订阅地址的前缀信息，也就是客户端接收服务端消息的地址的前缀信息
//        registry.enableSimpleBroker("/queue", "/topic");
//        // 全局(客户端)使用的消息前缀
//        registry.setApplicationDestinationPrefixes("/app");
//        //指服务端接收地址的前缀，意思就是说客户端给服务端发消息的地址的前缀
//        registry.setUserDestinationPrefix("/user");
//    }
//
//    /*@Override
//    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
//        registration.addDecoratorFactory(webSocketDecoratorFactory);
//        super.configureWebSocketTransport(registration);
//    }*/
//}
//
