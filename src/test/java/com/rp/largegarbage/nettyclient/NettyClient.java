package com.rp.largegarbage.nettyclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Description 客户端
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/7/7 17:48
 */
@Slf4j
@Data
@Component
public class NettyClient implements Runnable {
    /*static final String HOST = "119.57.53.254";
    static final int PORT = 1502;*/
    static final String HOST = "127.0.0.1";
    static final int PORT = 8082;
    static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));
    private String content;

    public NettyClient() {
    }
    /*public NettyClient(String content) {
        this.content = content;
    }*/

    @Override
    public void run() {
        // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            int num = 0;
            boolean boo = true;
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new NettyClientChannelInitializer() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast("decoder", new StringDecoder());
                            p.addLast("encoder", new StringEncoder());
                            p.addLast(new NettyClientHandler());
                        }
                    });
            ChannelFuture future = b.connect(HOST, PORT).sync();
            while (boo) {
                num++;
                future.channel().writeAndFlush(content + "--客户端请求消息");
                try { //休眠一段时间
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //每一条线程向服务端发送的次数
                if (num == 100) {
                    boo = false;
                }
            }
            log.info(content + "-----------------------------" + num);
            //future.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
    /*  下面是不加线程的
        public static void main(String[] args) throws Exception {

        sendMessage("hhh你好？");
    }

    public static void sendMessage(String content) throws InterruptedException {
        // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new NettyClientChannelInitializer() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast("decoder", new StringDecoder());
                            p.addLast("encoder", new StringEncoder());
                            p.addLast(new NettyClientHandler());
                        }
                    });

            ChannelFuture future = b.connect(HOST, PORT).sync();
            future.channel().writeAndFlush(content);
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }*/

