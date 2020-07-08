package com.rp.largegarbage.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

/**
 * @Description 启动引导
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/7/7 21:38
 */
@Component
public class HeartbeatClient {

    private final static Logger LOGGER = LoggerFactory.getLogger(HeartbeatClient.class);

    private EventLoopGroup group = new NioEventLoopGroup();

    private int nettyPort = 8083;
    private String host = "127.0.0.1";
    /*private int nettyPort = 1502;
    private String host = "119.57.53.254";*/

    private SocketChannel channel;

    @PostConstruct
    public void start() throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new CustomerHandleInitializer())
        ;

        ChannelFuture future = bootstrap.connect(host, nettyPort).sync();
        if (future.isSuccess()) {
            LOGGER.info("启动 Netty 成功");
        }
        channel = (SocketChannel) future.channel();
    }

}




